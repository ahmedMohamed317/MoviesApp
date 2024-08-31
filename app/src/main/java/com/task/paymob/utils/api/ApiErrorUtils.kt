package com.task.paymob.utils.api

import android.content.ContentValues.TAG
import com.google.gson.GsonBuilder
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

object ApiErrorUtils {
    fun parseError(response: Response<*>): ApiError {

        val gson = GsonBuilder().create()
        val error: ApiError
        try {
            error = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
        } catch (e: IOException) {
            e.message?.let { Timber.tag(TAG).d(it) }
            return ApiError()
        }
        return error
    }
}