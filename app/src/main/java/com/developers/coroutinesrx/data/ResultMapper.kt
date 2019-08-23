package com.developers.coroutinesrx.data

import com.developers.coroutinesrx.utils.ApplicationError

interface ResultMapper<out T : Any> {
    fun toResult(applicationError: ApplicationError?= null): Result<T>
}