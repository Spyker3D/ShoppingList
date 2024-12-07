package com.practicum.buyinglist.data.source.local

import androidx.room.Embedded
import androidx.room.Relation

data class LocalShoppingTaskWithGood(
    @Embedded val localShoppingTask: LocalShoppingTask,
    @Relation(
        parentColumn = "goodId",
        entityColumn = "id",
    )
    val good: LocalGood,
)
