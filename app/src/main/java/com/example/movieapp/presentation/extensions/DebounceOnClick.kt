package com.example.movieapp.presentation.extensions

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun EditText.onTextChangedListener(minLength: Int) =
    callbackFlow {
        val textWatcher =
            addTextChangedListener {
                if (it != null) {
                    if (it.length >= minLength)
                        trySend(Unit).isSuccess
                }
            }
        awaitClose { removeTextChangedListener(textWatcher) }
    }