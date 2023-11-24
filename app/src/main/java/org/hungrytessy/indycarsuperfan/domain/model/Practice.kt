package org.hungrytessy.indycarsuperfan.domain.model

import org.hungrytessy.indycarsuperfan.data.remote.dto.BaseStage
import java.util.TreeSet

/**
 * "type": "practice"
 */
class Practice() : SingleRaceStage() {
    init {
        type = "practice"
    }
}
