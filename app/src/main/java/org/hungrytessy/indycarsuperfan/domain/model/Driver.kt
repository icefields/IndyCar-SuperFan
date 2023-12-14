package org.hungrytessy.indycarsuperfan.domain.model

import android.net.Uri


data class Driver(
    val teams: List<Team>,
    val id: String,
    val name: String?,
    val gender: String?,
    val nationality: String?,
    val countryCode: String?,
    val countryOfResidence: String?,
    val countryOfResidenceId: String?,
    val countryCodeOfResidence: String?,
    val officialWebsite: String?,
    val weight: String?,
    val dateOfBirth: String?,
    val height: String?,
    val placeOfBirth: String?,
    val salary: String?,
    val debut: String?,
    val firstPoints: String?,
    val headShotUri: Uri
) {
    fun getTeamsString(): String {
        val sb: StringBuilder = java.lang.StringBuilder()
        for (team in teams) {
            sb.append(team.name)
            sb.append(", ")
        }

        val teamsStr = sb.toString()
        return if(teamsStr.isNotBlank() && teamsStr.length > 1) {
            teamsStr.substring(startIndex = 0, endIndex = teamsStr.length - 2)
        } else {
            teamsStr
        }
    }
}
