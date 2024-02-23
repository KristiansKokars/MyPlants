package com.kristianskokars.myplants.lib

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(
    context = CoroutineExceptionHandler { _, e ->
        Log.e("MyPlants", "Coroutine failed: ${e.localizedMessage}")
    },
    block = block
)

suspend fun LifecycleOwner.launchImmediate(
    block: suspend () -> Unit,
) = repeatOnLifecycle(Lifecycle.State.STARTED) {
    withContext(Dispatchers.Main.immediate) {
        block()
    }
}
