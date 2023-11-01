package org.hungrytessy.indycarsuperfan.data.models

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.extensions.isoZonedDateToLocalDateTime
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

abstract class BaseStage: Comparable<BaseStage> {
    @SerializedName("id") lateinit var id: String
    @SerializedName("description") var description: String? = null
    @SerializedName("scheduled") var scheduled: String? = null
    @SerializedName("scheduled_end") var scheduledEnd: String? = null
    @SerializedName("single_event") var singleEvent: Boolean = true
    @SerializedName("type") lateinit var type: String

    fun getScheduled(): LocalDateTime {
        val offsetDate = OffsetDateTime.of(scheduled?.let { it.isoZonedDateToLocalDateTime() }?: LocalDateTime.now(), ZoneOffset.UTC)
        return offsetDate.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    }

    fun getScheduledDateTimeFormatted(): String = getScheduled().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")) ?: "ERROR"

    fun getScheduledDateFormatted(): String = getScheduled().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) ?: "ERROR"
    fun getScheduledTimeFormatted(): String = getScheduled().format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "ERROR"

    fun getScheduledEnd(): LocalDateTime {
        val offsetDate = OffsetDateTime.of(scheduledEnd?.let { it.isoZonedDateToLocalDateTime() }?: LocalDateTime.now(), ZoneOffset.UTC)
        return offsetDate.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
        //return scheduledEnd?.let { stringToLocalDateTime(it) }?: LocalDateTime.MIN
    }

    fun isStageStarted(): Boolean = getScheduled().isBefore(LocalDateTime.now())

    fun isStageEnded(): Boolean = getScheduledEnd().isBefore(LocalDateTime.now())

    override fun compareTo(other: BaseStage): Int {
        return if (getScheduled().isBefore(other.getScheduled())) {
            -1;
        } else if (getScheduled().isAfter(other.getScheduled())) {
            1;
        } else {
            return id.compareTo(other.id)
        }
    }

    fun getStageName(textWrap: Boolean = false): String {
        return ("Qualifying" +
                if (textWrap) {"\n"} else {" "} +
                description?.let {
            when (it) {
                "Q1" -> "Group 1"
                "Q2" -> "Group 2"
                "Q3" -> "Round 2"
                "Q4" -> "Fast6"
                else -> it
            }
        }) ?: run { "ERROR" }
    }
}