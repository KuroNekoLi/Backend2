package com.cmoney.backend2.product.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.product.service.api.GraphQLQuery
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProductDataProviderService {
    @RecordApi
    @POST("ProductDataProvider/Product/GraphQLQuery")
    suspend fun getProductByGraphQL(
        @Header("Authorization") authorization: String,
        @Body query: GraphQLQuery
    ): Response<ResponseBody>
}