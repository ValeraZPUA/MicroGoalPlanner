package com.microgoalplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.microgoalplanner.data.repository.PlannerRepository
import com.microgoalplanner.ui.analytics.AnalyticsViewModel
import com.microgoalplanner.ui.goals.GoalsViewModel
import com.microgoalplanner.ui.home.HomeViewModel
import com.microgoalplanner.ui.preloader.PreloaderViewModel
import com.microgoalplanner.ui.settings.SettingsViewModel
import com.microgoalplanner.ui.stages.StagesViewModel

class ViewModelFactory(private val repository: PlannerRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PreloaderViewModel::class.java) -> PreloaderViewModel(repository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(GoalsViewModel::class.java) -> GoalsViewModel(repository) as T
            modelClass.isAssignableFrom(StagesViewModel::class.java) -> StagesViewModel(repository) as T
            modelClass.isAssignableFrom(AnalyticsViewModel::class.java) -> AnalyticsViewModel(repository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
