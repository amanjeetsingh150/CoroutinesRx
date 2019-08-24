package com.developers.coroutinesrx.state

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityRxStateManagementBinding
import com.developers.coroutinesrx.movies.MoviesListAdapter

class RxStateManagement : AppCompatActivity() {

    private lateinit var activityBinding: ActivityRxStateManagementBinding
    private lateinit var moviesListAdapter: MoviesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_rx_state_management)
        val rxViewModel = ViewModelProviders.of(this).get(StateViewModel::class.java)
        activityBinding.stateViewModel = rxViewModel
        rxViewModel.getSearchState().observe(this, Observer {
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
                SearchAction.IN_FLIGHT -> {
                    activityBinding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }
}
