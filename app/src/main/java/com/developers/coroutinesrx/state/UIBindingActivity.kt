package com.developers.coroutinesrx.state

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityUibindingBinding
import com.developers.coroutinesrx.utils.clicks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class UIBindingActivity : AppCompatActivity() {

    private val job = Job()
    private val dispatchers = Dispatchers.IO + job
    private val coroutineScope = CoroutineScope(job + dispatchers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = DataBindingUtil.setContentView<ActivityUibindingBinding>(
            this,
            R.layout.activity_uibinding
        )
        val uiBindingViewModel = ViewModelProviders.of(this).get(UiBindingViewModel::class.java)
        viewBinding.uiViewModel = uiBindingViewModel
        viewBinding.takeButton.clicks(coroutineScope) {

        }
    }


}
