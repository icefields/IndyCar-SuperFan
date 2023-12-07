package org.hungrytessy.indycarsuperfan.domain.model

import org.hungrytessy.indycarsuperfan.data.remote.dto.Venue
import java.util.TreeSet

class RaceWeekend(): BaseStage() {
    var status: String? = null
    var venue: Venue? = null
    var race: Race? = null
    var practice: TreeSet<Practice>? = null
    var qualification: Qualification? = null
}
