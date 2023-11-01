package org.hungrytessy.indycarsuperfan.data.models

import java.util.TreeSet

data class Season(
    var races: TreeSet<Stage>?,
    var seasonSummary: EventSummaryDetail?
): BaseStage()
