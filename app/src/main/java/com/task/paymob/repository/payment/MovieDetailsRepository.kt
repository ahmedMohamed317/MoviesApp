package com.task.paymob.repository.payment

import com.task.paymob.utils.AppResult

interface MovieDetailsRepository {


    suspend fun isThisMovieFavorite(movieId: Int): AppResult<Boolean>

}