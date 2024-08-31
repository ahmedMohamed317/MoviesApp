package com.task.paymob.fake_data_source

import com.task.paymob.model.Movie
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.utils.api.AppResult

class FakeHomeRepository : HomeRepository, SharedRepository {

    val apiMovies = mutableListOf<Movie>()
    val favoriteMovies = mutableListOf<Movie>()

    override suspend fun getMovies(year: String, sortBy: String, page: Int): AppResult<ResponseGetMovies> {
        val response = ResponseGetMovies(page ,apiMovies, true, message = "")
        return AppResult.Success(response)
    }

    override suspend fun getAllFavoriteMovies(): AppResult<List<Movie>> {
        return AppResult.Success(favoriteMovies)
    }

    override suspend fun addMovieToFavorite(movie: Movie): AppResult<Boolean> {
        favoriteMovies.add(movie)
        return AppResult.Success(true)
    }

    override suspend fun deleteMovieFromFavorite(movie: Movie): AppResult<Boolean> {
        favoriteMovies.remove(movie)
        return AppResult.Success(true)
    }
}
