package com.practicum.buyinglist.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "good",
)
data class LocalGood(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: String,
)
