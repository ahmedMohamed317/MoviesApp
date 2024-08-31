package com.task.paymob.repository.home

import com.task.paymob.model.Movie
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.api.AppResult


interface HomeRepository {
    suspend fun getMovies( year: String , sortBy: String , page: Int): AppResult<ResponseGetMovies>
    suspend fun getAllFavoriteMovies(): AppResult<List<Movie>>


}