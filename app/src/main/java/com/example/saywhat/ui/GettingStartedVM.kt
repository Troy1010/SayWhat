package com.example.saywhat.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.saywhat.databinding.ItemButtonBinding
import com.tminus1010.tmcommonkotlin.misc.tmTableView.IHasToViewItemRecipe
import com.tminus1010.tmcommonkotlin.misc.tmTableView.IViewItemRecipe3
import com.tminus1010.tmcommonkotlin.misc.tmTableView.ViewItemRecipe3

class GettingStartedVM : ViewModel() {
    val recipeGrid =
        listOf(
            listOf(
                object : IHasToViewItemRecipe {
                    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
                        return ViewItemRecipe3(context, ItemButtonBinding::inflate) { vb ->
                            vb.btnItem.text = "Howdy Button"
                        }
                    }
                },
                object : IHasToViewItemRecipe {
                    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
                        return ViewItemRecipe3(context, ItemButtonBinding::inflate) { vb ->
                            vb.btnItem.text = "Howdy Button"
                        }
                    }
                },
            ),
            listOf(
                object : IHasToViewItemRecipe {
                    override fun toViewItemRecipe(context: Context): IViewItemRecipe3 {
                        return ViewItemRecipe3(context, ItemButtonBinding::inflate) { vb ->
                            vb.btnItem.text = "Howdy Button"
                        }
                    }
                },
            ),
        )
}