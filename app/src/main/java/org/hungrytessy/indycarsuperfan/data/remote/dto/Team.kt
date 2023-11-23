package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("abbreviation") val abbreviation: String?,
)
