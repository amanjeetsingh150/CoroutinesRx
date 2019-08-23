package com.developers.coroutinesrx.data

import com.developers.coroutinesrx.utils.ApplicationError

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: ApplicationError) : Result<Nothing>()
}
