package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.Embedded
import androidx.room.Relation

class LocalShoppingTaskWithGood(
    @Embedded val localShoppingTask: LocalShoppingTask,
    @Relation(
        parentColumn = "goodId",
        entityColumn = "id",
    )
    val good: LocalGood,
)
