package com.example.saywhat.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onDone(onDone: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDone(text.toString())
            false
        } else true
    }
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) onDone(text.toString())
    }
}