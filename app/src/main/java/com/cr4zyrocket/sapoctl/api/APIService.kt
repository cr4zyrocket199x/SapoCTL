package com.cr4zyrocket.sapoctl.api

import com.cr4zyrocket.sapoctl.api.model.ResponseProduct
import com.cr4zyrocket.sapoctl.api.model.ResponseProductList
import com.cr4zyrocket.sapoctl.api.model.ResponseVariant
import com.cr4zyrocket.sapoctl.api.model.ResponseVariantList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    interface GetData{
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
    interface PostData{

    }
}