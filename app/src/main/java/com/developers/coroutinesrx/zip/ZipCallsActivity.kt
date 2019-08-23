package com.developers.coroutinesrx.zip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.data.ResultState
import com.developers.coroutinesrx.databinding.ActivityZipCallsBinding
import com.developers.coroutinesrx.movies.MoviesListAdapter

class ZipCallsActivity : AppCompatActivity() {

    private lateinit var zipViewModel: ZipViewModel
    private lateinit var moviesListAdapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = DataBindingUtil.setContentView<ActivityZipCallsBinding>(
            this,
            R.layout.activity_zip_calls
        )
        zipViewModel = ViewModelProviders.of(this).get(ZipViewModel::class.java)
        viewBinding.zipViewModel = zipViewModel
        zipViewModel.getFilteredMoviesState().observe(this, Observer {
            when (it) {
                is ResultState.Loading -> {

                }
                is ResultState.ResultClass -> {
                    val gridLayoutManager = GridLayoutManager(this, 3)
                    moviesListAdapter = MoviesListAdapter()
                    viewBinding.filteredMoviesFeed.layoutManager = gridLayoutManager
                    viewBinding.filteredMoviesFeed.adapter = moviesListAdapter
                    moviesListAdapter.submitList(it.result)
                }
            }
        })
    }
}
