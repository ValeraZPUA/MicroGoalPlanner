package com.microgoalplanner.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.microgoalplanner.R

class OnboardingFragment2 : Fragment(R.layout.fragment_onboarding_2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.buttonFinish).setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment2_to_homeFragment)
        }
    }
}
