package com.microgoalplanner.ui.preloader

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.microgoalplanner.R

class PreloaderFragment : Fragment(R.layout.fragment_preloader) {
    private val viewModel: PreloaderViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(
            onSuccess = { findNavController().navigate(R.id.action_preloaderFragment_to_onboardingFragment1) },
            onFailure = { /* TODO: показать диагностику и кнопку Retry */ }
        )
    }
}
