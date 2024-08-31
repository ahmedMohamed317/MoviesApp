package com.task.paymob.repository.home

import com.task.paymob.datasource.remote.home.HomeApi
import com.task.paymob.datasource.local.MovieDao
import com.task.paymob.model.Movie
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.api.AppResult
import com.task.paymob.utils.app.Utils.handleApiError
import com.task.paymob.utils.app.Utils.handleSuccess

class HomeRepositoryImpl(
    private val api: HomeApi, private val dao : MovieDao,
) : HomeRepository {
    // return Appresult with movies data if success or error if not
    override suspend fun getMovies( year: String , sortBy: String , page: Int): AppResult<ResponseGetMovies> {
        return try {
            val response = api.getMoviesForHome(year ,sortBy,page)
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    // return Appresult with favorite movies data if success or error if not
    override suspend fun getAllFavoriteMovies(): AppResult<List<Movie>> {
        return try {
            val movies = dao.getFavoriteMovies()
            AppResult.Success(movies)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }




}


