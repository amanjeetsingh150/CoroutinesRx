package com.developers.coroutinesrx.state

import android.os.Bundle
import android.view.View
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

class RxStateManagement : AppCompatActivity() {

    private lateinit var activityBinding: ActivityRxStateManagementBinding
    private val rxApiInterface by lazy { SearchApiInterface.asRxJavaClient() }
    private val compositeDisposable = CompositeDisposable()
    private lateinit var moviesListAdapter: MoviesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_rx_state_management)

        val events = activityBinding.searchButton.clicks()
            .map { SearchEvent(activityBinding.searchEditText.text.toString()) }

        val models = events.flatMap { rxApiInterface.searchMovies(it.searchTerm) }
            .map { it.results.asResult() }
            .onErrorReturn { Result.Error(it.toApplicationError()) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { activityBinding.progressBar.visibility = View.VISIBLE }

        compositeDisposable += models.subscribe({
            when (it) {
                is Result.Success -> {
                    activityBinding.progressBar.visibility = View.GONE
                    val gridLayoutManager = GridLayoutManager(this, 3)
                    moviesListAdapter = MoviesListAdapter()
                    activityBinding.searchListView.layoutManager = gridLayoutManager
                    activityBinding.searchListView.adapter = moviesListAdapter
                    moviesListAdapter.submitList(it.data)
                }
                is Result.Error -> {

                }
            }
        }, {

        })
    }
}
