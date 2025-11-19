kotlin
package com.example.inventorysync.repository

import com.example.inventorysync.data.dao.InventoryDao
import com.example.inventorysync.data.entities.MasterItem
import com.example.inventorysync.data.entities.ScannedItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao
) {
    
    suspend fun bulkInsertMasterItems(masterItems: List<MasterItem>) {
        inventoryDao.bulkInsertMasterItems(masterItems)
    }
    
    suspend fun getMasterItemByArticleNumber(articleNumber: String): MasterItem? {
        return inventoryDao.getMasterItemByArticleNumber(articleNumber)
    }
    
    suspend fun insertOrUpdateScannedItem(scannedItem: ScannedItem) {
        val existingItem = inventoryDao.getScannedItemByArticleNumber(scannedItem.articleNumber)
        if (existingItem != null) {
            val updatedItem = scannedItem.copy(id = existingItem.id)
            inventoryDao.updateScannedItem(updatedItem)
        } else {
            inventoryDao.insertScannedItem(scannedItem)
        }
    }
    
    fun getAllMasterItems(): Flow<List<MasterItem>> {
        return inventoryDao.getAllMasterItems()
    }
    
    fun getAllScannedItems(): Flow<List<ScannedItem>> {
        return inventoryDao.getAllScannedItems()
    }
    
    fun getExportData(): Flow<List<ScannedItem>> { // Fixed return type
        return inventoryDao.getAllScannedItems()
    }
}
