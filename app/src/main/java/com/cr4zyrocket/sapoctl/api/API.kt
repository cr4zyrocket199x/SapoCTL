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
                .addHeader("X-Sapo-SessionId", "3fb6993a-0cbf-446f-42c0-c1f04939aa8e")
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