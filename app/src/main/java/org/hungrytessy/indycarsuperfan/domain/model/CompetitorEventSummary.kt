package org.hungrytessy.indycarsuperfan.domain.model

data class CompetitorEventSummary (
    val id: String,
    val result: CompetitorEventResult?,
    val driver: Driver?
): Comparable<CompetitorEventSummary> {
    override fun compareTo(other: CompetitorEventSummary): Int {
        if (result == null) return 1
        if (result.position == null) return 1
        if (other.result == null) return - 1
        if (other.result.position == null) return - 1

        return result.position - other.result.position
    }
}
