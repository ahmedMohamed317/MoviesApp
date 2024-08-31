package com.task.paymob.repository.movieDetails


import android.content.Context
import com.task.paymob.datasource.local.AppDatabase
import com.task.paymob.utils.AppResult

class MovieDetailsRepositoryImpl(
      context: Context
) : MovieDetailsRepository {
    private val dao = AppDatabase.getDaoInstance(context)

    override suspend fun isThisMovieFavorite(movieId : Int): AppResult<Boolean> {
        return try {
            val bool = dao.isThisMovieFavorite(movieId)
            AppResult.Success(bool)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

}