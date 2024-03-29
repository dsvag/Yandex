package com.dsvag.yandex.base

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onStart
import java.math.BigDecimal

typealias Charts = Pair<List<Pair<Long, BigDecimal>>, List<Pair<Long, BigDecimal>>>

@ExperimentalCoroutinesApi
fun EditText.textChanges(): Flow<CharSequence?> = callbackFlow<CharSequence?> {
    val listener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            sendBlocking(s)
        }
    }

    addTextChangedListener(listener)

    awaitClose { removeTextChangedListener(listener) }

}.filterNotNull().onStart { emit(text) }

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}
