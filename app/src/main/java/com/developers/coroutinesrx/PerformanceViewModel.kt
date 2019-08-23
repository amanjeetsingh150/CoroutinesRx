package com.developers.coroutinesrx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developers.coroutinesrx.data.Characters
import com.developers.coroutinesrx.data.FeedState
import com.developers.coroutinesrx.data.Shows
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlin.random.Random

class PerformanceViewModel : ViewModel() {

    private val disposables = CompositeDisposable()
    private val feedState = MutableLiveData<FeedState>()
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    // For checking performance of Rx
    fun getRxCharacters() {
        val characters = mutableListOf<Characters>()
        disposables += Observable.range(1, 1000)
            .flatMap { Observable.just(Shows("Friends", it)) }
            .flatMap { getRemoteCharacters(it.id) }
            .flatMap {
                characters.add(it)
                Observable.just(characters)
            }.subscribeOn(Schedulers.io())
            .subscribeBy(onNext = { feedState.postValue(FeedState.DataState(it)) },
                onError = { feedState.postValue(FeedState.ErrorState) })
    }

    private fun getRemoteCharacters(id: Int): Observable<Characters> {
        val factor = Random.nextInt(0, 10000)
        return Observable.just(Characters(id * factor, "XYZ"))
    }

    // For checking performance of coroutines
    fun getCharactersCoroutines() {
        val characters = mutableListOf<Characters>()
        coroutineScope.launch {
            val mc = coroutineScope.async { 3 }
            val c = coroutineScope.async { 3  }
            for (i in 1..1000) {
                val show = Shows("Friends", i)
                val remoteCharacters = getRemoteCharactersAsync(show.id).await()
                characters.add(remoteCharacters)
            }
            feedState.postValue(FeedState.DataState(characters))
        }
    }

    private fun getRemoteCharactersAsync(id: Int) = coroutineScope.async {
        val factor = Random.nextInt(0, 10000)
        return@async Characters(id * factor, "XYZ")
    }

    fun openExceptionActivity() {
        feedState.postValue(FeedState.ExceptionActivityState)
    }

    fun getFeed() = feedState
}