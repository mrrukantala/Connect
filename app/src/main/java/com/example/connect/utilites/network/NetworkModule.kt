package com.example.connect.utilites.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.connect.BuildConfig
import com.example.connect.utilites.app.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides



    fun provideRetrofit(okHttp: OkHttpClient, @ApplicationContext context: Context) : Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory
                .create())
            client(
                okHttp.newBuilder().addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .collector(ChuckerCollector(context, true) )
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                ).build()
            )
            baseUrl(BuildConfig.API_BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(requestInterceptor: RequestInterceptor) : OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
        }.build()

    @Provides
    fun provideRequestInterceptor(preferences: SharedPreferences) : RequestInterceptor =  RequestInterceptor(preferences)

}