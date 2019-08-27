package com.developers.coroutinesrx.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers.coroutinesrx.data.remote.SearchApiInterface
import com.developers.coroutinesrx.exception.toApplicationError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoroutinesStateViewModel : ViewModel() {


    private val moviesClient by lazy { SearchApiInterface.asCoroutinesClient() }
    private val searchStates = MutableLiveData<SearchEvent>()

    @ExperimentalCoroutinesApi
    fun loadData(query: String) {

        viewModelScope.launch {

            flowOf(moviesClient.searchMoviesAsync(query))
                .map { SearchEvent(SearchAction.FETCH_SUCCESSFUL, it.results) }
                .catch {
                    SearchEvent(
                        searchAction = SearchAction.FETCH_FAILED,
                        searchData = emptyList(),
                        throwable = it.toApplicationError()
                    )
                }.collect {
                    searchStates.postValue(it)
                }

        }
    }

    fun getSearchState() = searchStates
}