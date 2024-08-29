package com.task.paymob.utils

import android.app.Application
import android.content.Context
import com.task.paymob.BuildConfig
import com.task.paymob.di.repositoryModule
import com.task.paymob.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

class PaymobMoviesApp : Application() {


    val listModule: List<Module> = listOf(
        viewModelModule,
        repositoryModule,
    )

    companion object {
        private var mContext: PaymobMoviesApp? = null

        fun getContext(): Context? {
            return mContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@PaymobMoviesApp)

            modules(
                listModule
            )
        }

    }



}