package com.practicum.spisokpokupok.core.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalGood

@Dao
interface GoodDao {
    @Upsert
    suspend fun upsert(good: LocalGood)

    @Upsert
    suspend fun upsertAll(goodList: List<LocalGood>)

    @Query("DELETE FROM good WHERE id = :goodId")
    suspend fun deleteGood(goodId: String)

    @Query("SELECT * FROM good")
    suspend fun getAllGoods(): List<LocalGood>

    @Query("SELECT id FROM good WHERE name = :name")
    suspend fun getGoodIdByName(name: String): Int
}
