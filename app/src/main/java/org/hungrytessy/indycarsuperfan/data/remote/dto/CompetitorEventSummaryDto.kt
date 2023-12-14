package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Driver

data class CompetitorEventSummaryDto (
    @SerializedName("id") val id: String,
    @SerializedName("competitor") private val competitor: DriverInfo?,
    @SerializedName("result") val result: CompetitorEventResultDto?
): Comparable<CompetitorEventSummaryDto> {

    // TODO: this is a temporary hack, refactor as soon as possible
    fun getDriver(): Driver? = IndyDataStore.drivers[id]

    override fun compareTo(other: CompetitorEventSummaryDto): Int {
        if (result == null) return 1
        if (result.position == null) return 1
        if (other.result == null) return - 1
        if (other.result.position == null) return - 1

        return result.position - other.result.position
    }
}

fun CompetitorEventSummaryDto.toCompetitorEventSummary(): CompetitorEventSummary =
    CompetitorEventSummary(
        id = id,
        result = result?.toCompetitorEventResult(),
        driver = getDriver()
    )
