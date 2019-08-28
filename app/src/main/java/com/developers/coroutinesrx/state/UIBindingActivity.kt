package com.developers.coroutinesrx.state

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.developers.coroutinesrx.R
import com.developers.coroutinesrx.databinding.ActivityUibindingBinding
import io.reactivex.Observable

class UIBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = DataBindingUtil.setContentView<ActivityUibindingBinding>(
            this,
            R.layout.activity_uibinding
        )
        val uiBindingViewModel = ViewModelProviders.of(this).get(UiBindingViewModel::class.java)
        viewBinding.uiViewModel = uiBindingViewModel

    }


}
