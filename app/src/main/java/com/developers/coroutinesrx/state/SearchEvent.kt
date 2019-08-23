package com.developers.coroutinesrx.state

import com.developers.coroutinesrx.data.MovieResult
import com.developers.coroutinesrx.utils.ApplicationError

data class SearchEvent(
    val searchAction: SearchAction,
    val searchTerm: String = "",
    val searchData: List<MovieResult> = emptyList(),
    val throwable: ApplicationError? = null
)