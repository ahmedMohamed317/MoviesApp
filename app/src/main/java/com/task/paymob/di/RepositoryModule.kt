package com.task.paymob.di

import android.content.Context
import com.task.paymob.datasource.remote.home.HomeApi
import com.task.paymob.repository.movieDetails.MovieDetailsRepository
import com.task.paymob.repository.movieDetails.MovieDetailsRepositoryImpl
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.repository.home.HomeRepositoryImpl
import com.task.paymob.repository.shared_repo.SharedRepository
import com.task.paymob.repository.shared_repo.SharedRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideHomeRepository(
        homeApi: HomeApi,
        context: Context

    ): HomeRepository {
        return HomeRepositoryImpl(homeApi,context)
    }
    single { provideHomeRepository( get(),androidContext() ) }

    fun provideMovieDetailsRepository(
        context: Context
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(context)
    }
    single { provideMovieDetailsRepository( androidContext()) }

    fun provideSharedRepository(
        context: Context
    ): SharedRepository {
        return SharedRepositoryImpl(context)
    }
    factory { provideSharedRepository( androidContext()) }

}