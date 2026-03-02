package com.microgoalplanner.ui.analytics

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.ViewModelFactory
import kotlinx.coroutines.launch

class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {
    private val viewModel: AnalyticsViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val analytics = view.findViewById<TextView>(R.id.textAnalytics)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.summary.collect { s ->
                analytics.text = if (s == null) "Нет данных" else """
                    Целей: ${s.totalGoals}
                    Завершено целей: ${s.completedGoals}
                    Этапов: ${s.totalStages}
                    Завершено этапов: ${s.completedStages}
                    Прогноз выполнения: ${s.projectedCompletionRatePercent}%
                """.trimIndent()
            }
        }
    }
}
