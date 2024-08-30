package com.task.paymob.api.home


import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.APIS
import com.task.paymob.utils.QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface HomeApi {
    @GET(APIS.GET_MOVIES)
    suspend fun getMoviesForHome(
        @Query(QUERY.QUERY_PRIMARY_RELEASE_YEAR) year: String ,
        @Query(QUERY.QUERY_SORT) sortByType: String,
        @Query(QUERY.QUERY_PAGE) page: Int,
        ): Response<ResponseGetMovies>

}