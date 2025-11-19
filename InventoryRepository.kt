```kotlin
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InventoryRepository(private val inventoryDao: InventoryDao) {

    suspend fun checkItemExists(articleNumber: String): Boolean {
        return withContext(Dispatchers.IO) {
            inventoryDao.getItemByArticleNumber(articleNumber) != null
        }
    }

    suspend fun insertMasterItems(items: List<MasterItem>) {
        withContext(Dispatchers.IO) {
            inventoryDao.insertMasterItems(items)
        }
    }

    suspend fun getMasterItems(): List<MasterItem> {
        return withContext(Dispatchers.IO) {
            inventoryDao.getAllMasterItems()
        }
    }

    suspend fun updateScannedQuantity(articleNumber: String, quantity: Int) {
        withContext(Dispatchers.IO) {
            inventoryDao.updateScannedQuantity(articleNumber, quantity)
        }
    }

    fun getMasterItemsLiveData(): LiveData<List<MasterItem>> {
        return inventoryDao.getAllMasterItemsLive()
    }

    suspend fun getExportData(): List<ExportDTO> {
        return withContext(Dispatchers.IO) {
            inventoryDao.getExportData().map { item ->
                ExportDTO(
                    articleNumber = item.articleNumber,
                    scannedQuantity = item.scannedQuantity,
                    originalBalance = item.balance,
                    shelfLocation = item.shelfLocation
                )
            }
        }
    }
}
```

Note: You'll need to create the corresponding Room DAO interface (`InventoryDao`) with the required database operations. The repository assumes the following DAO methods exist:
- `getItemByArticleNumber(articleNumber: String): MasterItem?`
- `insertMasterItems(items: List<MasterItem>)`
- `getAllMasterItems(): List<MasterItem>`
- `updateScannedQuantity(articleNumber: String, quantity: Int)`
- `getAllMasterItemsLive(): LiveData<List<MasterItem>>`
- `getExportData(): List<MasterItem>`