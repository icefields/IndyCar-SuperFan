package org.hungrytessy.indycarsuperfan.data.remote.network

import org.hungrytessy.indycarsuperfan.common.Resource
import org.hungrytessy.indycarsuperfan.data.remote.dto.Drivers
import org.hungrytessy.indycarsuperfan.data.remote.dto.Seasons
import org.hungrytessy.indycarsuperfan.data.remote.dto.Venues
import retrofit2.http.GET

/**
 * Main network interface which will fetch a new welcome title for us
 */
interface MainNetwork {
    @GET(PATH_DRIVERS)
    suspend fun getDrivers(): Drivers

    @GET(PATH_SEASONS)
    suspend fun getSeasons(): Seasons

    @GET(PATH_VENUES)
    suspend fun getVenues(): Venues

    companion object {
        const val API_KEY = "Q63Y9NX3TUF587NF"
        const val BASE_URL = "http://localhost/"
    }
}
