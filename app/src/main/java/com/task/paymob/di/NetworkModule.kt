package com.task.paymob.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.task.paymob.utils.APIS
import com.task.paymob.utils.USER_DATA
import com.task.paymob.utils.USER_INFO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    val connectTimeout: Long = 40
    val readTimeout: Long = 40

    fun provideHttpClient(context: Context): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                //Timber.d("bearer ${USER_DATA.CURRENT_USER?.data?.token}")
                val request: Request = original.newBuilder()
                    .header(
                        USER_DATA.AUTHORIZATION,

                        USER_DATA.BEARER + USER_INFO.TOKEN
                    )
                    .build()
                chain.proceed(request)
            })
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient(androidContext()) }
    single {
        val baseUrl = APIS.BASE_URL
        provideRetrofit(get(), baseUrl)
    }


}