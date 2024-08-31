package com.task.paymob.viewmodel.home


import com.task.paymob.base.BaseViewModel
import com.task.paymob.model.Movie
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.utils.api.AppResult
import com.task.paymob.utils.api.SingleLiveEvent

class HomeViewModel(private val repository: HomeRepository,private val sharedRepository: SharedRepository) : BaseViewModel() {


    val responseGetMovies = SingleLiveEvent<ResponseGetMovies>()
    val responseGetFavoriteMovies = SingleLiveEvent<List<Movie>>()
    val responseIsMovieDeleted = SingleLiveEvent<Boolean>()
    val responseAddedToFavorite = SingleLiveEvent<Boolean>()
    var currentPage = 1
    val favoriteMoviesList : MutableList<Movie> = mutableListOf()
    val moviesList : MutableList<Movie> = mutableListOf()

    fun getMoviesForHome(year: String ="2024", sortBy: String ="", page: Int) {
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


    fun getAllFavoriteMovies() {
        call({
            return@call repository.getAllFavoriteMovies()
        }) {
            when (it) {
                is AppResult.Success -> {
                    responseGetFavoriteMovies.postValue(it.successData)
                }
                is AppResult.Error -> {
                    showError.postValue(it.message)
                }
            }
        }
    }


    fun deleteFavoriteMovie(movie: Movie ){
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
    fun addMovieToFavorite(movie: Movie ){
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