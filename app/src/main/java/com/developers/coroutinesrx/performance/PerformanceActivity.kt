package com.developers.coroutinesrx.performance

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.data.FeedState
import com.developers.coroutinesrx.databinding.ActivityMainBinding

class PerformanceActivity : AppCompatActivity() {

    private lateinit var viewModel: PerformanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        viewModel = ViewModelProviders.of(this).get(PerformanceViewModel::class.java)
        viewBinding.performanceViewModel = viewModel
        viewModel.getFeed().observe(this, Observer {
            when (it) {
                is FeedState.ErrorState -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                is FeedState.DataState -> {
                    Toast.makeText(this, "Size: " + it.characters.size, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
