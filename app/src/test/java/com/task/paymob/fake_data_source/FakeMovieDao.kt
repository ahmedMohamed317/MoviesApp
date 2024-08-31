package com.task.paymob.fake_data_source

import com.task.paymob.datasource.local.MovieDao
import com.task.paymob.model.Movie

class FakeMovieDao : MovieDao {

    private val movies = mutableListOf<Movie>()
    override suspend fun getFavoriteMovies(): List<Movie> {
        return movies
    }

    override suspend fun addMovieToFavorite(movie: Movie): Long {
        movies.add(movie)
        return 1
    }

    override suspend fun deleteMovieFromFavorite(movie: Movie): Int {
        movies.remove(movie)
        return 1
    }

    override suspend fun isThisMovieFavorite(movieId: Int): Boolean {
        return movies.any { it.id == movieId }
    }

}
