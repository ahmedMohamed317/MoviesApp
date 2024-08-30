package com.task.paymob.di

import android.content.Context
import com.task.paymob.api.home.HomeApi
import com.task.paymob.repository.payment.MovieDetailsRepository
import com.task.paymob.repository.payment.MovieDetailsRepositoryImpl
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.repository.home.HomeRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideHomeRepository(
        homeApi: HomeApi
    ): HomeRepository {
        return HomeRepositoryImpl(homeApi)
    }
    single { provideHomeRepository( get() ) }

    fun provideMovieDetailsRepository(
        context: Context
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(context)
    }
    single { provideMovieDetailsRepository( androidContext()) }


}