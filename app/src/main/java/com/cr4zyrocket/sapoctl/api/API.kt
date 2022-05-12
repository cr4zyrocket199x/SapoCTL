package com.cr4zyrocket.sapoctl.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(CustomInterceptor())
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