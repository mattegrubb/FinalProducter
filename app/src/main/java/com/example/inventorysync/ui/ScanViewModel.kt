package com.example.inventorysync.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorysync.data.database.InventoryDatabase
import com.example.inventorysync.data.entities.MasterItem
import com.example.inventorysync.data.entities.ScannedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class ScanViewModel(private val database: InventoryDatabase) : ViewModel() {

    fun insertScannedItem(scannedItem: ScannedItem) {
        viewModelScope.launch {
            database.inventoryDao().insertOrUpdateScannedItem(scannedItem)
        }
    }

    suspend fun findMasterItemByArticleNumber(articleNumber: String): MasterItem? {
        return withContext(Dispatchers.IO) {
            database.inventoryDao().findMasterItemByArticleNumber(articleNumber)
        }
    }
}
