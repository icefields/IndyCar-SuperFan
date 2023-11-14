package org.hungrytessy.indycarsuperfan.network

import org.hungrytessy.indycarsuperfan.data.models.Drivers
import org.hungrytessy.indycarsuperfan.data.models.Seasons
import org.hungrytessy.indycarsuperfan.data.models.Venues
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
}
