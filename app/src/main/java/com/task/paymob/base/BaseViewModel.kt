package com.task.paymob.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.paymob.utils.api.AppResult
import com.task.paymob.utils.api.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel :
    ViewModel() {
    private val statusCode = SingleLiveEvent<Int?>()
    val showError = SingleLiveEvent<String?>()
    private var job: Job? = null

/*
    this method does a coroutine in the viewmodel scope and handles the retun which is app result
  */
    fun <T> call(
        serverCall: suspend () -> AppResult<T>,
        onResponse: (AppResult<T>) -> Unit
    ) {
        job = viewModelScope.launch {
            when (val result = serverCall()) {
                is AppResult.Success -> {
                    onResponse(result)
                }

                is AppResult.Error -> {
                    showError.value = result.exception.message
                    statusCode.value = result.statusCode
                }

            }
        }
    }
    // canceling the job if oncleared is invoked which avoid memory leaks for background tasks
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}