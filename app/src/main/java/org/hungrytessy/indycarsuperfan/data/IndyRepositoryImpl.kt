package org.hungrytessy.indycarsuperfan.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.hungrytessy.indycarsuperfan.data.mapper.allSeasonsRacesFactory
import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver
import org.hungrytessy.indycarsuperfan.data.remote.dto.Season
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage
import org.hungrytessy.indycarsuperfan.data.remote.dto.Venue
import org.hungrytessy.indycarsuperfan.data.remote.network.MainNetwork
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import java.util.TreeSet
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class IndyRepositoryImpl @Inject constructor(
    private val api: MainNetwork
) :  IndyRepository {
    private val seasons: TreeSet<Season> = TreeSet()
    val drivers: HashMap<String, Driver> = HashMap()
    private val venues: HashMap<String, Venue> = HashMap()
    private val raceWeekends: HashMap<String, RaceWeekend> = HashMap()
    private var seasonResults: Map<Season, TreeSet<RaceWeekend>> = LinkedHashMap()

    /**
     * call this on splash
     */
    override suspend fun generate() {
        withContext(Dispatchers.IO) {
            try {
                drivers.putAll(api.getDrivers().drivers)
                ensureActive()
                seasons.addAll(api.getSeasons().stages)
                ensureActive()
                venues.putAll(api.getVenues().venues)
                ensureActive()
                seasonResults = allSeasonsRacesFactory(seasons)
                for (key in seasonResults.keys) {
                    val weekendsTree = seasonResults[key] ?: TreeSet()
                    for (weekend in weekendsTree) {
                        raceWeekends[weekend.id] = weekend
                    }
                }

                // TODO: refactor and completely remove IndyDataStore
                IndyDataStore.drivers.putAll(drivers)
                IndyDataStore.seasons.addAll(seasons)
                IndyDataStore.venues.putAll(venues)
                IndyDataStore.seasonResults = seasonResults
                IndyDataStore.raceWeekends.putAll(raceWeekends)
            } catch (e: Exception) {
                // TODO: handle errors
                // do not capture CancellationExceptions, the coroutine needs to stop when cancelled
                if (e is CancellationException) throw e
            }
        }
    }

    override fun getNextRace(): Stage? {
        for (race in seasons.last().races!!) {
            //Log.d("LUCIFER", "${race.description} STATUS ${race.stageSummary?.stages?.last()?.status}")
            if ((race.stageSummary?.stages?.last()?.status) == "Not started") {
                return race
            }
        }
        return seasons.first().races!!.first()
    }

    override fun getCurrentStanding(): List<CompetitorEventSummary> {
        // if season not started yet get previous season
        val season: Season = if (isSeasonStarted()) {
            getLatestSeason()
        } else {
            getPreviousSeason()
        }
        return ArrayList<CompetitorEventSummary>(season.seasonSummary?.competitors!!)
    }

    /**
     * if the new season has no results means it's data for the upcoming season
     */
    override fun isSeasonStarted(): Boolean {
        return getLatestSeason().seasonSummary?.competitors != null
    }

    override fun getResultsDriverCurrentSeason(driverId: String): CompetitorEventSummary? {
        var result: CompetitorEventSummary? = null
        val seasonsList = ArrayList(seasons)
        // if season not started yet get previous season
        val season: Season = if (isSeasonStarted()) {
            seasons.last()
        } else {
            seasonsList[seasonsList.size - 2]
        }

        season.seasonSummary?.competitors?.let { competitors ->
            for(competitor in competitors) {
                if(competitor.id == driverId) {
                    result = competitor
                }
            }
        }

        return result
    }

    override fun getResultsDriverAllSeasons(driverId: String): Map<Season, CompetitorEventSummary> {
        val map = LinkedHashMap<Season, CompetitorEventSummary>()
        val seasonsCopy = TreeSet<Season>()
        seasonsCopy.addAll(seasons)
        if (!isSeasonStarted()) {
            seasonsCopy.remove(seasons.last())
        }

        for(season in seasonsCopy.reversed()) {
            season.seasonSummary?.competitors?.let { competitors ->
                for(competitor in competitors) {
                    if(competitor.id == driverId) {
                        try {
                            map[season] = competitor
                        } catch (ex: Exception){
                            Log.e("eeee", "${ex.localizedMessage} $competitor ")
                        }

                    }
                }
            }
        }
        return map
    }

    override fun getUpcomingRaces(): List<Stage> {
        val list: ArrayList<Stage> = ArrayList()
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
        return list
    }

    private fun getAllRaces(): ArrayList<Stage> {
        val list: ArrayList<Stage> = ArrayList()
        for (season in seasons.reversed()) {
            season.races?.let { list.addAll(it.reversed()) }
        }
        return list
    }

    private fun getPastRaces(): List<Stage> {
        val list = ArrayList<Stage>()
        list.addAll(getAllRaces())
        list.removeAll(LinkedHashSet(getUpcomingRaces()))
        return list
    }

    override fun getPastRaceWeekends(): List<RaceWeekend> {
        val list = ArrayList<RaceWeekend>()
        for (stage in getPastRaces()) {
            raceWeekends[stage.id]?.let { list.add(it) }
        }
        return list
    }

    override fun getCurrentSeasonFinishedRaces(): List<Stage> {
        val races: TreeSet<Stage> = TreeSet()
        if (isSeasonStarted()) {
            races.addAll(getLatestSeason().races ?: ArrayList())
        } else {
            races.addAll(getPreviousSeason().races ?: ArrayList())
        }

        races.removeAll(LinkedHashSet(getUpcomingRaces()))
        return ArrayList(races).reversed()
    }

    override fun getPreviousSeason(): Season {
        val seasonsList = ArrayList(seasons)
        return seasonsList[seasonsList.size - 2]
    }

    override fun getLatestSeason(): Season = seasons.last()

    override fun getSingleRace(raceId: String): RaceWeekend? {
        return raceWeekends[raceId]
    }

    override fun getRaceResults(raceId: String): RaceWeekend? = raceWeekends[raceId]

    override fun getPastWinners(venue: Venue): Map<String, Driver> {
        val winners = LinkedHashMap<String, Driver>()
        for(season in seasons) {
            seasonResults[season]?.let { races ->
                for(race in races) {
                    if(race.venue?.id == venue.id) {
                        race.race?.result?.first()?.getDriver()?.let { driver ->
                            winners["${season.description?.replace("Indycar", "")?.trim()}"] = driver
                        }
                    }
                }
            }
        }
        return winners
    }
}
