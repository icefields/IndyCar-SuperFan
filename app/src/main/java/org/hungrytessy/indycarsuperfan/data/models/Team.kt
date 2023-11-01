package org.hungrytessy.indycarsuperfan.data.models

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("abbreviation") val abbreviation: String?,
)

/*
class Team {
  final String id;
  final String? name;
 */