package com.task.paymob.viewmodel.movies_details


import android.content.Context
import com.task.paymob.base.BaseViewModel
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.SingleLiveEvent

class MoviesDetailsViewModel(private val repository: MoviesDetailsViewModel) : BaseViewModel() {


//    val responseGetFavoriteItems = SingleLiveEvent<ResponseGetMovies>()
//
//
//    fun getMoviesForHome(context: Context) {
//        call({
//            return@call repository.getFavoriteItems(context)
//        }) {
//            when (it) {
//                is AppResult.Success -> {
//                    responseGetFavoriteItems.postValue(it.successData!!)
//                }
//
//                is AppResult.Error -> {
//
//                    showError.postValue(it.message)
//                }
//            }
//        }
//
//    }
}