package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

/**
 * only used to build a list of all drivers
 */
class Drivers(@SerializedName("drivers") val drivers: HashMap<String, Driver> = HashMap()) {
    constructor(drs: List<Driver>): this() {
        for (dr in drs) {
            dr.competitor?.let {
                drivers[it.id] = dr
            }
        }
    }
}

data class Driver(
    @SerializedName("competitor") val competitor: DriverInfo?,
    @SerializedName("teams") val teams: Any?,
    @SerializedName("info") val info: InfoDriver?
) {
    fun getTeamsString(): String {
        val sb: StringBuilder = java.lang.StringBuilder()
        if (teams is List<*>) {
            for (team in teams) {
                if (team is LinkedTreeMap<*, *>) {
                    sb.append(team["name"])
                    sb.append(", ")
                }
            }
        }
        val teamsStr = sb.toString()
        return if(teamsStr.isNotBlank() && teamsStr.length > 1) {
            teamsStr.substring(startIndex = 0, endIndex = teamsStr.length - 2)
        } else {
            teamsStr
        }
    }
}

data class InfoDriver (
    @SerializedName("country_of_residence") val countryOfResidence: String?,
    @SerializedName("country_of_residence_id") val countryOfResidenceId: String?,
    @SerializedName("country_code_of_residence") val countryCodeOfResidence: String?,
    @SerializedName("url_official") val officialWebsite: String?,
    @SerializedName("weight") val weight: String?,
    @SerializedName("dateofbirth") val dateOfBirth: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("placeofbirth") val placeOfBirth: String?,
    @SerializedName("salary") val salary: String?,
    @SerializedName("debut") val debut: String?,
    @SerializedName("first_points") val firstPoints: String?,
)
