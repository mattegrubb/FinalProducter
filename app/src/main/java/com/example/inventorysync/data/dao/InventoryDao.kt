package com.example.inventorysync.data.dao

import androidx.room.*
import com.example.inventorysync.data.entities.MasterItem
import com.example.inventorysync.data.entities.ScannedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsertMasterItems(masterItems: List<MasterItem>)

    @Query("SELECT * FROM master_items WHERE articleNumber = :articleNumber LIMIT 1")
    suspend fun getMasterItemByArticleNumber(articleNumber: String): MasterItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScannedItem(scannedItem: ScannedItem)

    @Query("SELECT * FROM scanned_items")
    fun getAllScannedItems(): Flow<List<ScannedItem>>
}
