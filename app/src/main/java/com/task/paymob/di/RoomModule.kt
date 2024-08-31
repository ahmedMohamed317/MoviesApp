package com.task.paymob.di


import android.content.Context
import com.task.paymob.datasource.local.AppDatabase
import com.task.paymob.datasource.local.MovieDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val roomModule = module {
    // providing dao instance
    fun provideRoomDao(context: Context): MovieDao {
        return AppDatabase.getDaoInstance(context)
    }
    single { provideRoomDao(androidContext()) }

}