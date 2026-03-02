package com.microgoalplanner.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microgoalplanner.data.repository.PlannerRepository
import com.microgoalplanner.domain.model.Goal
import com.microgoalplanner.domain.model.GoalStatus
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoalsViewModel(private val repository: PlannerRepository) : ViewModel() {
    private val search = MutableStateFlow("")

    val goals = repository.observeGoalsWithProgress().combine(search) { goals, query ->
        goals.filter { it.goal.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearch(value: String) { search.value = value }

    fun createGoal(name: String, amount: Long, currency: String, deadline: LocalDate, priority: Int) {
        viewModelScope.launch {
            repository.createGoal(Goal(name = name, targetAmount = amount, currency = currency, deadline = deadline, priority = priority, status = GoalStatus.ACTIVE))
        }
    }
}
