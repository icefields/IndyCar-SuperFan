package org.hungrytessy.indycarsuperfan.data.mapper

import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummaryDto
import org.hungrytessy.indycarsuperfan.data.remote.dto.Season
import org.hungrytessy.indycarsuperfan.data.remote.dto.toCompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Practice
import org.hungrytessy.indycarsuperfan.domain.model.Qualification
import org.hungrytessy.indycarsuperfan.domain.model.QualificationStage
import org.hungrytessy.indycarsuperfan.domain.model.Race
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import java.util.TreeSet

private fun removeUnqualified(summary: TreeSet<CompetitorEventSummaryDto>?): TreeSet<CompetitorEventSummary> {
    val sanitized = TreeSet<CompetitorEventSummary>()
    summary?.let {
        for (driverResult in it) {
            if (driverResult.result?.position != null ) {
                sanitized.add(driverResult.toCompetitorEventSummary())
            }
        }
    }
    return sanitized
}

fun allSeasonsRacesFactory(seasons: TreeSet<Season>): Map<Season, TreeSet<RaceWeekend>> {
    val map = LinkedHashMap<Season, TreeSet<RaceWeekend>>()
    for(season in seasons) {
        map[season] = seasonRacesFactory(season)
    }
    return map
}

private fun competitorsDtoToCompetitors(dtoTree: TreeSet<CompetitorEventSummaryDto>?)
: TreeSet<CompetitorEventSummary>? = dtoTree?.let { dtoList ->
    TreeSet(dtoList.map { it.toCompetitorEventSummary() })
}

private fun seasonRacesFactory(season: Season): TreeSet<RaceWeekend> {
    val allSeasonRaces = TreeSet<RaceWeekend>()
    // from season grab every stage (race)
    season.races?.let { races ->
        for (rc in races) {
            val practiceList = TreeSet<Practice>()
            val race = Race()
            val qualification = Qualification()
            rc.stages?.let { raceStages ->
                // from race, ignore stageSummary, loop stages
                for(singleRaceStage in raceStages) {
                    // every stage is a practice, race or qualify
                    when(singleRaceStage.type) {
                        "practice" -> {
                            Practice().apply {
                                status = singleRaceStage.stageSummary?.status
                                result = competitorsDtoToCompetitors(singleRaceStage.stageSummary?.competitors) // TreeSet(singleRaceStage.stageSummary?.competitors?.mapNotNull { it.toCompetitorEventSummary() })
                                id = singleRaceStage.id
                                description = singleRaceStage.description
                                scheduled = singleRaceStage.scheduled
                                scheduledEnd = singleRaceStage.scheduledEnd
                                stageName = singleRaceStage.getStageName(true)
                            }.also {
                                practiceList.add(it)
                            }
                        }
                        "race" -> {
                            race.apply {
                                id = singleRaceStage.id
                                description = singleRaceStage.description
                                scheduled = singleRaceStage.scheduled
                                scheduledEnd = singleRaceStage.scheduledEnd
                                status = singleRaceStage.stageSummary?.status
                                result = competitorsDtoToCompetitors(singleRaceStage.stageSummary?.competitors) // TreeSet(singleRaceStage.stageSummary?.competitors?.mapNotNull { it.toCompetitorEventSummary() })
                                stageName = singleRaceStage.getStageName(true)
                            }
                        }
                        "qualifying" -> {
                            val qualifyList = TreeSet<QualificationStage>() // array if all the stages of qualification
                            singleRaceStage.stages?.let { qualificationStages ->
                                // this is a qualification stage, usually Q1 to Q4
                                for(singleQualifyingStage in qualificationStages) {
                                    if (singleQualifyingStage.type == "qualifying_part") {
                                        QualificationStage().apply {
                                            status = singleQualifyingStage.stageSummary?.status
                                            result = removeUnqualified(singleQualifyingStage.stageSummary?.competitors)
                                            id = singleQualifyingStage.id
                                            description = singleQualifyingStage.description
                                            scheduled = singleQualifyingStage.scheduled
                                            scheduledEnd = singleQualifyingStage.scheduledEnd
                                            stageName = singleQualifyingStage.getStageName(true)
                                        }.also {
                                            qualifyList.add(it)
                                        }
                                    }
                                }
                            }

                            qualification.apply {
                                id = singleRaceStage.id
                                description = singleRaceStage.description
                                scheduled = singleRaceStage.scheduled
                                scheduledEnd = singleRaceStage.scheduledEnd
                                qualificationStages = qualifyList
                                status = singleRaceStage.stageSummary?.status
                                stageName = singleRaceStage.getStageName(true)
                            }
                        }
                    }
                }
            }

            RaceWeekend().apply {
                status = rc.status
                venue = rc.venue
                this.race = race
                this.qualification = qualification
                practice = practiceList
                id = rc.id
                description = rc.description
                scheduled = rc.scheduled
                scheduledEnd = rc.scheduledEnd
                type = rc.type
                stageName = rc.getStageName(true)
            }.also {
                allSeasonRaces.add(it)
            }
        }
    }
    return allSeasonRaces
}
