package com.task.paymob.viewmodel.movies_details


import com.task.paymob.base.BaseViewModel
import com.task.paymob.model.Movie
import com.task.paymob.repository.movieDetails.MovieDetailsRepository
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.utils.AppResult
import com.task.paymob.utils.SingleLiveEvent

class MoviesDetailsViewModel(private val repository: MovieDetailsRepository,private val sharedRepository: SharedRepository) : BaseViewModel() {

    val responseIsThisMovieFavorite = SingleLiveEvent<Boolean>()
    val responseIsMovieDeleted = SingleLiveEvent<Boolean>()
    val responseAddedToFavorite = SingleLiveEvent<Boolean>()
    fun isThisMovieFavorite(id: Int) {
        call({
            return@call repository.isThisMovieFavorite(id)
        }) {
            when (it) {
                is AppResult.Success -> {
                    responseIsThisMovieFavorite.postValue(it.successData)
                }
                is AppResult.Error -> {
                    showError.postValue(it.message)
                }
            }
        }
    }

    fun deleteFavoriteMovie(movie: Movie){
        call({
            return@call sharedRepository.deleteMovieFromFavorite(movie)
        }) {
            when (it) {
                is AppResult.Success -> {
                    responseIsMovieDeleted.postValue(it.successData)
                }
                is AppResult.Error -> {
                    showError.postValue(it.message)
                }
            }
        }
    }
    fun addMovieToFavorite(movie: Movie){
        call({
            return@call sharedRepository.addMovieToFavorite(movie)
        }) {
            when (it) {
                is AppResult.Success -> {
                    responseAddedToFavorite.postValue(it.successData)
                }
                is AppResult.Error -> {
                    showError.postValue(it.message)
                }
            }
        }
    }
}