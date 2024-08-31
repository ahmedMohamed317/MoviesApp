package com.task.paymob.utils.app

import android.app.Application
import com.task.paymob.BuildConfig
import com.task.paymob.di.apiModule
import com.task.paymob.di.networkModule
import com.task.paymob.di.repositoryModule
import com.task.paymob.di.roomModule
import com.task.paymob.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

class PaymobMoviesApp : Application() {


    private val listModule: List<Module> = listOf(
        apiModule,
        viewModelModule,
        repositoryModule,
        networkModule,
        roomModule
    )

    companion object {
        private var mContext: PaymobMoviesApp? = null

//        fun getContext(): Context? {
//            return mContext
//        }

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PaymobMoviesApp)

            modules(
                listModule
            )
        }

    }



}