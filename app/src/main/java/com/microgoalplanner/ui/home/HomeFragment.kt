package com.microgoalplanner.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.ViewModelFactory
import com.microgoalplanner.validation.ValidationRules
import java.time.LocalDate
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val summary = view.findViewById<TextView>(R.id.textSummary)
        view.findViewById<Button>(R.id.buttonAddGoal).setOnClickListener { showCreateGoalDialog() }
        view.findViewById<Button>(R.id.buttonOpenAnalytics).setOnClickListener {
            findNavController().navigate(R.id.analyticsFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.goals.collect { goals ->
                val active = goals.count { it.goal.status.name == "ACTIVE" }
                val completed = goals.count { it.goal.status.name == "COMPLETED" }
                summary.text = "Активных целей: $active\nЗавершённых: $completed\nВсего: ${goals.size}"
            }
        }
    }

    private fun showCreateGoalDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_goal, null)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Новая цель")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val name = dialogView.findViewById<TextInputEditText>(R.id.inputGoalName).text.toString()
                val amount = dialogView.findViewById<TextInputEditText>(R.id.inputGoalAmount).text.toString().toLongOrNull() ?: 0L
                val currency = dialogView.findViewById<TextInputEditText>(R.id.inputGoalCurrency).text.toString().ifBlank { "USD" }
                val deadline = LocalDate.parse(dialogView.findViewById<TextInputEditText>(R.id.inputGoalDeadline).text.toString())

                if (ValidationRules.validateText(name) && ValidationRules.validateNumber(amount) && ValidationRules.validateDate(deadline)) {
                    viewModel.quickCreateGoal(name, amount, currency, deadline)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
