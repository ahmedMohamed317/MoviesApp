package com.task.paymob.repository.payment


import android.content.Context
import com.task.paymob.datasource.local.AppDatabase
import com.task.paymob.model.Movie
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.utils.AppResult

class MovieDetailsRepositoryImpl(
      context: Context
) : MovieDetailsRepository,SharedRepository {
    private val dao = AppDatabase.getDaoInstance(context)

    override suspend fun isThisMovieFavorite(movieId : Int): AppResult<Boolean> {
        return try {
            val bool = dao.isThisMovieFavorite(movieId)
            AppResult.Success(bool)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
    override suspend fun addMovieToFavorite(movie: Movie): AppResult<Boolean> {
        return try {
            val result = dao.addMovieToFavorite(movie)
            AppResult.Success(result > 0 )
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    override suspend fun deleteMovieFromFavorite(movie: Movie): AppResult<Boolean> {
        return try {
            val result = dao.deleteMovieFromFavorite(movie)
            AppResult.Success(result > 0 )
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}