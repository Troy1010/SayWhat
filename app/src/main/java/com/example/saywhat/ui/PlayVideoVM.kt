package com.example.saywhat.ui

import androidx.lifecycle.ViewModel
import com.example.saywhat.all.extensions.easyEmit
import com.example.saywhat.app.AppData
import com.example.saywhat.app.Errors
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PlayVideoVM @Inject constructor(
    private val appData: AppData,
    private val errors: Errors,
) : ViewModel() {
    // # View Events
    val youTubePlayer = MutableSharedFlow<YouTubePlayer>()

    // # State
    val youTubeIDAndPlayer =
        combine(
            appData.youTubeID.filter { it != null }.map { it!! },
            youTubePlayer,
        ) { a, b -> Pair(a, b) }
            .catch { errors.easyEmit(it) }
}