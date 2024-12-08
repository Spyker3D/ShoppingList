package com.practicum.spisokpokupok.core.data.roomDb.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood

@Dao
interface GoodDao {
    @Upsert
    suspend fun upsert(good: LocalGood)

    @Upsert
    suspend fun upsertAll(goodList: List<LocalGood>)
}
