package com.practicum.spisokpokupok.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "good",
)
data class LovalGood(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: String,
)
