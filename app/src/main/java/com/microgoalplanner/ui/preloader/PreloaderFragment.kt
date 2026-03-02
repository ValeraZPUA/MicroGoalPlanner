package com.microgoalplanner.ui.preloader

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.microgoalplanner.App
import com.microgoalplanner.R
import com.microgoalplanner.ViewModelFactory

class PreloaderFragment : Fragment(R.layout.fragment_preloader) {
    private val viewModel: PreloaderViewModel by viewModels {
        ViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val errorText = view.findViewById<TextView>(R.id.textError)
        val retry = view.findViewById<Button>(R.id.buttonRetry)
        retry.setOnClickListener { startInit(errorText, retry) }
        startInit(errorText, retry)
    }

    private fun startInit(errorText: TextView, retry: Button) {
        errorText.visibility = View.GONE
        retry.visibility = View.GONE
        viewModel.initialize(
            onSuccess = { isOnboardingDone ->
                val destination = if (isOnboardingDone) R.id.homeFragment else R.id.onboardingFragment1
                findNavController().navigate(destination)
            },
            onFailure = {
                errorText.visibility = View.VISIBLE
                retry.visibility = View.VISIBLE
                errorText.text = "Ошибка инициализации: ${it.message}"
            }
        )
    }
}
