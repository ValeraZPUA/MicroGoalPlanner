package com.microgoalplanner.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microgoalplanner.data.repository.PlannerRepository
import com.microgoalplanner.domain.model.Goal
import com.microgoalplanner.domain.model.GoalStatus
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PlannerRepository) : ViewModel() {
    val goals = repository.observeGoalsWithProgress().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun quickCreateGoal(name: String, amount: Long, currency: String, deadline: LocalDate) {
        viewModelScope.launch {
            repository.createGoal(
                Goal(name = name, targetAmount = amount, currency = currency, deadline = deadline, status = GoalStatus.ACTIVE)
            )
        }
    }
}
