package com.microgoalplanner.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.ViewModelFactory
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currency = view.findViewById<EditText>(R.id.inputCurrency)
        val notifications = view.findViewById<Switch>(R.id.switchNotifications)
        val info = view.findViewById<TextView>(R.id.textSettingsInfo)

        view.findViewById<Button>(R.id.buttonSaveSettings).setOnClickListener {
            viewModel.saveCurrency(currency.text.toString().ifBlank { "USD" })
            viewModel.saveNotifications(notifications.isChecked)
        }
        view.findViewById<Button>(R.id.buttonClearData).setOnClickListener { viewModel.clearAllData() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.settings.collect {
                currency.setText(it.currency)
                notifications.isChecked = it.notificationsEnabled
                info.text = "Версия 1.0\nПриватность: ${if (it.privacyMode) "Вкл" else "Выкл"}"
            }
        }
    }
}
