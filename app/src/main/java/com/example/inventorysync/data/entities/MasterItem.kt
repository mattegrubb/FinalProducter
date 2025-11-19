package com.example.inventorysync.data.entities

)
    val originalBalance: Int
    val description: String,
    val articleNumber: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
data class MasterItem(
)
    indices = [Index(value = ["articleNumber"], unique = true)]
    tableName = "master_items",
@Entity(

import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Entity
