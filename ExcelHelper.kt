```kotlin
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream
import java.io.OutputStream

class ExcelHelper {

    fun parseExcel(inputStream: InputStream): List<MasterItem> {
        val workbook = XSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)
        val items = mutableListOf<MasterItem>()

        for (row in sheet) {
            if (row.rowNum == 0) continue // Skip header row

            val articleNumber = row.getCell(0)?.toString() ?: ""
            val description = row.getCell(1)?.toString() ?: ""
            val balance = row.getCell(2)?.toString()?.toIntOrNull() ?: 0
            val shelfLocation = row.getCell(3)?.toString() ?: ""

            if (articleNumber.isNotBlank()) {
                items.add(MasterItem(
                    articleNumber = articleNumber,
                    description = description,
                    balance = balance,
                    shelfLocation = shelfLocation
                ))
            }
        }

        workbook.close()
        return items
    }

    fun exportToExcel(data: List<ExportDTO>, outputStream: OutputStream) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Inventory Data")

        // Create header row
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Article Number")
        headerRow.createCell(1).setCellValue("Scanned Quantity")
        headerRow.createCell(2).setCellValue("Original Balance")
        headerRow.createCell(3).setCellValue("Shelf Location")

        // Add data rows
        data.forEachIndexed { index, item ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(item.articleNumber)
            row.createCell(1).setCellValue(item.scannedQuantity)
            row.createCell(2).setCellValue(item.originalBalance)
            row.createCell(3).setCellValue(item.shelfLocation)
        }

        // Auto-size columns
        for (i in 0..3) {
            sheet.autoSizeColumn(i)
        }

        workbook.write(outputStream)
        workbook.close()
    }
}

data class MasterItem(
    val articleNumber: String,
    val description: String,
    val balance: Int,
    val shelfLocation: String
)

data class ExportDTO(
    val articleNumber: String,
    val scannedQuantity: Int,
    val originalBalance: Int,
    val shelfLocation: String
)
```