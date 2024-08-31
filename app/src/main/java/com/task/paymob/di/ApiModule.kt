package com.task.paymob.di


import com.task.paymob.datasource.remote.home.HomeApi
import org.koin.dsl.module
import retrofit2.Retrofit


val apiModule = module {


    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }
    single { provideHomeApi(get()) }

}