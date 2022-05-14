package com.cr4zyrocket.sapoctl.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        addInterceptor {
            val newRequest: Request = it.request()
                .newBuilder()
                .addHeader("X-Sapo-SessionId", "9a9bc0d9-1323-8e04-7dd1-f19f38ef9731")
                .build()
            it.proceed(newRequest)
        }
    }.build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://interndev.mysapogo.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    val apiService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }
}