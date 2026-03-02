package com.microgoalplanner.ui.goals

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.ViewModelFactory
import kotlinx.coroutines.launch

class GoalsListFragment : Fragment(R.layout.fragment_goals) {
    private val viewModel: GoalsViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val search = view.findViewById<EditText>(R.id.inputSearchGoals)
        val listText = view.findViewById<TextView>(R.id.textGoalsList)

        search.doAfterTextChanged { viewModel.setSearch(it?.toString().orEmpty()) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.goals.collect { goals ->
                listText.text = if (goals.isEmpty()) "Нет целей" else goals.joinToString("\n\n") {
                    "${it.goal.name} (${it.goal.currency} ${it.goal.targetAmount})\nПрогресс: ${it.progressPercent}% | Осталось: ${it.remainingAmount}"
                }
            }
        }
    }
}
