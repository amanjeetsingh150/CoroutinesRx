package com.developers.coroutinesrx.data

import com.developers.coroutinesrx.utils.ApplicationError

data class Response(
    val dates: Dates? = null,
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<MovieResult> = emptyList(),
    val totalResults: Int? = null
) : ResultMapper<Response> {

    override fun toResult(applicationError: ApplicationError?): Result<Response> {
        return when {
            isSuccess() -> Result.Success(Response(dates, page, totalPages, results, totalResults))
            else -> Result.Error(applicationError!!)
        }
    }

    private fun isSuccess() = !results.isNullOrEmpty()
}
