package com.developers.coroutinesrx.exception

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityExceptionBinding
import com.developers.coroutinesrx.utils.ErrorType

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
        exceptionViewModel.getExceptionError().observe(this, Observer {
            when (it.errorType) {
                ErrorType.CONNECTION -> {
                    showMessage("$it " + it.message.orEmpty())
                }
                ErrorType.SERVER -> {
                    showMessage("$it " + it.message.orEmpty())
                }
                ErrorType.UNKNOWN -> {
                    showMessage("$it " + it.message.orEmpty())
                }
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
