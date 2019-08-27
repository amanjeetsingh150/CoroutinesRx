package com.developers.coroutinesrx.exception

import com.developers.coroutinesrx.data.Response
import com.developers.coroutinesrx.data.Result
import com.developers.coroutinesrx.data.remote.ApiInterface
import com.developers.coroutinesrx.utils.ApplicationError
import com.developers.coroutinesrx.utils.ErrorType
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


class ExceptionRepository(private var apiInterface: ApiInterface) {

    /**
     * Calls [ApiInterface] to get popular movies. The function maps your result to Success or
     * failure whenever you are calling your API.
     * In case of error onErrorReturn will wrap error in [Result.Error] and helps you to avoid
     * sudden death further in  your program.
     *
     *
     * @return Single<Result<Response>> a generic result object of your response.
     */
    fun getMovies(): Single<Result<Response>> {
        return apiInterface.getPopularMovies()
            .map { it.asResult().also { throw IllegalArgumentException() } }
            .onErrorReturn { Result.Error(it.toApplicationError()) }
    }

}


/**
 * Extension to convert any object into result
 */
fun <T> T.asResult(): Result<T> {
    return Result.Success(this)
}

/**
 * Extension to convert the throwable into @[ApplicationError]
 */
fun Throwable.toApplicationError(): ApplicationError {
    return when {
        this is HttpException -> {
            try {
                val responseString = this.response()?.errorBody()?.string()
                val jsonObject = JSONObject(responseString).getJSONObject("meta")

                ApplicationError(
                    ErrorType.SERVER,
                    jsonObject.getInt("code"),
                    jsonObject.getString("errorDetail")
                )
            } catch (e: Exception) {
                ApplicationError(ErrorType.SERVER)
            }
        }

        this is IOException -> ApplicationError(ErrorType.CONNECTION)

        else -> ApplicationError(ErrorType.UNKNOWN)
    }

}