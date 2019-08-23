package com.developers.coroutinesrx.exception

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityExceptionBinding
import io.reactivex.disposables.Disposable

class ExceptionActivity : AppCompatActivity() {

    private lateinit var exceptionViewModel: ExceptionViewModel
    private var searchDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = DataBindingUtil.setContentView<ActivityExceptionBinding>(
            this,
            R.layout.activity_exception
        )
        exceptionViewModel = ViewModelProviders.of(this).get(ExceptionViewModel::class.java)
        viewBinding.exceptionViewModel = exceptionViewModel
    }

    override fun onStop() {
        super.onStop()
        searchDisposable?.dispose()
    }


}
