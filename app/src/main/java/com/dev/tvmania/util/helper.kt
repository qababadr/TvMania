package com.dev.tvmania.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun String.removeHtmlTags(): String {
    return Regex(pattern = "<\\w+>|</\\w+>").replace(this, replacement = "")
}

inline fun <Result, Request> networkBoundResource(
    crossinline databaseQuery: () -> Flow<Result>,
    crossinline apiCall: suspend () -> Request,
    crossinline saveApiCallResult: suspend (Request) -> Unit,
    crossinline shouldFetch: (Result) -> Boolean = { true },
    crossinline onApiCallSuccess: () -> Unit = { },
    crossinline onApiCallFailed: (Throwable) -> Unit = { }
) = channelFlow {

    val data = databaseQuery().first()

    if (shouldFetch(data)) {
        val loading = launch {
            databaseQuery().collect {
                send(Resource.Loading(it))
            }
        }
        try {
            saveApiCallResult(apiCall())
            onApiCallSuccess()
            loading.cancel()
            databaseQuery().collect {
                send(Resource.Success(it))
            }
        } catch (throwable: Throwable) {
            onApiCallFailed(throwable)
            loading.cancel()
            databaseQuery().collect {
                send(Resource.Error(it, throwable))
            }
        }
    } else {
        databaseQuery().collect {
            send(Resource.Success(it))
        }
    }

}