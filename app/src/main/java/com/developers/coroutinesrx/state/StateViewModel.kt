package com.developers.coroutinesrx.state

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class StateViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun loadData() {

    }
}