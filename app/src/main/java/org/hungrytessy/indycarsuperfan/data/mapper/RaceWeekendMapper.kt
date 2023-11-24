package org.hungrytessy.indycarsuperfan.data.mapper

import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.remote.dto.Season
import org.hungrytessy.indycarsuperfan.domain.model.Practice
import org.hungrytessy.indycarsuperfan.domain.model.Qualification
import org.hungrytessy.indycarsuperfan.domain.model.QualificationStage
import org.hungrytessy.indycarsuperfan.domain.model.Race
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import java.util.TreeSet

private fun removeUnqualified(summary: TreeSet<CompetitorEventSummary>?): TreeSet<CompetitorEventSummary> {
    val sanitized = TreeSet<CompetitorEventSummary>()
    summary?.let {
        for (driverResult in it) {
            if (driverResult.result?.position != null ) {
                sanitized.add(driverResult)
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

fun seasonRacesFactory(season: Season): TreeSet<RaceWeekend> {
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
                            val practice: Practice = Practice()
                            practice.status = singleRaceStage.stageSummary?.status
                            practice.result = singleRaceStage.stageSummary?.competitors
                            practice.id = singleRaceStage.id
                            practice.description = singleRaceStage.description
                            practice.scheduled = singleRaceStage.scheduled
                            practice.scheduledEnd = singleRaceStage.scheduledEnd
                            //practice.type = singleRaceStage.type
                            practiceList.add(practice)
                        }
                        "race" -> {
                            race.id = singleRaceStage.id
                            race.description = singleRaceStage.description
                            race.scheduled = singleRaceStage.scheduled
                            race.scheduledEnd = singleRaceStage.scheduledEnd
                            race.status = singleRaceStage.stageSummary?.status
                            race.result = singleRaceStage.stageSummary?.competitors
                            //race.type = singleRaceStage.type
                        }
                        "qualifying" -> {
                            val qualifyList = TreeSet<QualificationStage>() // array if all the stages of qualification
                            singleRaceStage.stages?.let { qualificationStages ->
                                // this is a qualification stage, usually Q1 to Q4
                                for(singleQualifyingStage in qualificationStages) {
                                    if (singleQualifyingStage.type == "qualifying_part") {
                                        val stage = QualificationStage()
                                        stage.status = singleQualifyingStage.stageSummary?.status
                                        stage.result = removeUnqualified(singleQualifyingStage.stageSummary?.competitors)
                                        stage.id = singleQualifyingStage.id
                                        stage.description = singleQualifyingStage.description
                                        stage.scheduled = singleQualifyingStage.scheduled
                                        stage.scheduledEnd = singleQualifyingStage.scheduledEnd
                                        //stage.type = singleQualifyingStage.type
                                        qualifyList.add(stage)
                                    }
                                }
                            }

                            qualification.id = singleRaceStage.id
                            qualification.description = singleRaceStage.description
                            qualification.scheduled = singleRaceStage.scheduled
                            qualification.scheduledEnd = singleRaceStage.scheduledEnd
                            qualification.qualificationStages = qualifyList
                            //qualification.type = singleRaceStage.type
                            qualification.status = singleRaceStage.stageSummary?.status
                        }
                    }
                }
            }
            val raceWeekend = RaceWeekend()
            raceWeekend.status = rc.status
            raceWeekend.venue = rc.venue
            raceWeekend.race = race
            raceWeekend.qualification = qualification
            raceWeekend.practice = practiceList
            raceWeekend.id = rc.id
            raceWeekend.description = rc.description
            raceWeekend.scheduled = rc.scheduled
            raceWeekend.scheduledEnd = rc.scheduledEnd
            raceWeekend.type = rc.type
            allSeasonRaces.add(raceWeekend)
        }
    }
    return allSeasonRaces
}