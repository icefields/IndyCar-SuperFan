package org.hungrytessy.indycarsuperfan.data.models

import com.google.gson.annotations.SerializedName

data class DriverInfo (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("country_code") val countryCode: String?,
)

/*
class DriverInfo {
  final String id;
  final String? name;
  final String? gender;
  final String? nationality;
  final String? countryCode;
 */