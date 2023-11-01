package org.hungrytessy.indycarsuperfan.data.models

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import java.util.TreeSet

data class CompetitorEventSummary (
    @SerializedName("id") val id: String,
    @SerializedName("competitor") private val competitor: DriverInfo?,
    @SerializedName("result") val result: CompetitorEventResult?
): Comparable<CompetitorEventSummary> {

    fun getDriver(): Driver? = IndyDataStore.drivers[id]

    override fun compareTo(other: CompetitorEventSummary): Int {
        if (result == null) return 1
        if (result.position == null) return 1
        if (other.result == null) return - 1
        if (other.result.position == null) return - 1

        return result.position - other.result.position
    }
}


/**
final String id;
final DriverInfo competitor;
final CompetitorEventResult? result;

@override
int compareTo(CompetitorEventSummary other) {
if (result == null) return 1;
if (result!.position == null) return 1;
if (other.result == null) return -1;
if (other.result!.position == null) return -1;

return result!.position! - other.result!.position!;
}
 */