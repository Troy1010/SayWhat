package com.example.saywhat.ui

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.saywhat.R
import com.example.saywhat.app.AppData
import com.example.saywhat.app.Errors
import com.example.saywhat.app.YouTubeIDParser
import com.example.saywhat.app.YouTubeIDParserException
import com.example.saywhat.databinding.ItemButtonBinding
import com.example.saywhat.databinding.ItemEditTextBinding
import com.example.saywhat.databinding.ItemTextViewBinding
import com.example.saywhat.extensions.easyEmit
import com.example.saywhat.extensions.onDone
import com.tminus1010.tmcommonkotlin.misc.extensions.bind
import com.tminus1010.tmcommonkotlin.misc.tmTableView.ViewItemRecipe3
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.rx3.asFlow
import javax.inject.Inject

@HiltViewModel
class GettingStartedVM @Inject constructor(
    private val appData: AppData,
    private val errors: Errors,
    private val youTubeIDParser: YouTubeIDParser,
) : ViewModel() {
    // # View Events
    val youTubeLinkEditTextTouched = MutableSharedFlow<Unit>()

    // # User Intents
    fun userSetYouTubeLink(s: String) {
        runCatching { appData.youtubeLink = youTubeIDParser.parse(s) }
            .getOrElse { errors.onNext(it) }
    }

    fun userSubmit() {
        when {
            runCatching { appData.youtubeLink }.isFailure -> errors.onNext(MissingYouTubeLinkException())
            else -> navForward.easyEmit(Unit)
        }
    }

    // # Internal
    private var originalColor: Int? = null

    // # Events
    val navForward = MutableSharedFlow<Unit>()

    // # State
    val highlightAtYouTubeLinkInput =
        merge(
            youTubeLinkEditTextTouched.map { null },
            errors.asFlow().filter { it is YouTubeIDParserException }.map { R.color.colorErrorRed },
        )
    val recipeGrid =
        listOf(
            listOf(
                { context: Context ->
                    ViewItemRecipe3(context, ItemTextViewBinding::inflate) { vb ->
                        vb.root.text = "Please enter youtube link"
                    }
                },
            ),
            listOf(
                { context: Context ->
                    ViewItemRecipe3(context, ItemEditTextBinding::inflate) { vb ->
                        // # Setup
                        vb.root.setOnClickListener { youTubeLinkEditTextTouched.easyEmit(Unit) }
                        originalColor = vb.root.currentTextColor
                        // # User Intents
                        vb.root.onDone { userSetYouTubeLink(it) }
                        // # State
                        vb.root.bind(highlightAtYouTubeLinkInput) { setTextColor(it?.let { ContextCompat.getColor(context, it) } ?: originalColor!!) }
                    }
                },
            ),
            listOf(
                { context: Context ->
                    ViewItemRecipe3(context, ItemButtonBinding::inflate) { vb ->
                        vb.root.text = "Submit"
                        vb.root.setOnClickListener { userSubmit() }
                    }
                },
            ),
        )
}