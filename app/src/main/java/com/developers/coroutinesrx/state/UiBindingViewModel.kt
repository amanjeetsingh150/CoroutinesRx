package com.developers.coroutinesrx.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UiBindingViewModel : ViewModel() {


    private var startTime: Long = 0L

    private val actor = viewModelScope.actor<SearchEvent>(
        Dispatchers.Main,
        capacity = Channel.RENDEZVOUS
    ) {
        throttle(500).consumeEach {
            val time = System.currentTimeMillis() - startTime
            Log.d("UiBindingViewModel", "START TIME $startTime  Yiem $time")
        }
    }

    fun onTakeButtonClick() {
        startTime = System.currentTimeMillis()
        val searchEvent = SearchEvent(SearchAction.IN_FLIGHT)
        viewModelScope.launch {
            for (i in 1..10) {
                delay(200)
                actor.send(searchEvent)
            }
        }
    }


    @ExperimentalCoroutinesApi
    fun <E> ReceiveChannel<E>.throttle(wait: Long): ReceiveChannel<E> =
        viewModelScope.produce(Dispatchers.Default) {
            var nextTime = 0L
            consumeEach {
                val curTime = System.currentTimeMillis()
                if (curTime >= nextTime) {
                    nextTime = curTime + wait
                    send(it)
                }
            }
        }
}