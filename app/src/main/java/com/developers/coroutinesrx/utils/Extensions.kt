package com.developers.coroutinesrx.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

fun <T> Context.intentTo(destination: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, destination)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}


/**
 *  A customized zip operator for coroutines. Launches a new coroutine scope.
 *
 *  @param source1 deferred result 1
 *  @param source2 deferred result 2
 *  @param zipper a function to be called after both [source1] and [source2] completes
 *
 *  @returns a [Deferred] result of type [R].
 */
suspend fun <T1, T2, R> zip(source1: Deferred<T1>, source2: Deferred<T2>, zipper: (T1, T2) -> R) = coroutineScope {
    async { zipper(source1.await(), source2.await()) }
}

