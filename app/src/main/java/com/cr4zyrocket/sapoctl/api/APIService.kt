package com.cr4zyrocket.sapoctl.api

import com.cr4zyrocket.sapoctl.api.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("admin/products.json?limit=20")
    suspend fun getResponseProductList(@Query("page") currentPage: Long,@Query("query")keySearch: String): Response<ResponseProductList>

    @GET("admin/variants/search.json?limit=20")
    suspend fun getResponseVariantList(@Query("page") currentPage: Long,@Query("query")keySearch: String): Response<ResponseVariantList>

    @GET("admin/products/{productId}.json")
    suspend fun getProduct(@Path("productId") productId: Long): Response<ResponseProduct>


    @GET("admin/products/{productId}/variants/{variantId}.json")
    suspend fun getVariant(
            @Path("productId") productId: Long,
            @Path("variantId") variantId: Long
    ): Response<ResponseVariant>
}