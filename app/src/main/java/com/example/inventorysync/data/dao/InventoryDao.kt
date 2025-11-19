package com.example.inventorysync.data.dao

import androidx.room.*
import com.example.inventorysync.data.entities.MasterItem
import com.example.inventorysync.data.entities.ScannedItem

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBulkMasterItems(masterItems: List<MasterItem>)

    @Query("SELECT * FROM master_items WHERE articleNumber = :articleNumber LIMIT 1")
    suspend fun findMasterItemByArticleNumber(articleNumber: String): MasterItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateScannedItem(scannedItem: ScannedItem)

    @Query("SELECT * FROM scanned_items")
    suspend fun getAllScannedItems(): List<ScannedItem>
}
