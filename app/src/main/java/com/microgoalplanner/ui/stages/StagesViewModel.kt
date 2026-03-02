package com.microgoalplanner.ui.stages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microgoalplanner.data.repository.PlannerRepository
import com.microgoalplanner.domain.model.Stage
import com.microgoalplanner.domain.model.StageStatus
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StagesViewModel(private val repository: PlannerRepository) : ViewModel() {
    private val search = MutableStateFlow("")

    val stages = repository.observeStages().combine(search) { stages, query ->
        stages.filter { it.name.contains(query, true) || it.description.contains(query, true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearch(value: String) { search.value = value }

    fun createStage(goalId: Long, name: String, amount: Long, deadline: LocalDate, description: String, priority: Int) {
        viewModelScope.launch {
            repository.createStage(
                Stage(
                    goalId = goalId,
                    name = name,
                    amount = amount,
                    deadline = deadline,
                    description = description,
                    priority = priority,
                    status = StageStatus.TODO
                )
            )
        }
    }

    fun markCompleted(stage: Stage, note: String) {
        viewModelScope.launch {
            repository.updateStage(stage.copy(status = StageStatus.COMPLETED, note = note))
        }
    }
}
