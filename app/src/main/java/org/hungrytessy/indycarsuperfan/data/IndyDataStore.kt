package org.hungrytessy.indycarsuperfan.data

import org.hungrytessy.indycarsuperfan.domain.model.Driver

object IndyDataStore {
    // TODO refactor, grab this from repository (SINGLE SOURCE OF TRUTH PATTERN)
    var drivers: HashMap<String, Driver> = HashMap()
}
