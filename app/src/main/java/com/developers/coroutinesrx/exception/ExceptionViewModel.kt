package com.developers.coroutinesrx.exception

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers.coroutinesrx.data.Result
import com.developers.coroutinesrx.data.remote.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


class ExceptionViewModel : ViewModel() {

    private val apiInterface: ApiInterface by lazy { ApiInterface.asRxJavaClient() }
    private val coroutinesApiInterface: ApiInterface by lazy { ApiInterface.asCoroutinesClient() }

    private val exceptionRepository: ExceptionRepository = ExceptionRepository(apiInterface)
    private val disposables = CompositeDisposable()

    fun getMoviesFromRx() {
        disposables += exceptionRepository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is Result.Success -> {

                    }
                    is Result.Error -> {

                    }
                }
            }, {

            })
    }

    fun getMoviesFromCoroutines() {
        viewModelScope.launch {
            val result = coroutinesApiInterface.getExceptionOnMoviesCall()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    suspend fun ApiInterface.getExceptionOnMoviesCall(): Int {
        delay(100)
        return this.run { 5/0 }
    }
}