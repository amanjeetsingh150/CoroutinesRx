package com.developers.coroutinesrx.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Error

@Parcelize
data class ApplicationError(
    val errorType: ErrorType,
    val code: Int? = null,
    val message: String? = null
) : Parcelable, Error()