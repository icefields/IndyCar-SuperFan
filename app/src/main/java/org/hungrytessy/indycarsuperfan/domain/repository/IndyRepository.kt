package org.hungrytessy.indycarsuperfan.domain.repository

import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver
import org.hungrytessy.indycarsuperfan.data.remote.dto.Season
import org.hungrytessy.indycarsuperfan.data.remote.dto.Stage
import org.hungrytessy.indycarsuperfan.data.remote.dto.Venue
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend

interface IndyRepository {
    suspend fun generate()
    fun getPastWinners(venue: Venue): Map<String, Driver>
    fun getRaceResults(raceId: String): RaceWeekend?
    fun getSingleRace(raceId: String): RaceWeekend?
    fun getLatestSeason(): Season
    fun getPreviousSeason(): Season
    fun getCurrentSeasonFinishedRaces(): List<Stage>
    fun getPastRaceWeekends(): List<RaceWeekend>
    fun getUpcomingRaces(): List<Stage>
    fun getResultsDriverAllSeasons(driverId: String): Map<Season, CompetitorEventSummary>
    fun getResultsDriverCurrentSeason(driverId: String): CompetitorEventSummary?
    fun isSeasonStarted(): Boolean
    fun getCurrentStanding(): List<CompetitorEventSummary>
    fun getNextRace(): Stage?
    suspend fun fetchNews(): List<IndyRssItem>
}
