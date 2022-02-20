package com.example.saywhat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saywhat.R
import com.example.saywhat.databinding.FragGettingStartedBinding

class GettingStartedFrag : Fragment(R.layout.frag_getting_started) {
    private val gettingStartedVM by lazy { ViewModelProvider(this)[GettingStartedVM::class.java] }
    lateinit var vb: FragGettingStartedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragGettingStartedBinding.bind(view)
        vb.tmtableview.initialize(
            recipeGrid = gettingStartedVM.recipeGrid.map { it.map { it.toViewItemRecipe(requireContext()) } },
            shouldFitItemWidthsInsideTable = true,
        )
    }
}