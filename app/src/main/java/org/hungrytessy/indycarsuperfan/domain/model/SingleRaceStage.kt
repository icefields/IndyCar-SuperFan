package org.hungrytessy.indycarsuperfan.domain.model

import org.hungrytessy.indycarsuperfan.data.remote.dto.BaseStage
import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummary
import java.util.TreeSet

abstract class SingleRaceStage(): BaseStage() {
    var result: TreeSet<CompetitorEventSummary>? = null // from stageSummary.competitors
    var status: String? = null
}
