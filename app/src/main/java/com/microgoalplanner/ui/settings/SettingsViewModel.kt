package com.microgoalplanner.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microgoalplanner.data.local.SettingsEntity
import com.microgoalplanner.data.repository.PlannerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: PlannerRepository) : ViewModel() {
    val settings = repository.observeSettings().map {
        it ?: SettingsEntity(currency = "USD", notificationsEnabled = true, weeklyReminderEnabled = true, onboardingCompleted = false, privacyMode = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsEntity(currency = "USD", notificationsEnabled = true, weeklyReminderEnabled = true, onboardingCompleted = false, privacyMode = false))

    fun saveCurrency(currency: String) {
        viewModelScope.launch { repository.upsertSettings(settings.value.copy(currency = currency)) }
    }

    fun saveNotifications(enabled: Boolean) {
        viewModelScope.launch { repository.upsertSettings(settings.value.copy(notificationsEnabled = enabled)) }
    }

    fun clearAllData() {
        viewModelScope.launch { repository.clearAllData() }
    }
}
