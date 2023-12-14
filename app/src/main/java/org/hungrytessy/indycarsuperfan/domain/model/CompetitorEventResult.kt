package org.hungrytessy.indycarsuperfan.domain.model

data class CompetitorEventResult (
    val bestSpeed: Double?,
    val laps: Int?,
    val position: Int?,
    val status: String?,
    val gap: String?,
    val carNumber: Int?,

    val points: Int?,
    val speed: Double?,
    val fastestLapTime: String?,
    val grid: Int?,
    val lapsLed: Int?,
    val time: String?,
    val avgSpeed: String?,
    val leadingChangesLaps: String?,

    // Season stats
    val victories: Int?,
    val races: Int?,
    val racesWithPoints: Int?,
    val polePositions: Int?,
    val podiums: Int?,
    val top5: Int?,
    val top10: Int?,
)
