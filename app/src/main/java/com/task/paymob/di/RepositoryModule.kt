package com.task.paymob.di

import com.task.paymob.datasource.local.MovieDao
import com.task.paymob.datasource.remote.home.HomeApi
import com.task.paymob.repository.movieDetails.MovieDetailsRepository
import com.task.paymob.repository.movieDetails.MovieDetailsRepositoryImpl
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.repository.home.HomeRepositoryImpl
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.repository.shared_repo.SharedRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideHomeRepository(
        homeApi: HomeApi,
        movieDao: MovieDao

    ): HomeRepository {
        return HomeRepositoryImpl(homeApi,movieDao)
    }
    single { provideHomeRepository( get(),get() ) }

    fun provideMovieDetailsRepository(
        movieDao: MovieDao
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(movieDao)
    }
    single { provideMovieDetailsRepository( get() ) }

    fun provideSharedRepository(
        movieDao: MovieDao
    ): SharedRepository {
        return SharedRepositoryImpl(movieDao)
    }
    factory { provideSharedRepository( get()) }

}