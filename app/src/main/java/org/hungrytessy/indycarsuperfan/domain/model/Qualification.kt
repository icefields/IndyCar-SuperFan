package org.hungrytessy.indycarsuperfan.domain.model

import org.hungrytessy.indycarsuperfan.data.remote.dto.BaseStage
import java.util.TreeSet

/**
 * "type": "qualifying"
 */
class Qualification() : BaseStage() {
    init {
        type = "qualifying"
    }
    var status: String? = null
    var qualificationStages: TreeSet<QualificationStage>? = null
}
