package com.microgoalplanner.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.data.local.SettingsEntity
import com.microgoalplanner.notifications.ReminderScheduler
import kotlinx.coroutines.launch

class OnboardingFragment2 : Fragment(R.layout.fragment_onboarding_2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.buttonFinish).setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                (requireActivity().application as App).repository.upsertSettings(
                    SettingsEntity(
                        currency = "USD",
                        notificationsEnabled = true,
                        weeklyReminderEnabled = true,
                        onboardingCompleted = true,
                        privacyMode = false
                    )
                )
                ReminderScheduler(requireContext()).scheduleWeeklyGoalReminder()
                findNavController().navigate(R.id.action_onboardingFragment2_to_homeFragment)
            }
        }
    }
}
