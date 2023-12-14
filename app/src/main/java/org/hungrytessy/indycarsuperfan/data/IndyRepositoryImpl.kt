package org.hungrytessy.indycarsuperfan.data

import android.util.Log
import com.prof18.rssparser.RssParserBuilder
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okio.IOException
import org.hungrytessy.indycarsuperfan.common.RSS_MOTORSPORT
import org.hungrytessy.indycarsuperfan.common.RSS_YOUTUBE
import org.hungrytessy.indycarsuperfan.common.Resource
import org.hungrytessy.indycarsuperfan.common.isoZonedDateToLocalDateTime
import org.hungrytessy.indycarsuperfan.common.rssDateStringToLocalDateTime
import org.hungrytessy.indycarsuperfan.common.toIndyRssItem
import org.hungrytessy.indycarsuperfan.data.mapper.RaceWeekendMapper
import org.hungrytessy.indycarsuperfan.data.remote.dto.SeasonDto
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage
import org.hungrytessy.indycarsuperfan.data.remote.dto.toCompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.remote.dto.toDriver
import org.hungrytessy.indycarsuperfan.data.remote.dto.toSeason
import org.hungrytessy.indycarsuperfan.data.remote.dto.toVenue
import org.hungrytessy.indycarsuperfan.data.remote.network.MainNetwork
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.domain.model.Season
import org.hungrytessy.indycarsuperfan.domain.model.Venue
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import retrofit2.HttpException
import java.time.LocalDateTime
import java.util.TreeSet
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class IndyRepositoryImpl @Inject constructor(
    private val api: MainNetwork
) :  IndyRepository {
    private val seasons: TreeSet<SeasonDto> = TreeSet()
    private val drivers: HashMap<String, Driver> = HashMap()
    private val venues: HashMap<String, Venue> = HashMap()
    private val raceWeekends: HashMap<String, RaceWeekend> = HashMap()
    private var seasonResults: Map<SeasonDto, TreeSet<RaceWeekend>> = LinkedHashMap()
    private var mapper: RaceWeekendMapper = RaceWeekendMapper()
    private var dispatcher = Dispatchers.Main

    /**
     * call this on splash
     */
    override suspend fun generate(): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val driversResult = async { api.getDrivers() }
                val venuesResult = async { api.getVenues() }

                driversResult.await().drivers.let { driversMap ->
                    for((key, value) in driversMap) {
                        drivers[key] = value.toDriver()
                    }
                    // drivers.putAll(driversMap.map { it.value.toDriver() })
                    IndyDataStore.drivers = drivers
                    ensureActive()
                    val seasonsResult = api.getSeasons()
                    seasons.addAll(seasonsResult.stages)
                    seasonResults = mapper(seasons)
                    ensureActive()
                    for (key in seasonResults.keys) {
                        val weekendsTree = seasonResults[key] ?: TreeSet()
                        for (weekend in weekendsTree) {
                            raceWeekends[weekend.id] = weekend
                        }
                    }
                }

                ensureActive()

                venuesResult.await().venues.let {
                    for ((key, value) in it) {
                        venues[key] = value.toVenue()
                    }
                }

                Resource.Success(true)
            } catch (e: IOException) {
                Resource.Error(exception = e)
            } catch (e: HttpException) {
                Resource.Error(exception = e)
            }  catch (e: Exception) {
                // do not capture CancellationExceptions, the coroutine needs to stop when cancelled
                if (e is CancellationException) throw e
                Resource.Error(exception = e)
            }
        }
    }

    override suspend fun getDrivers(): Resource<HashMap<String, Driver>> = withContext(dispatcher) { Resource.Success(drivers) }

    override suspend fun fetchNews(): List<IndyRssItem> = withContext(dispatcher) {
        // TODO abstract the rss parser and inject
        val rssParser = RssParserBuilder(callFactory = OkHttpClient(), charset = Charsets.UTF_8,).build()
        val rssMotorsports: RssChannel = rssParser.getRssChannel(RSS_MOTORSPORT)
        val rssYoutube: RssChannel = rssParser.getRssChannel(RSS_YOUTUBE)

        val allRssTree: TreeSet<RssItem> = TreeSet { o1, o2 ->
            val date1 = try {
                o1.pubDate!!.isoZonedDateToLocalDateTime()
            } catch (e: Exception) {
                try {
                    rssDateStringToLocalDateTime(o1.pubDate!!)
                } catch (e: Exception) {
                    LocalDateTime.now()
                }
            }

            val date2 = try {
                o2.pubDate!!.isoZonedDateToLocalDateTime()
            } catch (e: Exception) {
                try {
                    rssDateStringToLocalDateTime(o2.pubDate!!)
                } catch (e: Exception) {
                    LocalDateTime.now()
                }
            }

            if (date1.isBefore(date2)) {
                1;
            } else if (date1.isAfter(date2)) {
                -1;
            } else {
                0
            }

        }
        allRssTree.addAll(rssMotorsports.items)
        allRssTree.addAll(rssYoutube.items)
        ArrayList(allRssTree).map { it.toIndyRssItem() }
    }

    override suspend fun getNextRace(): RaceWeekend? {
        for (race in seasons.last().races!!) {
            if ((race.stageSummary?.stages?.last()?.status) == "Not started") {
                return mapper.stagesToRaceWeekends(setOf(race)).first()
            }
        }
        return mapper.stagesToRaceWeekends(setOf(seasons.first().races!!.first())).first()
    }

    override suspend fun getCurrentStanding(): List<CompetitorEventSummary> = withContext(dispatcher) {
        // if season not started yet get previous season
        val season: SeasonDto = if (isSeasonStarted()) {
            getLatestSeason()
        } else {
            getPreviousSeason()
        }
        ArrayList<CompetitorEventSummary>(season.seasonSummary?.competitors!!.map { it.toCompetitorEventSummary() })
    }

    /**
     * if the new season has no results means it's data for the upcoming season
     */
    override suspend fun isSeasonStarted(): Boolean = withContext(dispatcher) {
        getLatestSeason().seasonSummary?.competitors != null
    }

    override suspend fun getResultsDriverCurrentSeason(driverId: String): CompetitorEventSummary? = withContext(dispatcher) {
        var result: CompetitorEventSummary? = null
        val seasonsList = ArrayList(seasons)
        // if season not started yet get previous season
        val season: SeasonDto = if (isSeasonStarted()) {
            seasons.last()
        } else {
            seasonsList[seasonsList.size - 2]
        }

        season.seasonSummary?.competitors?.let { competitors ->
            for(competitor in competitors) {
                if(competitor.id == driverId) {
                    result = competitor.toCompetitorEventSummary()
                }
            }
        }

        result
    }

    override suspend fun getResultsDriverAllSeasons(driverId: String): Map<Season, CompetitorEventSummary> = withContext(dispatcher) {
        val map = LinkedHashMap<Season, CompetitorEventSummary>()
        val seasonsCopy = TreeSet<SeasonDto>()
        seasonsCopy.addAll(seasons)
        if (!isSeasonStarted()) {
            seasonsCopy.remove(seasons.last())
        }

        for(season in seasonsCopy.reversed()) {
            season.seasonSummary?.competitors?.let { competitors ->
                for(competitor in competitors) {
                    if(competitor.id == driverId) {
                        try {
                            val season1 = season.toSeason()
                            map[season1] = competitor.toCompetitorEventSummary()
                        } catch (ex: Exception){
                            Log.e("eeee", "${ex.localizedMessage} $competitor ")
                        }

                    }
                }
            }
        }
        map
    }

    override suspend fun getUpcomingRaces(): List<RaceWeekend> = withContext(dispatcher) {
        val list: LinkedHashSet<Stage> = LinkedHashSet()
        getLatestSeason().races?.let { races ->
            if (isSeasonStarted()) {
                for (race in races) {
                    if(!race.isStageStarted()) {
                        list.add(race)
                    }
                }
            } else {
                list.addAll(races)
            }
        }
        mapper.stagesToRaceWeekends(getUpcomingStages()).toList()
    }

    private suspend fun getUpcomingStages(): Set<Stage> = withContext(dispatcher) {
        val list: LinkedHashSet<Stage> = LinkedHashSet()
        getLatestSeason().races?.let { races ->
            if (isSeasonStarted()) {
                for (race in races) {
                    if(!race.isStageStarted()) {
                        list.add(race)
                    }
                }
            } else {
                list.addAll(races)
            }
        }
        list
    }

    private suspend fun getAllRaces(): ArrayList<Stage> = withContext(dispatcher) {
        val list: ArrayList<Stage> = ArrayList()
        for (season in seasons.reversed()) {
            season.races?.let { list.addAll(it.reversed()) }
        }
        list
    }

    private suspend fun getPastRaces(): List<RaceWeekend> = withContext(dispatcher) {
        val list = ArrayList<Stage>()
        list.addAll(getAllRaces())
        //mapper.stagesToRaceWeekends(
        list.removeAll(getUpcomingStages().toList())
        mapper.stagesToRaceWeekends(list.toSet()).toList().reversed()
    }

    override suspend fun getPastRaceWeekends(): List<RaceWeekend> = withContext(dispatcher) {
        val list = ArrayList<RaceWeekend>()
        for (stage in getPastRaces()) {
            raceWeekends[stage.id]?.let { list.add(it) }
        }
        list
    }

    override suspend fun getCurrentSeasonFinishedRaces(): List<RaceWeekend> = withContext(dispatcher)  {
        val races: TreeSet<Stage> = TreeSet()
        if (isSeasonStarted()) {
            races.addAll(getLatestSeason().races ?: ArrayList())
        } else {
            races.addAll(getPreviousSeason().races ?: ArrayList())
        }

        val wkds = mapper.stagesToRaceWeekends(races)
        wkds.removeAll(LinkedHashSet(getUpcomingRaces()))
        ArrayList(wkds).reversed()
    }

    private suspend fun getPreviousSeason(): SeasonDto = withContext(dispatcher)  {
        val seasonsList = ArrayList(seasons)
        seasonsList[seasonsList.size - 2]
    }

    private suspend fun getLatestSeason(): SeasonDto = withContext(dispatcher) { seasons.last() }

    override suspend fun getSingleRace(raceId: String): RaceWeekend?  = withContext(dispatcher) {
        raceWeekends[raceId]
    }

    override suspend fun getRaceResults(raceId: String): RaceWeekend? = withContext(dispatcher) {
        raceWeekends[raceId]
    }

    override suspend fun getPastWinners(venue: Venue): Map<String, Driver> = withContext(dispatcher)  {
        val winners = LinkedHashMap<String, Driver>()
        for(season in seasons) {
            seasonResults[season]?.let { races ->
                for(race in races) {
                    if(race.venue?.id == venue.id) {
                        race.race?.result?.first()?.driver?.let { driver ->
                            winners["${season.description?.replace("Indycar", "")?.trim()}"] = driver
                        }
                    }
                }
            }
        }
        winners
    }
}
