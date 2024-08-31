package com.task.paymob.repository.movieDetails

import com.task.paymob.utils.api.AppResult

interface MovieDetailsRepository {


    suspend fun isThisMovieFavorite(movieId: Int): AppResult<Boolean>

}