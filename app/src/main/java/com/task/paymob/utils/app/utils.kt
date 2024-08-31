package com.task.paymob.utils.app

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.task.paymob.utils.api.ApiErrorUtils
import com.task.paymob.utils.api.AppResult
import retrofit2.Response


object Utils {
    // returning AppResult.Success with data if response is successful
    // returning AppResult.Error with exception message if response is not successful

    fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
        val error = ApiErrorUtils.parseError(resp)
        return AppResult.Error(Exception(error.message), resp.code())
    }

    fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
        response.body()?.let {
            return AppResult.Success(it)
        } ?: return handleApiError(response)
    }

    // loading the image with glide and applying center crop and rounded corners also managing placeholders
    fun loadImage(context: Context
                  , path : String
                  , imageView: ImageView
                  ,placeholder: Int
                  ,cornerRadiusValue: Int
    ) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusValue))

        Glide.with(context)
            .load(APIS.POSTER_URL + path)
            .apply(requestOptions)
            .placeholder(placeholder)
            .into(imageView)

    }
}
