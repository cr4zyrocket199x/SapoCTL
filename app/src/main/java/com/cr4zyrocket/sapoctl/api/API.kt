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
                .addHeader("X-Sapo-SessionId", "3ad3f3b0-0bf9-5f01-81af-4183f72d5752")
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
    val apiServiceGetData: APIService.GetData by lazy {
        retrofit.create(APIService.GetData::class.java)
    }
    val apiServicePostData: APIService.PostData by lazy {
        retrofit.create(APIService.PostData::class.java)
    }
}