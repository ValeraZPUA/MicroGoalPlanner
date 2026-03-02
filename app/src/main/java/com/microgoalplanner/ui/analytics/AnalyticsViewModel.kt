package com.microgoalplanner.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microgoalplanner.data.repository.PlannerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AnalyticsViewModel(repository: PlannerRepository) : ViewModel() {
    val summary = repository.observeAnalytics().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
