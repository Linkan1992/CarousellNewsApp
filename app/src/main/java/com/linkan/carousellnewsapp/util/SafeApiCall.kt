package com.linkan.carousellnewsapp.util

import retrofit2.Response

suspend fun <T> safeApiCall(apiFun: suspend () -> Response<T>?): ResultEvent<T> {

    try {
        val response = apiFun.invoke()
        response?.takeIf { it.isSuccessful && it.body() != null }
            ?.body()
            ?.let { body ->
                return ResultEvent.Success(data = body)
            }

        return ResultEvent.Error(
            if (!response?.message()
                    .isNullOrBlank()
            ) response.message() else "Something Went Wrong. Please try again later.", null
        )
    } catch (exe: Exception) {
        return ResultEvent.Error(exe.message ?: "No Data", null)
    }
}
