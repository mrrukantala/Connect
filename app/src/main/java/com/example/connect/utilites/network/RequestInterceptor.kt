package com.example.connect.utilites.network

import com.example.connect.utilites.app.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(
    private val sharedPreferences: SharedPreferences
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getToken()
        val newReq = chain.request().newBuilder().addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newReq)
    }

}