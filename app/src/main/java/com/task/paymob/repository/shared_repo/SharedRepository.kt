package com.task.paymob.repository.shared_repo

import com.task.paymob.model.Movie
import com.task.paymob.utils.api.AppResult


interface SharedRepository {

    suspend fun addMovieToFavorite(movie: Movie): AppResult<Boolean>
    suspend fun deleteMovieFromFavorite(movie: Movie): AppResult<Boolean>

}