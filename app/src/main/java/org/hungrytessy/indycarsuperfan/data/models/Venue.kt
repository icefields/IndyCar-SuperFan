package org.hungrytessy.indycarsuperfan.data.models

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.data.IndyDataStore.drivers

/**
 * only used to build a list of all venues
 */
class Venues(@SerializedName("venues") val venues: HashMap<String, Venue> = HashMap()) {
    constructor(vns: List<Venue>): this() {
        for (vn in vns) {
            venues[vn.id] = vn
        }
    }
}

data class Venue (
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


/*
"curves_left": 4, "curves_right": 0, "url_official"
  final String id;
  final String? name;
  final String? city;
  final String? country;
  final String? coordinates;
  final String? countryCode;
  final int? curvesRight;
  final int? curvesLeft;
  final int? debut;
  final int? length;
  final String? urlOfficial;
  final String? timezone;
 */