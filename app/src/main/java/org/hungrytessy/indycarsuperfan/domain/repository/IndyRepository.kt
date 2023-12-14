package org.hungrytessy.indycarsuperfan.domain.repository

import org.hungrytessy.indycarsuperfan.common.Resource
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.domain.model.Season
import org.hungrytessy.indycarsuperfan.domain.model.Venue

interface IndyRepository {
    suspend fun generate(): Resource<Boolean>
    suspend fun getDrivers(): Resource<HashMap<String, Driver>>
    suspend fun getPastWinners(venue: Venue): Map<String, Driver>
    suspend fun getRaceResults(raceId: String): RaceWeekend?
    suspend fun getSingleRace(raceId: String): RaceWeekend?
    suspend fun getCurrentSeasonFinishedRaces(): List<RaceWeekend>
    suspend fun getPastRaceWeekends(): List<RaceWeekend>
    suspend fun getUpcomingRaces(): List<RaceWeekend>
    suspend fun getResultsDriverAllSeasons(driverId: String): Map<Season, CompetitorEventSummary>
    suspend fun getResultsDriverCurrentSeason(driverId: String): CompetitorEventSummary?
    suspend fun isSeasonStarted(): Boolean
    suspend fun getCurrentStanding(): List<CompetitorEventSummary>
    suspend fun getNextRace(): RaceWeekend?
    suspend fun fetchNews(): List<IndyRssItem>
}
