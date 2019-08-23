package com.developers.coroutinesrx.data

sealed class ResultState {

    object Loading: ResultState()

    data class ResultClass(val result: List<MovieResult>): ResultState()

    data class ErrorState(val throwable: Throwable): ResultState()
}