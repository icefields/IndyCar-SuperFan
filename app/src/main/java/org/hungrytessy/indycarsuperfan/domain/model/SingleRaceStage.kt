package org.hungrytessy.indycarsuperfan.domain.model

import java.util.TreeSet

abstract class SingleRaceStage: BaseStage() {
    var result: TreeSet<CompetitorEventSummary>? = null // from stageSummary.competitors
    var status: String? = null
}
