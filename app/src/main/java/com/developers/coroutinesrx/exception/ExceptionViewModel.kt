package com.developers.coroutinesrx.exception

import android.util.Log
import androidx.lifecycle.ViewModel
import com.developers.coroutinesrx.data.Response
import com.developers.coroutinesrx.data.Result
import com.developers.coroutinesrx.data.remote.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class ExceptionViewModel : ViewModel() {

    private val apiInterface: ApiInterface by lazy {
        ApiInterface.asRxJavaClient()
    }
    private val exceptionRepository: ExceptionRepository = ExceptionRepository(apiInterface)
    private val disposables = CompositeDisposable()

    fun getMoviesFromRx() {
//        disposables += exceptionRepository.getMovies()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(onNext = {
//                when (it) {
//                    is Result.Error -> {
//                        Log.d("ExceptionViewModel ", "Exception Model ${it.exception}")
//                    }
//                    is Result.Success -> {
//                        Log.d("ExceptionViewModel ", "Data Model ${it.data}")
//                    }
//                }
//            },
//                onError = {
//                    Log.d("ExceptionViewModel ", "Exception Model")
//                })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}