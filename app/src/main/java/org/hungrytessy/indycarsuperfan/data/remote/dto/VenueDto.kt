package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.domain.model.Venue

/**
 * only used to build a list of all venues
 */
class Venues(
    @SerializedName("venues") val venues: HashMap<String, VenueDto> = HashMap()
) {
    constructor(vns: List<VenueDto>): this() {
        for (vn in vns) {
            venues[vn.id] = vn
        }
    }
}

data class VenueDto (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("coordinates") val coordinates: String?,
    @SerializedName("country_code") val countryCode: String?,
    @SerializedName("url_official") val urlOfficial: String?,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("curves_right") val curvesRight: Int?,
    @SerializedName("curves_left") val curvesLeft: Int?,
    @SerializedName("debut") val debut: Int?,
    @SerializedName("length") val length: Int?,
)

fun VenueDto.toVenue() = Venue(
    id = id,
    name = name,
    city = city,
    country = country,
    coordinates = coordinates,
    countryCode = countryCode,
    urlOfficial = urlOfficial,
    timezone = timezone,
    curvesRight = curvesRight,
    curvesLeft = curvesLeft,
    debut = debut,
    length = length
)
