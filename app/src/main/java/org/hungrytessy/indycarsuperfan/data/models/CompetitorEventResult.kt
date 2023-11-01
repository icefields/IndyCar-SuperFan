package org.hungrytessy.indycarsuperfan.data.models

import com.google.gson.annotations.SerializedName

data class CompetitorEventResult (
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


/*
"car_number": 2,
                    "position": 2,
                    "victories": 1,
                    "races": 17,
                    "races_with_points": 17,
                    "pole_positions": 0,
 "fastest_lap_time": "01:07.9026",
                    "laps": 90,
                    "position": 1,
                    "grid": 3,
                    "status": "Finished",
                    "lapsled": 56,
                    "car_number": 10,
                    "time": "01:52:53.0361",
                    "av_speed": "177.068",
                    "leading_changes_laps": "18,42,67"
class CompetitorEventResult {
    final double? bestSpeed;
    final int? laps;
    final int? position;
    final String? status; // Finished
    final String? gap;
    final int? carNumber;

    final int? points;
    final double? speed;
    final String? fastestLapTime;
    final int? grid;
    final int? lapsLed;
    final String? time;
    final String? avgSpeed;
    final String? leadingChangesLaps;

    // Season stats
    final int? victories;
    final int? races;
    final int? racesWithPoints;
    final int? polePositions;
    final int? podiums;
    final int? top5;
    final int? top10;

 */