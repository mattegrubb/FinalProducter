```kotlin
package com.example.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: InventoryRepository) : ViewModel() {
    private val _totalScannedCount = MutableStateFlow(0)
    val totalScannedCount: StateFlow<Int> = _totalScannedCount

    private val _loggedInUserInitials = MutableStateFlow("")
    val loggedInUserInitials: StateFlow<String> = _loggedInUserInitials

    private val _currentFileName = MutableStateFlow("No file selected")
    val currentFileName: StateFlow<String> = _currentFileName

    fun updateTotalScanned(count: Int) {
        _totalScannedCount.value = count
    }

    fun setUserInitials(initials: String) {
        _loggedInUserInitials.value = initials
    }

    fun setCurrentFileName(name: String) {
        _currentFileName.value = name
    }
}
```