package org.hungrytessy.indycarsuperfan.data.remote.network

const val PATH_DRIVERS = "drivers_01.json"
const val PATH_SEASONS = "seasons_03.json"
const val PATH_VENUES = "venues_01.json"

enum class IndyPath(val value: String) {
    DRIVERS(PATH_DRIVERS),
    SEASONS(PATH_SEASONS),
    VENUES(PATH_VENUES);

    companion object {
        fun getPath(str: String): IndyPath {
            if (str == PATH_DRIVERS) return DRIVERS
            if (str == PATH_SEASONS) return SEASONS
            if (str == PATH_VENUES) return VENUES
            return SEASONS
        }
    }
}
