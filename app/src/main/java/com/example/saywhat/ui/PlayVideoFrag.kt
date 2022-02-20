package com.example.saywhat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saywhat.R
import com.example.saywhat.databinding.FragGettingStartedBinding
import com.example.saywhat.databinding.FragPlayVideoBinding
import com.tminus1010.tmcommonkotlin.rx.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayVideoFrag : Fragment(R.layout.frag_play_video) {
    lateinit var vb: FragPlayVideoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragPlayVideoBinding.bind(view)
        // # Setup
        lifecycle.addObserver(vb.youtubePlayerView)
    }
}