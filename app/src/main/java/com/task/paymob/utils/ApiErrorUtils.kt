package com.task.paymob.utils

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtils {
    fun parseError(response: Response<*>): ApiError {

        val gson = GsonBuilder().create()
        val error: ApiError
        try {
            error = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d(TAG, it) }
            return ApiError()
        }
        return error
    }
}