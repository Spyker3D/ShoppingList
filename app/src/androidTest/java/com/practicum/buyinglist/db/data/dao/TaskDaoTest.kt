package com.practicum.buyinglist.db.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.practicum.spisokpokupok.core.data.ShoppingListDatabase
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalActualShoppingList
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalActualShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingList
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTask
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTaskWithGood
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
                    goodId = 1,
                    quantity = 1,
                    quantityType = "quantityType",
                    id = 1,
                    isCompleted = false,
                    position = 0,
                )
            val good =
                LocalGood(
                    name = "name",
                    id = 1,
                )
            val taskWithGood =
                LocalShoppingTaskWithGood(
                    localShoppingTask = task,
                    good = good,
                )
            database.GoodDao().upsert(good)
            database.ShoppingTaskDao().upsert(task)

            val tasks = database.ShoppingTaskDao().observeAllWithGoods().first()

            assertEquals(1, tasks.size)
            assertEquals(taskWithGood, tasks[0])
        }

    @Test
    fun insertListAndGetLists() =
        runTest {
            val list =
                LocalShoppingList(
                    name = "name",
                    id = "id",
                )
            val actualList =
                LocalActualShoppingList(
                    shoppingListId = "id",
                    isFavorite = false,
                    id = 1,
                )
            val taskWithGood =
                LocalActualShoppingListWithName(
                    localActualShoppingList = actualList,
                    localShoppingList = list,
                )
            database.ShoppingListDao().upsert(list)
            database.ShoppingListDao()

            val tasks = database.ShoppingTaskDao().observeAllWithGoods().first()

            assertEquals(1, tasks.size)
            assertEquals(taskWithGood, tasks[0])
        }
}
