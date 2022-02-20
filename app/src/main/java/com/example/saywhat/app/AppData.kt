package com.example.saywhat.app

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AppData @Inject constructor() {
    var youTubeID = MutableStateFlow<String?>(null)
}