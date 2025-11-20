package com.example.inventorysync.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.room.Room
import com.example.inventorysync.data.database.InventoryDatabase
import com.example.inventorysync.data.entities.InventoryItem
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InventoryDaoTest {

    private lateinit var database: InventoryDatabase
    private lateinit var dao: InventoryDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            context, InventoryDatabase::class.java
        ).build()
        dao = database.inventoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun writeAndReadInventoryItem() {
        val item = InventoryItem(id = 1, name = "Test Item", quantity = 10)
        dao.insert(item)
        val retrievedItem = dao.getItemById(1)
        assertNotNull(retrievedItem)
        assertEquals(item.name, retrievedItem?.name)
    }
}