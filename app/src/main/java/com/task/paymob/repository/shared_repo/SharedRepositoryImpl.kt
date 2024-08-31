package com.task.paymob.repository.shared_repo


import com.task.paymob.datasource.local.MovieDao
import com.task.paymob.model.Movie
import com.task.paymob.utils.api.AppResult


class SharedRepositoryImpl(
    private val dao : MovieDao
) : SharedRepository {

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


