package com.microgoalplanner.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.microgoalplanner.R

class OnboardingFragment1 : Fragment(R.layout.fragment_onboarding_1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.buttonNext).setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment1_to_onboardingFragment2)
        }
    }
}
