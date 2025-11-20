package com.example.inventorysync

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.inventorysync.repository.InventoryRepository
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val repository by lazy { InventoryRepository(/* Pass DAO or dependencies */) }
    private val viewModel: SharedViewModel by viewModels {
        SharedViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set initial user (you can replace with actual user logic)
        viewModel.setUserInitials("AD")

        // Observe ViewModel changes
        lifecycleScope.launchWhenStarted {
            viewModel.loggedInUserInitials.collect { initials ->
                findViewById<TextView>(R.id.tvUserInitials).text = initials
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.currentFileName.collect { fileName ->
                findViewById<TextView>(R.id.tvFileName).text = fileName
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.totalScannedCount.collect { count ->
                findViewById<TextView>(R.id.tvScannedCount).text = 
                    "Scanned: $count"
            }
        }

        // Load main menu by default
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, MainMenuFragment())
        }
    }
}