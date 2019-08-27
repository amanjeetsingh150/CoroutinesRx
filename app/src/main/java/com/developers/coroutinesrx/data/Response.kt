package com.developers.coroutinesrx.data

data class Response(
    val dates: Dates? = null,
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<MovieResult> = emptyList(),
    val totalResults: Int? = null
)