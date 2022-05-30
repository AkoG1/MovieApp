package com.example.movieapp.presentation.extensions

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
fun EditText.onTextChangedListener(minLength: Int) =
    callbackFlow<Unit> {
        addTextChangedListener {
            if (it != null) {
                if (it.length >= minLength)
                    trySend(Unit).isSuccess
            }
        }
        awaitClose()
    }