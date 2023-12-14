package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.domain.model.Team

data class TeamDto (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("abbreviation") val abbreviation: String?,
)

fun TeamDto.toTeam(): Team = Team(id, name, abbreviation)