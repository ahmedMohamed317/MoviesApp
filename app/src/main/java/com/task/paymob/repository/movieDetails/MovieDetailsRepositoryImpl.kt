package com.task.paymob.repository.movieDetails


import com.task.paymob.datasource.local.MovieDao
import com.task.paymob.utils.api.AppResult

class MovieDetailsRepositoryImpl(
    private val dao : MovieDao
) : MovieDetailsRepository {

    override suspend fun isThisMovieFavorite(movieId : Int): AppResult<Boolean> {
        return try {
            val bool = dao.isThisMovieFavorite(movieId)
            AppResult.Success(bool)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

}