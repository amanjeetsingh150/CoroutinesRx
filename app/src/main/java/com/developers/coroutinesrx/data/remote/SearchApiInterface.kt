package com.developers.coroutinesrx.data.remote

import com.developers.coroutinesrx.BuildConfig
import com.developers.coroutinesrx.SearchResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiInterface {

    @GET("movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") key: String = BuildConfig.MOVIE_KEY
    ): Observable<SearchResult>


    companion object Factory {

        fun asRxJavaClient(): SearchApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/search/")
                .build()

            return retrofit.create(SearchApiInterface::class.java)

        }

        fun asCoroutinesClient(): SearchApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("https://api.themoviedb.org/3/search/")
                .build()

            return retrofit.create(SearchApiInterface::class.java)
        }
    }
}