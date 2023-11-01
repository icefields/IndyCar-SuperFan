package org.hungrytessy.indycarsuperfan.data.models

import androidx.recyclerview.widget.SortedList
import com.google.gson.annotations.SerializedName
import java.util.TreeSet

data class EventSummaryDetail (
    @SerializedName("status") val status: String?, // "Closed",
    @SerializedName("competitors") var competitors: TreeSet<CompetitorEventSummary>?,
    @SerializedName("teams") val teams: List<Team>?,
    @SerializedName("venue") val venue: Venue?,
    @SerializedName("stages") val stages: TreeSet<Stage>?
): BaseStage()

data class EventSummary (
    @SerializedName("stage") val stage: EventSummaryDetail?
)

/**
final String? status; // "Closed",
final SortedList<CompetitorEventSummary>? competitors;
final List<Team>? teams;
final Venue? venue;
final SortedList<Stage>? stages;
 */