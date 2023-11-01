package org.hungrytessy.indycarsuperfan

interface MainActivityNav {
    fun goToDriverProfile(driverId: String)
    fun switchToResults()
    fun goToWeekendRaceResult(raceId: String)
    fun goToWeekendRaceSchedule(raceId: String)
}