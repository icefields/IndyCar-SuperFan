package org.hungrytessy.indycarsuperfan.domain.model

import org.hungrytessy.indycarsuperfan.R
import java.util.TreeSet

class RaceWeekend: BaseStage() {
    var status: String? = null
    var venue: Venue? = null
    var race: Race? = null
    var practice: TreeSet<Practice>? = null
    var qualification: Qualification? = null
    var trackDrawable: Int = R.drawable.img_placeholder
}
