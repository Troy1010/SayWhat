package com.example.saywhat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.saywhat.R
import com.example.saywhat.databinding.FragPlayVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayVideoFrag : Fragment(R.layout.frag_play_video) {
    lateinit var vb: FragPlayVideoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragPlayVideoBinding.bind(view)
        // # Setup
        lifecycle.addObserver(vb.youtubePlayerView)
        vb.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "S0Q4gqBUs7c"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
}