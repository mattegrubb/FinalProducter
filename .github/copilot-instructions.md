# Copilot Instructions for InventorySync Pro

Welcome to the InventorySync Pro codebase! This document provides essential guidance for AI coding agents to be productive in this project. Follow these instructions to understand the architecture, workflows, and conventions specific to this repository.

## Project Overview
InventorySync Pro is an offline inventory management app for Android, designed to handle large datasets (13,000+ items). It uses:
- **Room Database** for local data storage.
- **CameraX** and **ML Kit** for barcode scanning.
- **Apache POI** for Excel file operations.
- **Coroutines** for asynchronous programming.

Key directories:
- `app/src/main/java/com/example/inventory/`: Core app logic, including barcode scanning, database operations, and UI components.
- `app/src/main/java/com/example/inventorysync/`: Synchronization logic, repository patterns, and utility classes.
- `app/src/main/res/`: Android resource files (layouts, colors, etc.).

## Architecture
The app follows a modular MVVM (Model-View-ViewModel) architecture:
- **Model**: Data layer, including `Room` entities, DAOs, and the `InventoryDatabase`.
- **ViewModel**: Handles UI-related data and business logic. Example: `SharedViewModel`, `ScanViewModel`.
- **View**: XML layouts and fragments like `MainMenuFragment` and `ScanFragment`.

### Data Flow
1. **Barcode Scanning**: `BarcodeAnalyzer` processes camera input using ML Kit.
2. **Database Operations**: `InventoryDao` interfaces with `Room` for CRUD operations.
3. **Excel Export/Import**: `ExcelExporter` and `ArticleImporter` handle file I/O with Apache POI.

## Developer Workflows
### Build and Run
1. Open the project in Android Studio.
2. Use the Gradle wrapper (`gradlew`) to build the project:
   ```bash
   ./gradlew build
   ```
3. Run the app on an emulator or connected device.

### Testing
- Unit tests are located in `src/test/java/`.
- Run tests with:
  ```bash
  ./gradlew test
  ```

### Debugging
- Use Android Studio's debugger for breakpoints and logcat for runtime logs.
- Common issues:
  - Missing `ArtikelListaGrund.xlsx` file in `C:/dev/`.
  - Permissions for camera and file storage.

## Project-Specific Conventions
- **Dependency Injection**: Manual injection is used; no Dagger/Hilt.
- **Error Handling**: Use `try-catch` blocks for database and file operations.
- **Naming**: Follow Android conventions (e.g., `camelCase` for variables, `PascalCase` for classes).

## Integration Points
- **External Libraries**:
  - `ML Kit`: Barcode scanning.
  - `Apache POI`: Excel file handling.
- **Cross-Component Communication**:
  - Use `SharedViewModel` for data sharing between fragments.

## Examples
### Adding a New DAO
1. Create a new interface in `data/dao/`:
   ```kotlin
   @Dao
   interface NewEntityDao {
       @Insert
       fun insert(entity: NewEntity)

       @Query("SELECT * FROM new_entity")
       fun getAll(): List<NewEntity>
   }
   ```
2. Add the DAO to `InventoryDatabase`.

### Adding a New Fragment
1. Create a new layout in `res/layout/`.
2. Create a new `Fragment` class in `ui/`.
3. Update navigation in `MainMenuFragment`.

---

For questions or updates, refer to the `README.md` or contact the repository owner.