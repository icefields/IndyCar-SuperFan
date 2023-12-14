package org.hungrytessy.indycarsuperfan.data.remote.dto

import android.util.Log
import org.hungrytessy.indycarsuperfan.domain.model.Season
import java.util.TreeSet

data class SeasonDto(
    var races: TreeSet<Stage>?,
    var seasonSummary: EventSummaryDetail?
): BaseStageDto()

fun SeasonDto.toSeason(): Season {
    val dto = this
    return Season().apply {
        Log.d("aaaa", "$description")
        id = dto.id
        description = dto.description
        scheduled = dto.scheduled
        scheduledEnd = dto.scheduledEnd
        singleEvent = dto.singleEvent
        type = dto.type
        stageName = dto.description ?: ""
    }
}
