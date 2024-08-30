package com.task.paymob.repository.home


import com.task.paymob.api.home.HomeApi
import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.AppResult
import com.task.paymob.utils.Utils.handleApiError
import com.task.paymob.utils.Utils.handleSuccess

class HomeRepositoryImpl(
    private val api: HomeApi,
) : HomeRepository {
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


}