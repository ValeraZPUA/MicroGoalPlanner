package com.microgoalplanner.ui.preloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microgoalplanner.data.repository.PlannerRepository
import kotlinx.coroutines.launch

class PreloaderViewModel(private val repository: PlannerRepository) : ViewModel() {
    fun initialize(onSuccess: (Boolean) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val settings = repository.getSettings()
                onSuccess(settings?.onboardingCompleted == true)
            } catch (t: Throwable) {
                onFailure(t)
            }
        }
    }
}
