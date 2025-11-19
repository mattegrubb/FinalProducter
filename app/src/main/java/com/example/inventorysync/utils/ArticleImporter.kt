package com.example.inventorysync.utils

import android.content.Context
import android.util.Log
import com.example.inventorysync.data.database.InventoryDatabase
import com.example.inventorysync.data.entities.MasterItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException

object ArticleImporter {

    fun importStandardArticles(context: Context, filePath: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file = File(filePath)
                if (!file.exists()) {
                    Log.e("ArticleImporter", "File not found: $filePath")
                    return@launch
                }

                val inputStream = FileInputStream(file)
                val workbook = WorkbookFactory.create(inputStream)
                val sheet = workbook.getSheetAt(0)

                val articles = mutableListOf<MasterItem>()

                for (row in sheet) {
                    if (row.rowNum == 0) continue // Skip header row

                    val articleNumber = row.getCell(0)?.stringCellValue ?: ""
                    val description = row.getCell(1)?.stringCellValue ?: ""
                    val originalBalance = row.getCell(2)?.numericCellValue?.toInt() ?: 0

                    if (articleNumber.isNotEmpty() && description.isNotEmpty()) {
                        articles.add(
                            MasterItem(
                                articleNumber = articleNumber,
                                description = description,
                                originalBalance = originalBalance
                            )
                        )
                    }
                }

                val database = InventoryDatabase.getDatabase(context)
                database.inventoryDao().insertBulkMasterItems(articles)

                Log.d("ArticleImporter", "Successfully imported ${articles.size} articles.")
            } catch (e: IOException) {
                Log.e("ArticleImporter", "Error reading the file: $filePath", e)
            } catch (e: IllegalArgumentException) {
                Log.e("ArticleImporter", "Invalid argument error while importing articles", e)
            } catch (e: android.database.sqlite.SQLiteException) {
                Log.e("ArticleImporter", "Database error while importing articles", e)
            } catch (e: Exception) {
                Log.e("ArticleImporter", "Unexpected error while importing articles", e)
            }
        }
    }
}
