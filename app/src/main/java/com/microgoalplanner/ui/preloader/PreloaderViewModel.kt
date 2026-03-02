package com.microgoalplanner.ui.preloader

import androidx.lifecycle.ViewModel

class PreloaderViewModel : ViewModel() {
    fun initialize(onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        try {
            // TODO: Initialize Room, check dependencies, load theme and user session.
            onSuccess()
        } catch (t: Throwable) {
            onFailure(t)
        }
    }
}
