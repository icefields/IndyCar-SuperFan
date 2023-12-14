package org.hungrytessy.indycarsuperfan.domain.model

import com.google.gson.annotations.SerializedName
import org.hungrytessy.indycarsuperfan.data.remote.dto.VenueDto

data class Venue (
    val id: String,
    val name: String?,
    val city: String?,
    val country: String?,
    val coordinates: String?,
    val countryCode: String?,
    val urlOfficial: String?,
    val timezone: String?,
    val curvesRight: Int?,
    val curvesLeft: Int?,
    val debut: Int?,
    val length: Int?,
)
