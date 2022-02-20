package com.example.saywhat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saywhat.R
import com.example.saywhat.all.extensions.easyEmit
import com.example.saywhat.app.Errors
import com.example.saywhat.databinding.FragPlayVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.misc.extensions.bind
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlayVideoFrag : Fragment(R.layout.frag_play_video) {
    lateinit var vb: FragPlayVideoBinding
    private val playVideoVM by lazy { ViewModelProvider(this)[PlayVideoVM::class.java] }

    @Inject
    lateinit var errors: Errors

    @Inject
    lateinit var toaster: Toaster

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragPlayVideoBinding.bind(view)
        // # Events
        errors.observe(viewLifecycleOwner) {
            logz("Error:", it)
            when (it) {
                else -> toaster.toast("Error occurred:${it.javaClass.simpleName}")
            }
        }
        // # Setup
        lifecycle.addObserver(vb.youtubePlayerView)
        vb.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) = playVideoVM.youTubePlayer.easyEmit(youTubePlayer)
        })
        // # State
        vb.youtubePlayerView.bind(playVideoVM.youTubeIDAndPlayer) { (video, player) -> player.loadVideo(video, 0f) }
    }
}