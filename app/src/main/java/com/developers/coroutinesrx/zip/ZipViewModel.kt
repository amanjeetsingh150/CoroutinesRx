package com.developers.coroutinesrx.zip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developers.coroutinesrx.data.Response
import com.developers.coroutinesrx.data.ResultState
import com.developers.coroutinesrx.data.ResultsItem
import com.developers.coroutinesrx.data.remote.ApiInterface
import com.developers.coroutinesrx.utils.zip
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip

class ZipViewModel : ViewModel() {

    companion object {
        private const val SIX_RATING = 6.0
    }

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val coroutinesApiInterface by lazy { ApiInterface.asCoroutinesClient() }
    private val rxApiInterface by lazy { ApiInterface.asRxJavaClient() }
    private val compositeDisposable = CompositeDisposable()
    private val moviesState = MutableLiveData<ResultState>()

    /**
     * Zipping two movie results and filtering movies having rating greater than 6.
     * This function uses coroutines to zip two calls for [ApiInterface.getPopularMoviesAsync] and [ApiInterface.getNowPlayingMoviesAsync]
     *
     * Uses customized [zip] operator for zipping two calls
     */
    fun zipCallsFromCoroutines() {
        coroutineScope.launch {
            val popularMovies = coroutinesApiInterface.getPopularMoviesAsync()
            val nowPlayingMovies = coroutinesApiInterface.getNowPlayingMoviesAsync()

            moviesState.postValue(ResultState.Loading)

            val filteredResults =
                zip(nowPlayingMovies, popularMovies) { nowPlayingMoviesResults, popularMoviesResults ->
                    // Apply function on both results
                    return@zip filterResults(nowPlayingMoviesResults, popularMoviesResults)
                }.await()

            moviesState.postValue(ResultState.ResultClass(filteredResults))
        }
    }

    /**
     * Zipping two movie results and filtering movies having rating greater than 6.
     * This function uses coroutines [Flow] API. This API is rich with different operators. This function uses a
     * [flowOf] Flow Builder and zip two flow calls.
     */
    @FlowPreview
    fun zipCallsFromCoroutinesFlow() {
        coroutineScope.launch {

            val nowPlayingMoviesResults = flowOf(coroutinesApiInterface.getNowPlayingMoviesAsync())
            val popularMovies = flowOf(coroutinesApiInterface.getPopularMoviesAsync())
            nowPlayingMoviesResults.zip(popularMovies) { popularMoviesResults, nowPlayingMovies ->
                // Applying function to resultant calls
                filterResults(nowPlayingMovies.await(), popularMoviesResults.await())
            }.collect {
                // Do something with your result
                moviesState.postValue(ResultState.ResultClass(it))
            }
        }
    }

    /**
     * Zipping two movie results and filtering movies having rating greater than 6.
     * This function uses Rx to zip two calls [ApiInterface.getNowPlayingMovies] and [ApiInterface.getPopularMovies]
     *
     * Uses Rx [Single.zip] operator for zipping two [Single]
     */
    fun zipCallsFromRx() {
        compositeDisposable += Single.zip(rxApiInterface.getPopularMovies(), rxApiInterface.getNowPlayingMovies(),
            BiFunction<Response, Response, List<ResultsItem>> { popularMovies, nowPlayingMovies ->
                filterResults(nowPlayingMovies, popularMovies)
            })
            .doOnSubscribe { moviesState.postValue(ResultState.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // Do something with filtered movies
                moviesState.postValue(ResultState.ResultClass(it))
            }, {
                // Handle errors here
            })
    }

    /**
     * Filter movies results that have rating greater than 6.
     */
    private fun filterResults(nowPlayingMoviesResults: Response, popularMoviesResults: Response): List<ResultsItem> {
        val movies = mutableListOf<ResultsItem>()
        val filteredNowPlayingMovies = nowPlayingMoviesResults.results.filter {
            isRatingMoreThanGiven(it.voteAverage, SIX_RATING)
        }
        val filteredPopularMovies = popularMoviesResults.results.filter {
            isRatingMoreThanGiven(it.voteAverage, SIX_RATING)
        }
        movies.addAll(filteredNowPlayingMovies)
        movies.addAll(filteredPopularMovies)
        return movies
    }

    private fun isRatingMoreThanGiven(rating: Double, threshHoldRating: Double): Boolean {
        return rating > threshHoldRating
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
        compositeDisposable.clear()
    }

    fun getFilteredMoviesState() = moviesState
}