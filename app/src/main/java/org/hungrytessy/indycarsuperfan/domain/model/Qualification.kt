package org.hungrytessy.indycarsuperfan.domain.model

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
