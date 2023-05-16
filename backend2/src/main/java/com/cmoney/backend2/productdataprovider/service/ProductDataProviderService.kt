package com.cmoney.backend2.productdataprovider.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.productdataprovider.service.api.GraphQLQuery
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ProductDataProviderService {
    @RecordApi
    @POST
    suspend fun getProductByGraphQL(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body query: GraphQLQuery
    ): Response<ResponseBody>
}