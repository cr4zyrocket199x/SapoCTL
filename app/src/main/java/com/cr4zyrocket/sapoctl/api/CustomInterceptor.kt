package com.cr4zyrocket.sapoctl.api

import okhttp3.*
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request()
            .newBuilder()
            .addHeader("X-Sapo-SessionId", "87446e3d-df89-15fc-7890-66fa60fa53e8")
            .build()
        return chain.proceed(newRequest)
    }
}