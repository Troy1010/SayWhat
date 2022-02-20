package com.example.saywhat.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.saywhat.databinding.ItemButtonBinding
import com.example.saywhat.databinding.ItemEditTextBinding
import com.example.saywhat.databinding.ItemTextViewBinding
import com.example.saywhat.ui.extensions.onDone
import com.tminus1010.tmcommonkotlin.misc.tmTableView.IHasToViewItemRecipe
import com.tminus1010.tmcommonkotlin.misc.tmTableView.IViewItemRecipe3
import com.tminus1010.tmcommonkotlin.misc.tmTableView.ViewItemRecipe3
import io.reactivex.rxjava3.subjects.PublishSubject

class GettingStartedVM : ViewModel() {
    // # User Intents
    fun userSetYoutubeLink(s: String) {
        TODO()
    }

    fun userSubmit() {
        navForward.onNext(Unit)
    }

    // # Events
    val navForward = PublishSubject.create<Unit>()

    // # State
    val recipeGrid =
        listOf(
            listOf(
                object : IHasToViewItemRecipe {
                    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
                        return ViewItemRecipe3(context, ItemTextViewBinding::inflate) { vb ->
                            vb.root.text = "Please enter youtube link"
                        }
                    }
                },
            ),
            listOf(
                object : IHasToViewItemRecipe {
                    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
                        return ViewItemRecipe3(context, ItemEditTextBinding::inflate) { vb ->
                            vb.root.onDone { userSetYoutubeLink(it) }
                        }
                    }
                },
            ),
            listOf(
                object : IHasToViewItemRecipe {
                    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
                        return ViewItemRecipe3(context, ItemButtonBinding::inflate) { vb ->
                            vb.root.text = "Submit"
                            vb.root.setOnClickListener { userSubmit() }
                        }
                    }
                },
            ),
        )
}