package com.task.paymob.datasource.remote.home


import com.task.paymob.model.ResponseGetMovies
import com.task.paymob.utils.app.APIS
import com.task.paymob.utils.app.QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface HomeApi {
    // getting movies from api with query params , year for the year of the movies which is 2024 is determined in task
    // sort is default and page varies depends on the page showing
    @GET(APIS.GET_MOVIES)
    suspend fun getMoviesForHome(
        @Query(QUERY.QUERY_PRIMARY_RELEASE_YEAR) year: String,
        @Query(QUERY.QUERY_SORT) sortByType: String,
        @Query(QUERY.QUERY_PAGE) page: Int,
        ): Response<ResponseGetMovies>

}