package org.hungrytessy.indycarsuperfan.data.remote.dto

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import java.util.ArrayList

/**
 * only used to build a list of all drivers
 */
class Drivers(
    @SerializedName("drivers") val drivers: HashMap<String, DriverDto> = HashMap()
) {
    constructor(drs: List<DriverDto>): this() {
        for (dr in drs) {
            dr.competitor?.let {
                drivers[it.id] = dr
            }
        }
    }
}

data class DriverDto(
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

    fun getTeamsList(): List<TeamDto> =
        if (teams is List<*>) {
            val list = ArrayList<TeamDto>()
            for (team in teams) {
                if (team is LinkedTreeMap<*, *>) {
                    list.add(
                        TeamDto(
                            team["id"] as String,
                            team["name"]?.let { it as String },
                            team["abbreviation"]?.let { it as String },
                        ))
                }
            }
            list
        } else {
            listOf<TeamDto>()
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

fun DriverDto.getAssetUrlHeadshot(): Uri {
    val firstName = (competitor?.name ?: "").split(", ")[1].lowercase()
    val lastName = (competitor?.name ?: "").split(", ")[0].lowercase().replace(" ", "_")
    return Uri.parse("file:///android_asset/images/headshots/img_${firstName[0]}_${lastName}.png")
}

fun DriverDto.toDriver(): Driver = Driver(
    teams = getTeamsList().map { it.toTeam() },
    id = competitor?.id ?: "${System.currentTimeMillis()}",
    name = competitor?.name,
    gender = competitor?.gender,
    nationality = competitor?.nationality,
    countryCode = competitor?.countryCode,
    countryOfResidence = info?.countryOfResidence,
    countryOfResidenceId = info?.countryOfResidenceId,
    countryCodeOfResidence = info?.countryCodeOfResidence,
    officialWebsite = info?.officialWebsite,
    weight = info?.weight,
    dateOfBirth = info?.dateOfBirth,
    height = info?.height,
    placeOfBirth = info?.placeOfBirth,
    salary = info?.salary,
    debut = info?.debut,
    firstPoints = info?.firstPoints,
    headShotUri = getAssetUrlHeadshot()
)
