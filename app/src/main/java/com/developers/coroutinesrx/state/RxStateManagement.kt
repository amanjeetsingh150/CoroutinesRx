package com.developers.coroutinesrx.state

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.data.Result
import com.developers.coroutinesrx.data.remote.SearchApiInterface
import com.developers.coroutinesrx.databinding.ActivityRxStateManagementBinding
import com.developers.coroutinesrx.exception.asResult
import com.developers.coroutinesrx.exception.toApplicationError
import com.developers.coroutinesrx.movies.MoviesListAdapter
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class RxStateManagement : AppCompatActivity() {

    private lateinit var activityBinding: ActivityRxStateManagementBinding
    private val rxApiInterface by lazy { SearchApiInterface.asRxJavaClient() }
    private val compositeDisposable = CompositeDisposable()
    private lateinit var moviesListAdapter: MoviesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_rx_state_management)

        val events = activityBinding.searchButton.clicks()
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { SearchEvent(SearchAction.IN_FLIGHT, activityBinding.searchEditText.text.toString()) }
            .observeOn(Schedulers.io())

        val models = events.flatMap { rxApiInterface.searchMovies(it.searchTerm) }
            .map {
                SearchEvent(
                    SearchAction.FETCH_SUCCESSFUL,
                    activityBinding.searchEditText.text.toString(),
                    it.results
                )
            }
            .onErrorReturn {
                SearchEvent(
                    SearchAction.FETCH_FAILED,
                    activityBinding.searchEditText.text.toString(),
                    emptyList(),
                    it.toApplicationError()
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable += models.subscribe {
            when (it.searchAction) {
                SearchAction.FETCH_SUCCESSFUL -> {
                    activityBinding.progressBar.visibility = View.GONE
                    val gridLayoutManager = GridLayoutManager(this, 3)
                    moviesListAdapter = MoviesListAdapter()
                    activityBinding.searchListView.layoutManager = gridLayoutManager
                    activityBinding.searchListView.adapter = moviesListAdapter
                    moviesListAdapter.submitList(it.searchData)
                }
                SearchAction.FETCH_FAILED -> {
                    Toast.makeText(this, it.throwable?.message, Toast.LENGTH_SHORT).show()
                }
                SearchAction.IN_FLIGHT -> { }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
