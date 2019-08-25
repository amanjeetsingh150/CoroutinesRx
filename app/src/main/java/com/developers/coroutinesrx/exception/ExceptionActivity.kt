package com.developers.coroutinesrx.exception

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityExceptionBinding

class ExceptionActivity : AppCompatActivity() {

    private lateinit var exceptionViewModel: ExceptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = DataBindingUtil.setContentView<ActivityExceptionBinding>(
            this,
            R.layout.activity_exception
        )
        exceptionViewModel = ViewModelProviders.of(this).get(ExceptionViewModel::class.java)
        viewBinding.exceptionViewModel = exceptionViewModel
    }
}
