package com.practicum.buyinglist.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.buyinglist.domain.model.Good

@Entity(
    tableName = "good",
)
data class LocalGood(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: String,
)

fun LocalGood.toExternal() =
    Good(
        id = id,
        name = name,
    )

fun Good.toLocal() =
    LocalGood(
        id = id,
        name = name,
    )
