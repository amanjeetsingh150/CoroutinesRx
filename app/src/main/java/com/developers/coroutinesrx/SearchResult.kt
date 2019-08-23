package com.developers.coroutinesrx

import com.developers.coroutinesrx.data.MovieResult
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("page") var page: Int = 0, //1
    @SerializedName("total_results") var totalResults: Int = 0, //117
    @SerializedName("total_pages") var totalPages: Int = 0, //6
    @SerializedName("results") var results: List<MovieResult> = listOf()
)

