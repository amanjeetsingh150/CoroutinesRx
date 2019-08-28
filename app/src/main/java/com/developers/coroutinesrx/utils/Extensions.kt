package com.developers.coroutinesrx.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor

fun <T> Context.intentTo(destination: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, destination)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}


/**
 *  A customized zip operator for coroutines. Launches a new coroutine scope.
 *
 *  @param source1 result 1
 *  @param source2 result 2
 *  @param zipper a function to be called after both [source1] and [source2] completes
 *
 *  @returns a [Deferred] result of type [R].
 */
suspend fun <T1, T2, R> zip(source1: T1, source2: T2, zipper: (T1, T2) -> R) = coroutineScope {
    async { zipper(source1, source2) }
}


fun View.clicks(
    scope: CoroutineScope,
    capacity: Int = Channel.RENDEZVOUS,
    action: suspend () -> Unit
) {
    val events = scope.actor<Unit>(Dispatchers.Main, capacity) {
        for (unit in channel) action()
    }

    setOnClickListener(listener(scope, events::offer))
    events.invokeOnClose { setOnClickListener(null) }
}


private fun listener(
    scope: CoroutineScope,
    emitter: (Unit) -> Boolean
) = View.OnClickListener {
    if (scope.isActive) { emitter(Unit) }
}