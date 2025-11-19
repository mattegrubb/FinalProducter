package com.example.inventorysync.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventorysync.data.database.InventoryDatabase

class ScanViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            val database = InventoryDatabase.getDatabase(context)
            @Suppress("UNCHECKED_CAST")
            return ScanViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
