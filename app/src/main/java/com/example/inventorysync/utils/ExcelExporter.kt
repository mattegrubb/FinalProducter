package com.example.inventorysync.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.inventorysync.data.entities.ScannedItem
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ExcelExporter {

    fun exportScannedItems(context: Context, scannedItems: List<ScannedItem>): File? {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Scanned Items")

        // Create header row
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("ID")
        headerRow.createCell(1).setCellValue("Article Number")
        headerRow.createCell(2).setCellValue("Quantity Scanned")
        headerRow.createCell(3).setCellValue("Shelf Location")
        headerRow.createCell(4).setCellValue("User Initials")
        headerRow.createCell(5).setCellValue("Timestamp")

        // Populate data rows
        for ((index, item) in scannedItems.withIndex()) {
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(item.id.toDouble())
            row.createCell(1).setCellValue(item.articleNumber)
            row.createCell(2).setCellValue(item.quantityScanned.toDouble())
            row.createCell(3).setCellValue(item.shelfLocation)
            row.createCell(4).setCellValue(item.userInitials)
            row.createCell(5).setCellValue(item.timestamp.toDouble())
        }

        // Save the file
        val fileName = "ScannedItems_${System.currentTimeMillis()}.xlsx"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        return try {
            FileOutputStream(file).use { outputStream ->
                workbook.write(outputStream)
            }
            workbook.close()
            file
        } catch (e: IOException) {
            Log.e("ExcelExporter", "Error writing Excel file", e)
            null
        }
    }
}
