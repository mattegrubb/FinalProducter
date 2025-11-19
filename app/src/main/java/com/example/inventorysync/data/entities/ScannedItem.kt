package com.example.inventorysync.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scanned_items")
data class ScannedItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleNumber: String,
    val quantityScanned: Int,
    val shelfLocation: String,
    val userInitials: String,
    val timestamp: Long
)
