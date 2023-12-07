package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompetitorEventResultDto (
    @SerializedName("best_speed") val bestSpeed: Double?,
    @SerializedName("laps") val laps: Int?,
    @SerializedName("position") val position: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("gap") val gap: String?,
    @SerializedName("car_number") val carNumber: Int?,

    @SerializedName("points") val points: Int?,
    @SerializedName("speed") val speed: Double?,
    @SerializedName("fastest_lap_time") val fastestLapTime: String?,
    @SerializedName("grid") val grid: Int?,
    @SerializedName("lapsled") val lapsLed: Int?,
    @SerializedName("time") val time: String?,
    @SerializedName("av_speed") val avgSpeed: String?,
    @SerializedName("leading_changes_laps") val leadingChangesLaps: String?,

    // Season stats
    @SerializedName("victories") val victories: Int?,
    @SerializedName("races") val races: Int?,
    @SerializedName("races_with_points") val racesWithPoints: Int?,
    @SerializedName("pole_positions") val polePositions: Int?,
    @SerializedName("podiums") val podiums: Int?,
    @SerializedName("top5") val top5: Int?,
    @SerializedName("top10") val top10: Int?,
)
