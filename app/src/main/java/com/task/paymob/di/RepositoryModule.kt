package com.task.paymob.di

import android.content.Context
import com.task.paymob.repository.payment.MovieDetailsRepository
import com.task.paymob.repository.payment.MovieDetailsRepositoryImpl
import com.task.paymob.repository.home.HomeRepository
import com.task.paymob.repository.home.HomeRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideHomeRepository(
        context: Context
    ): HomeRepository {
        return HomeRepositoryImpl(context)
    }
    single { provideHomeRepository( androidContext()) }

    fun provideMovieDetailsRepository(
        context: Context
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(context)
    }
    single { provideMovieDetailsRepository( androidContext()) }


}