package org.hungrytessy.indycarsuperfan.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.TreeSet

data class Seasons(
    @SerializedName("stages")
    val stages: TreeSet<SeasonDto>
)
