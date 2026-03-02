package com.microgoalplanner.ui.stages

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.ViewModelFactory
import com.microgoalplanner.validation.ValidationRules
import java.time.LocalDate
import kotlinx.coroutines.launch

class StagesOverviewFragment : Fragment(R.layout.fragment_stages) {
    private val viewModel: StagesViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val search = view.findViewById<EditText>(R.id.inputSearchStages)
        val listText = view.findViewById<TextView>(R.id.textStagesList)

        view.findViewById<Button>(R.id.buttonCreateStage).setOnClickListener { showCreateStageDialog() }
        search.doAfterTextChanged { viewModel.setSearch(it?.toString().orEmpty()) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stages.collect { stages ->
                listText.text = if (stages.isEmpty()) "Нет этапов" else stages.joinToString("\n\n") {
                    "#${it.goalId} ${it.name}\n${it.amount} до ${it.deadline}\nСтатус: ${it.status}"
                }
            }
        }
    }

    private fun showCreateStageDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_stage, null)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Новый этап")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val goalId = dialogView.findViewById<TextInputEditText>(R.id.inputStageGoalId).text.toString().toLongOrNull() ?: 0L
                val name = dialogView.findViewById<TextInputEditText>(R.id.inputStageName).text.toString()
                val amount = dialogView.findViewById<TextInputEditText>(R.id.inputStageAmount).text.toString().toLongOrNull() ?: 0L
                val deadline = LocalDate.parse(dialogView.findViewById<TextInputEditText>(R.id.inputStageDeadline).text.toString())
                val description = dialogView.findViewById<TextInputEditText>(R.id.inputStageDescription).text.toString()
                val priority = dialogView.findViewById<TextInputEditText>(R.id.inputStagePriority).text.toString().toIntOrNull() ?: 1

                if (goalId > 0 && ValidationRules.validateText(name) && ValidationRules.validateNumber(amount) && ValidationRules.validateDate(deadline)) {
                    viewModel.createStage(goalId, name, amount, deadline, description, priority)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
