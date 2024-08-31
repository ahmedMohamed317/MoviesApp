package com.task.paymob.repository.home


import android.content.Context
import com.task.paymob.datasource.remote.home.HomeApi
import com.task.paymob.datasource.local.AppDatabase
import com.task.paymob.model.Movie
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.AppResult
import com.task.paymob.utils.Utils.handleApiError
import com.task.paymob.utils.Utils.handleSuccess

class HomeRepositoryImpl(
    private val api: HomeApi, context: Context
) : HomeRepository {
    private val dao = AppDatabase.getDaoInstance(context)

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


    override suspend fun getAllFavoriteMovies(): AppResult<List<Movie>> {
        return try {
            val movies = dao.getFavoriteMovies()
            AppResult.Success(movies)
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }




}


