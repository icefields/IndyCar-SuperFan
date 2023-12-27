package org.hungrytessy.indycarsuperfan.domain.model


import org.hungrytessy.indycarsuperfan.common.isoZonedDateToLocalDateTime
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

abstract class BaseStage: Comparable<BaseStage> {
    lateinit var id: String
    var description: String? = null
    var scheduled: String? = null
    var scheduledEnd: String? = null
    var singleEvent: Boolean = true
    lateinit var type: String
    var stageName: String = ""

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
}
