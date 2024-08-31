package com.task.paymob.datasource.remote.home


import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.app.APIS
import com.task.paymob.utils.app.QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface HomeApi {
    @GET(APIS.GET_MOVIES)
    suspend fun getMoviesForHome(
        @Query(QUERY.QUERY_PRIMARY_RELEASE_YEAR) year: String,
        @Query(QUERY.QUERY_SORT) sortByType: String,
        @Query(QUERY.QUERY_PAGE) page: Int,
        ): Response<ResponseGetMovies>

}