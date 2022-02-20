package com.example.saywhat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saywhat.R
import com.example.saywhat.databinding.FragGettingStartedBinding
import com.tminus1010.tmcommonkotlin.rx.extensions.observe
import com.tminus1010.tmcommonkotlin.view.extensions.nav

class GettingStartedFrag : Fragment(R.layout.frag_getting_started) {
    private val gettingStartedVM by lazy { ViewModelProvider(this)[GettingStartedVM::class.java] }
    lateinit var vb: FragGettingStartedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragGettingStartedBinding.bind(view)
        // # Events
        gettingStartedVM.navForward.observe(viewLifecycleOwner) { nav.navigate(R.id.playVideoFrag) }
        // # State
        vb.tmtableview.initialize(
            recipeGrid = gettingStartedVM.recipeGrid.map { it.map { it(requireContext()) } },
            shouldFitItemWidthsInsideTable = true,
        )
    }
}