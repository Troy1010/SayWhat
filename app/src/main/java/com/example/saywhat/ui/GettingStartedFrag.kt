package com.example.saywhat.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saywhat.R
import com.example.saywhat.app.Errors
import com.example.saywhat.app.YouTubeIDParserException
import com.example.saywhat.databinding.FragGettingStartedBinding
import com.tminus1010.tmcommonkotlin.coroutines.extensions.observe
import com.tminus1010.tmcommonkotlin.rx.extensions.observe
import com.tminus1010.tmcommonkotlin.view.extensions.nav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GettingStartedFrag : Fragment(R.layout.frag_getting_started) {
    lateinit var vb: FragGettingStartedBinding
    private val gettingStartedVM by lazy { ViewModelProvider(this)[GettingStartedVM::class.java] }

    @Inject
    lateinit var errors: Errors

    @Inject
    lateinit var toaster: Toaster

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragGettingStartedBinding.bind(view)
        // # Events
        errors.observe(viewLifecycleOwner) {
            when (it) {
                is YouTubeIDParserException -> toaster.toast("Invalid YouTube link")
                is MissingYouTubeLinkException -> toaster.toast("Please enter a valid YouTube link")
                else -> toaster.toast("Error occurred:${it.javaClass.simpleName}")
            }

        }
        gettingStartedVM.navForward.observe(viewLifecycleOwner) { nav.navigate(R.id.playVideoFrag) }
        // # State
        vb.tmtableview.initialize(
            recipeGrid = gettingStartedVM.recipeGrid.map { it.map { it(requireContext()) } },
            shouldFitItemWidthsInsideTable = true,
        )
    }
}