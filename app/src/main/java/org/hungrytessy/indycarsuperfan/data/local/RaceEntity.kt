package org.hungrytessy.indycarsuperfan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RaceEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)