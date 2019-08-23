package com.developers.coroutinesrx.data

sealed class FeedState {

    data class DataState(val characters: List<Characters>): FeedState()

    object ErrorState: FeedState()

    object ExceptionActivityState: FeedState()
}