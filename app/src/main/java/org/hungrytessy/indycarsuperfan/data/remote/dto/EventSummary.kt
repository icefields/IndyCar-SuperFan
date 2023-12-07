package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.TreeSet

data class EventSummaryDetail (
    @SerializedName("status") val status: String?, // "Closed",
    @SerializedName("competitors") var competitors: TreeSet<CompetitorEventSummaryDto>?,
    @SerializedName("teams") val teams: List<Team>?,
    @SerializedName("venue") val venue: Venue?,
    @SerializedName("stages") val stages: TreeSet<Stage>?
): BaseStageDto()

data class EventSummary (
    @SerializedName("stage") val stage: EventSummaryDetail?
)
