package com.developers.coroutinesrx.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developers.coroutinesrx.data.remote.SearchApiInterface
import com.developers.coroutinesrx.exception.toApplicationError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class StateViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val moviesClient by lazy { SearchApiInterface.asRxJavaClient() }
    private val searchStates = MutableLiveData<SearchEvent>()

    fun loadData(query: String) {

        val models = getSearchResults(query)

        compositeDisposable += models
            .subscribe({
                searchStates.postValue(it)
            }, {
                // handle errors here
                searchStates.postValue(
                    SearchEvent(
                        searchAction = SearchAction.FETCH_FAILED,
                        searchData = emptyList(),
                        throwable = it.toApplicationError()
                    )
                )
            })
    }

    private fun getSearchResults(query: String): Observable<SearchEvent> {
        return moviesClient.searchMovies(query)
            .map { SearchEvent(SearchAction.FETCH_SUCCESSFUL, it.results) }
            .onErrorReturn {
                SearchEvent(
                    searchAction = SearchAction.FETCH_FAILED,
                    searchData = emptyList(),
                    throwable = it.toApplicationError()
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getSearchState() = searchStates

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}