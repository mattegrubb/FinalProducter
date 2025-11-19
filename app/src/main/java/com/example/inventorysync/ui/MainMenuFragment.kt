package com.example.inventorysync.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.inventorysync.databinding.FragmentMainMenuBinding
import com.example.inventorysync.utils.ExcelExporter
import com.example.inventorysync.data.database.InventoryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenuFragment : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanButton.setOnClickListener {
            // Navigate to ScanFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ScanFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.exportButton.setOnClickListener {
            exportScannedItems()
        }
    }

    private fun exportScannedItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = InventoryDatabase.getDatabase(requireContext())
            val scannedItems = database.inventoryDao().getAllScannedItems()

            val file = ExcelExporter.exportScannedItems(requireContext(), scannedItems)
            withContext(Dispatchers.Main) {
                if (file != null) {
                    Toast.makeText(requireContext(), "Exported to: ${file.absolutePath}", Toast.LENGTH_LONG).show()

                    // Optionally, open the file
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(file.toUri(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    startActivity(Intent.createChooser(intent, "Open Excel File"))
                } else {
                    Toast.makeText(requireContext(), "Export failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
