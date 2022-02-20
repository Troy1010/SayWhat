package com.example.saywhat.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saywhat.R

class GettingStartedFrag : Fragment(R.layout.frag_getting_started) {
    private val gettingStartedVM by lazy { ViewModelProvider(this)[GettingStartedVM::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    
    companion object {
        fun newInstance() = GettingStartedFrag()
    }
}