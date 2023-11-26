package org.hungrytessy.indycarsuperfan.data

import org.hungrytessy.indycarsuperfan.data.remote.dto.Driver

object IndyDataStore {
    // TODO refactor, grab this from repository (SINGLE SOURCE OF TRUTH PATTERN)
    val drivers: HashMap<String, Driver> = HashMap()
}
