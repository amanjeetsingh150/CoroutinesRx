package com.developers.coroutinesrx.exception

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers.coroutinesrx.data.Result
import com.developers.coroutinesrx.data.remote.ApiInterface
import com.developers.coroutinesrx.utils.ApplicationError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ExceptionViewModel : ViewModel() {

    private val apiInterface: ApiInterface by lazy { ApiInterface.asRxJavaClient() }
    private val coroutinesApiInterface: ApiInterface by lazy { ApiInterface.asCoroutinesClient() }

    private val exceptionRepository: ExceptionRepository = ExceptionRepository(apiInterface)
    private val disposables = CompositeDisposable()


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->

    }

    /**
     * Handle the predictable errors inside the [Result.Error] which can be used to produce a state
     * to handle exceptions on UI side.
     * Use onError for unpredictable errors as it risks terminating your stream of events.
     */
    fun getMoviesFromRx() {
        disposables += exceptionRepository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is Result.Success -> {
                        // Handle success by getting list of movies
                        val movies = it.data.results
                    }
                    is Result.Error -> {
                        // Handle your errors here
                        val applicationError = it.exception
                    }
                }
            }, {

            })
    }

    /**
     *
     */
    fun getMoviesFromCoroutines() {
        viewModelScope.launch {
            try {
                val result = async { coroutinesApiInterface.getExceptionOnMoviesCall() }.await()
            } catch (exception: Exception) {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    /**
     *  An extension function to mock an exception when you call any suspend function
     */
    private suspend fun ApiInterface.getExceptionOnMoviesCall(): Int {
        delay(100)
        return this.run { 5 / 0 }
    }
}