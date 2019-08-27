package com.developers.coroutinesrx.data.remote

import com.developers.coroutinesrx.BuildConfig
import com.developers.coroutinesrx.data.Response
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.MOVIE_KEY
    ): Single<Response>

    @GET("now_playing")
    fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.MOVIE_KEY
    ): Single<Response>

    @GET("popular")
    suspend fun getPopularMoviesAsync(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.MOVIE_KEY
    ): Response

    @GET("now_playing")
    suspend fun getNowPlayingMoviesAsync(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = BuildConfig.MOVIE_KEY
    ): Response

    companion object Factory {

        fun asRxJavaClient(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .build()

            return retrofit.create(ApiInterface::class.java)

        }

        fun asCoroutinesClient(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

}