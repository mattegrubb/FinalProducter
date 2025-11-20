package com.example.inventorysync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    class Factory(private val repository: InventoryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}