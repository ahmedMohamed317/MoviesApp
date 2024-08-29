package com.task.paymob.utils

import android.content.Context
import com.task.paymob.R
import com.task.paymob.utils.AppResult

fun Context.noNetworkConnectivityError(): AppResult.Error {
    return AppResult.Error(Exception(this.resources.getString(R.string.no_network_connectivity)))

}

