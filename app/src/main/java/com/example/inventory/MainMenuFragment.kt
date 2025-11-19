```kotlin
package com.example.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainMenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnUserSettings).setOnClickListener {
            findNavController().navigate(R.id.action_to_user_settings)
        }

        view.findViewById<View>(R.id.btnImport).setOnClickListener {
            findNavController().navigate(R.id.action_to_import)
        }

        view.findViewById<View>(R.id.btnScan).setOnClickListener {
            findNavController().navigate(R.id.action_to_scan)
        }

        view.findViewById<View>(R.id.btnExport).setOnClickListener {
            findNavController().navigate(R.id.action_to_export)
        }
    }
}
```

You'll also need to add these color resources to your `res/values/colors.xml`: