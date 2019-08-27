package com.developers.coroutinesrx.exception

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers.coroutinesrx.data.Result
import com.developers.coroutinesrx.data.remote.ApiInterface
import com.developers.coroutinesrx.utils.ApplicationError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ExceptionViewModel : ViewModel() {

    private val apiInterface: ApiInterface by lazy { ApiInterface.asRxJavaClient() }
    private val coroutinesApiInterface: ApiInterface by lazy { ApiInterface.asCoroutinesClient() }

    private val exceptionRepository: ExceptionRepository = ExceptionRepository(apiInterface)
    private val disposables = CompositeDisposable()
    private val exceptionMessage = MutableLiveData<ApplicationError>()

    companion object{
        private val TAG = ExceptionViewModel::class.java.simpleName.toString()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "Catching from CEH")
        exceptionMessage.postValue(exception.toApplicationError())
    }

    /**
     * Handle the predictable errors inside the [Result.Error] which can be used to produce a state
     * to handle exceptions on UI side.
     * Use onError for unpredictable errors as it risks terminating your stream of events.
     */
    fun getMoviesFromRx() {
        disposables += exceptionRepository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is Result.Success -> {
                        // Handle success by getting list of movies
                        val movies = it.data.results
                    }
                    is Result.Error -> {
                        // Handle your errors here
                        val applicationError = it.exception.copy(
                            errorType = it.exception.errorType,
                            code = null,
                            message = "HANDLED ERROR "
                        )
                        exceptionMessage.postValue(applicationError)
                    }
                }
            }, {
                // Need to be implemented for the errors that can be unpredictable. For state which
                // may end up this stream.
            })
    }

    /**
     * This method needs both [CoroutineExceptionHandler] and try/catch as the relationship of
     * coroutine is like:
     *
     *          (A) [launch]
     *          /               | Exception bubbling tendency is above to cancel launch coroutine.
     *         /                | So you have to install a [CoroutineExceptionHandler] and for
     *        /                 | await() as you will have result on the await() suspension point
     *     (B) [async]          | you need try/catch install.
     *
     * B is child to A. for catching B await result try catch is there but as both parent and child are connected the CEH
     * is also needed
     * The exception will be handled by both catch and [CoroutineExceptionHandler] installed above.
     */
    fun getMoviesFromCoroutines() {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                async { coroutinesApiInterface.getExceptionOnMoviesCall() }.await()
            } catch (exception: Exception) {
                Log.d(TAG, "Catching from try catch")
                val applicationError = exception.toApplicationError()
                exceptionMessage.postValue(applicationError)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    /**
     *  An extension function to mock an exception when you call any suspend function
     */
    private suspend fun ApiInterface.getExceptionOnMoviesCall(): Int {
        delay(100)
        return this.run { 5 / 0 }
    }

    fun getExceptionError() = exceptionMessage
}