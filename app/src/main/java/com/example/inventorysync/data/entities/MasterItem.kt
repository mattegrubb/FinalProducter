package com.example.inventorysync.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "master_items",
    indices = [Index(value = ["articleNumber"], unique = true)]
)
data class MasterItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val articleNumber: String,
    val description: String,
    val originalBalance: Int
)
