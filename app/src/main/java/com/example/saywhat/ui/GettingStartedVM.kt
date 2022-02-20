package com.example.saywhat.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.saywhat.app.AppData
import com.example.saywhat.app.Errors
import com.example.saywhat.app.YouTubeIDParser
import com.example.saywhat.databinding.ItemButtonBinding
import com.example.saywhat.databinding.ItemEditTextBinding
import com.example.saywhat.databinding.ItemTextViewBinding
import com.example.saywhat.ui.extensions.onDone
import com.tminus1010.tmcommonkotlin.misc.tmTableView.ViewItemRecipe3
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class GettingStartedVM @Inject constructor(
    private val appData: AppData,
    private val errors: Errors,
    private val youTubeIDParser: YouTubeIDParser,
) : ViewModel() {
    // # User Intents
    fun userSetYouTubeLink(s: String) {
        runCatching { appData.youtubeLink = youTubeIDParser.parse(s) }
            .getOrElse { errors.onNext(it) }
    }

    fun userSubmit() {
        when {
            runCatching { appData.youtubeLink }.isFailure -> errors.onNext(MissingYouTubeLinkException())
            else -> navForward.onNext(Unit)
        }
    }

    // # Events
    val navForward = PublishSubject.create<Unit>()

    // # State
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
                        vb.root.onDone { userSetYouTubeLink(it) }
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