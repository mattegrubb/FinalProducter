# Project Architecture: Native Android Inventory System (Offline)

**Purpose:** Standalone offline app for managing a large inventory (approx. 13,000 records) based on an Excel import.

**1. Technical Foundation & Architecture**
* **Language & OS:** Kotlin, Min SDK 29+ (Android 10+).
* **Architecture:** Modern MVVM (Model-View-ViewModel) with Repository pattern.
* **Asynchronicity:** Strictly use Kotlin Coroutines for all database and file handling (e.g., when importing 13,000 rows) to avoid UI freezes.
* **Libraries:** Room Database (SQLite), CameraX (or ML Kit) for scanning, Apache POI for Excel (.xlsx) I/O.

**2. Data Model (Room Database)**
The app has two main tables:
* **Entity 1: `MasterItem` (ReadOnly)**
    * **Purpose:** The original list imported from Excel. Read only in the background.
        * **Fields:** `articleNumber` (Indexed for fast lookups), `description`, `originalBalance`.
    * **Room Enforcement:** "ReadOnly" means the DAO for `MasterItem` should **not** contain any `update` or `delete` methods. Data is written only during the initial import from Excel; thereafter, only read operations are allowed via the DAO. Room has no built-in mechanism to mark an entity as read-only, so this must be ensured via code structure and convention.
* **Entity 2: `ScannedItem` (Writable)**
    * **Purpose:** Logs the user's inventory data.
                * **Fields:** `articleNumber`, `quantityScanned`, `shelfLocation` (1-20, **enforced at database level via SQLite CHECK constraint in Room Entity definition**), `userInitials`, `timestamp`.

**3. Core Functions & Flows**
* **HUD (Always Visible):** Displays `[Initials]`, `[Filename]`, and `[Count Scanned]` (current session).
* **Import:** Reads a local `.xlsx` file (Apache POI), parses data, and **Bulk-inserts** into the `MasterItem` table. Shows "File Loaded: [Filename]" in the HUD.
* **Scanning (Core Function):**
    * Uses CameraX/ML Kit to read QR/Barcodes.
    * **Logic:** Search `articleNumber` in `MasterItem`.
    * **If Match:** Open data entry screen with pre-filled info.
    * **If New:** Allow user to add with a temporary, editable ID prefix (e.g., '00').
* **Export:** Generates an `.xlsx` or `.csv` file to local storage based on data from `ScannedItem`.
    * **Column Requirements (STRICT):** Article Number, Scanned Quantity, Original Balance/Description, Shelf Location.

---

### 🔨 New Workflow with GPT-4o in Android Studio

Since you have already run some prompts on Hugging Face (which likely provided you with dependencies and database files), the next step is to **verify and correct** that code using GPT-4o.

#### Step A: Setting Context (One-off/As needed)
1.  Open your GPT-4o plugin/chat window.
2.  Paste the **Architectural Blueprint** above.
3.  Give the instruction: `"I have now pasted the project's full architecture. Use this context for all future code questions."`

#### Step B: Verify and Implement the Database
Now you can use GPT-4o to ensure your existing code matches the blueprint.

**Suggested editing prompt for GPT-4o:**

> "Based on the architectural blueprint, review my current `InventoryDatabase.kt` and `MasterItem.kt`. Ensure that `MasterItem` has a unique index on `articleNumber` and that `InventoryDatabase` uses the Singleton pattern and is thread-safe with Coroutines."

**Once you have verified the database code structure, we will proceed to creating the Repository and Excel handling logic.**

**Do you want me to formulate the prompt for creating `ExcelHelper` and `InventoryRepository` based on the blueprint?**