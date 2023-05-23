package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.virtualassets.service.api.exchange.ExchangeRequestBody
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeRequestBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody
import retrofit2.Response
import retrofit2.http.*

interface VirtualAssetsService {
    /**
     * 取得可兌換商品清單
     */
    @RecordApi
    @GET
    suspend fun getExchangeProductList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("AppId") appId: Int,
        @Query("Guid") guid: String
    ): Response<GetExchangeProductListResponseBody>

    /**
     * 批次取得會員最後一次兌換指定商品的時間
     */
    @RecordApi
    @POST
    suspend fun getGroupLastExchangeTime(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: GetGroupLastExchangeTimeRequestBody
    ): Response<GetGroupLastExchangeTimeResponseBody>

    /**
     * 會員兌換指定商品
     */
    @RecordApi
    @POST
    suspend fun exchange(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body requestBody: ExchangeRequestBody
    ): Response<Void>
}