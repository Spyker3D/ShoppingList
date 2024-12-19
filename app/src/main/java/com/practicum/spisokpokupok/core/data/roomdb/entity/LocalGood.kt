package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "good",
)
data class LocalGood(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)