package com.example.android.politicalpreparedness.common

sealed class Result<out T> {

    open val data: T? = null

    data class Success<out T>(override val data: T) : Result<T>()

    data class Error(val throwable: Throwable) : Result<Nothing>() {
        constructor(message: String) : this(Throwable(message))
    }
}