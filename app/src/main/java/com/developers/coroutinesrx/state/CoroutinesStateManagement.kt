package com.developers.coroutinesrx.state

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityCoroutinesStateManagementBinding
import com.developers.coroutinesrx.movies.MoviesListAdapter

class CoroutinesStateManagement : AppCompatActivity() {

    private lateinit var activityBinding: ActivityCoroutinesStateManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_coroutines_state_management)
        val coroutinesViewModel =
            ViewModelProviders.of(this).get(CoroutinesStateViewModel::class.java)
        activityBinding.stateViewModel = coroutinesViewModel
        coroutinesViewModel.getSearchState().observe(this, Observer {
            when (it.searchAction) {
                SearchAction.FETCH_SUCCESSFUL -> {
                    showMoviesGrid(it)
                }
                SearchAction.FETCH_FAILED -> {
                    Toast.makeText(this, it.throwable?.message, Toast.LENGTH_SHORT).show()
                }
                SearchAction.IN_FLIGHT -> {
                    activityBinding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showMoviesGrid(searchEvent: SearchEvent) {
        activityBinding.progressBar.visibility = View.GONE
        val gridLayoutManager = GridLayoutManager(this, 3)
        val moviesListAdapter = MoviesListAdapter()
        activityBinding.searchListView.layoutManager = gridLayoutManager
        activityBinding.searchListView.adapter = moviesListAdapter
        moviesListAdapter.submitList(searchEvent.searchData)
    }
}
