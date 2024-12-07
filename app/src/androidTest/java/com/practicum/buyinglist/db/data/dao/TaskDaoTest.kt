
package com.practicum.buyinglist.db.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.practicum.spisokpokupok.core.data.ShoppingListDatabase
import com.practicum.spisokpokupok.core.data.entity.LocalShoppingTask
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ShoppingTaskDaoTest {
    private lateinit var database: ShoppingListDatabase

    @Before
    fun initDb() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    getApplicationContext(),
                    ShoppingListDatabase::class.java,
                ).allowMainThreadQueries()
                .build()
    }

    @Test
    fun insertTaskAndGetTasks() =
        runTest {
            val task =
                LocalShoppingTask(
                    shoppingListId = "shoppingListId",
                    goodId = "goodId",
                    quantity = 1,
                    quantityType = "quantityType",
                    id = "id",
                    isCompleted = false,
                    position = 0,
                )
            database.ShoppingTaskDao().upsert(task)

            val tasks = database.ShoppingTaskDao().observeAllWithGoods().first()

            assertEquals(1, tasks.size)
            assertEquals(task, tasks[0])
        }
}
