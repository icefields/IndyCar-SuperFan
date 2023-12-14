package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DriverInfo (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("country_code") val countryCode: String?,
)
