package com.example.inventorysync.utils

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayOutputStream

class ExcelExporterTest {

    @Test
    fun `test export to Excel`() {
        // Arrange
        val exporter = ExcelExporter()
        val data = listOf(
            listOf("Header1", "Header2"),
            listOf("Row1Col1", "Row1Col2"),
            listOf("Row2Col1", "Row2Col2")
        )

        // Act
        val outputStream = ByteArrayOutputStream()
        exporter.exportToExcel(data, outputStream)
        val workbook = XSSFWorkbook(outputStream.toByteArray().inputStream())

        // Assert
        val sheet = workbook.getSheetAt(0)
        assertEquals("Header1", sheet.getRow(0).getCell(0).stringCellValue)
        assertEquals("Row1Col1", sheet.getRow(1).getCell(0).stringCellValue)
        workbook.close()
    }
}