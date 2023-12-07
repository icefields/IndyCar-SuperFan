package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.TreeSet

/// events from a single season OR a single race
/// in the case of single race the Events in the stages list are the events inside the race, ie practice, qualify, race
data class Stages (
    @SerializedName("stages") val stages: TreeSet<Stage>
)

data class Stage (
    @SerializedName("status") val status: String?, // "Closed",
    @SerializedName("venue") val venue: Venue?,
    @SerializedName("stages") val stages: TreeSet<Stage>?,

    var stageSummary: EventSummaryDetail?
): BaseStageDto()
