package com.task.paymob.fake_data_source

import com.task.paymob.model.Movie
import com.task.paymob.repository.movieDetails.MovieDetailsRepository
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.utils.AppResult

class FakeMoviesDetailsRepository : MovieDetailsRepository, SharedRepository {

    val favoriteMovies = mutableListOf<Movie>()



    override suspend fun addMovieToFavorite(movie: Movie): AppResult<Boolean> {
        favoriteMovies.add(movie)
        return AppResult.Success(true)
    }

    override suspend fun deleteMovieFromFavorite(movie: Movie): AppResult<Boolean> {
        favoriteMovies.remove(movie)
        return AppResult.Success(true)
    }

    override suspend fun isThisMovieFavorite(movieId: Int): AppResult<Boolean> {
        return if (favoriteMovies.any { it.id == movieId }) {
            AppResult.Success(true)
        } else {
            AppResult.Success(false)
        }
    }
}
