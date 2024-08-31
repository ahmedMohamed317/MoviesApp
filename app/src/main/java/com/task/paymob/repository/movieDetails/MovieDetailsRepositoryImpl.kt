package com.task.paymob.repository.movieDetails


import com.task.paymob.datasource.local.MovieDao
import com.task.paymob.utils.api.AppResult

class MovieDetailsRepositoryImpl(
    private val dao : MovieDao
) : MovieDetailsRepository {
    // return Appresult with boolean value if success or error if not , the boolean depends on if the movie is favorite or not
    override suspend fun isThisMovieFavorite(movieId : Int): AppResult<Boolean> {
        return try {
            val bool = dao.isThisMovieFavorite(movieId)
            AppResult.Success(bool)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

}