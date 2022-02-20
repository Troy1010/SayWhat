package com.example.saywhat.ui

import androidx.lifecycle.ViewModel
import com.example.saywhat.app.AppData
import com.example.saywhat.app.Errors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayVideoVM @Inject constructor(
    private val appData: AppData,
    private val errors: Errors,
) : ViewModel() {
    // # User Intents

    // # Events

    // # State
}