package com.task.paymob.viewmodel.home


import com.task.paymob.base.BaseViewModel
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.utils.AppResult
import com.task.paymob.utils.SingleLiveEvent

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {


    val responseGetMovies = SingleLiveEvent<ResponseGetMovies>()
    fun getMoviesForHome(year: String, sortBy: String, page: Int) {
        call({
            return@call repository.getMovies(year, sortBy, page)
        }) {
            when (it) {
                is AppResult.Success -> {
                    responseGetMovies.postValue(it.successData)
                }

                is AppResult.Error -> {

                    showError.postValue(it.message)
                }
            }
        }

    }
}