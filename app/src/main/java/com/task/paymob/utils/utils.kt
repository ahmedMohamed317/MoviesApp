package com.task.paymob.utils

import android.content.Context
import android.provider.Settings
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import retrofit2.Response


object Utils {
    fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
        val error = ApiErrorUtils.parseError(resp)
        return AppResult.Error(Exception(error.message), resp.code())
    }

    fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
        response.body()?.let {
            return AppResult.Success(it)
        } ?: return handleApiError(response)
    }

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun loadImage(context: Context
                  , link : String
                  , imageView: ImageView
                  ,placeholder: Int
                  ,shouldAddApiLink:Boolean = true
                  ,cornerRadiusValue: Int
    ) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusValue))

        if (shouldAddApiLink){
            val newLink = ("APIS.BASE_URL" + link).replace("\\","/")
            Glide.with(context)
                .load(newLink)
                .apply(requestOptions)
                .placeholder(placeholder)
                .into(imageView)


        }
        else {
            Glide.with(context)
                .load(link)
                .apply(requestOptions)
                .placeholder(placeholder)
                .into(imageView)
        }
    }
}
