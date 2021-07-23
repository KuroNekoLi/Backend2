package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.customgroup2.service.api.data.RawStock
import com.cmoney.backend2.customgroup2.service.api.searchstocks.SearchStocksRequestBody
import com.cmoney.backend2.customgroup2.service.api.searchstocksbymarkettype.SearchStocksByMarketTypeRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CustomGroup2Service {

    /**
     * 根據關鍵字、回傳語系搜尋股市標的
     */
    @RecordApi
    @POST("CustomGroupService/api/searchstocks")
    suspend fun searchStocks(
        @Header("Authorization") authorization: String,
        @Body requestBody: SearchStocksRequestBody
    ): Response<List<RawStock?>>

    /**
     * 根據關鍵字、回傳語系、市場類別搜尋股市標的
     */
    @RecordApi
    @POST("CustomGroupService/api/searchstocksbymarkettype")
    suspend fun searchStocksByMarketType(
        @Header("Authorization") authorization: String,
        @Body requestBody: SearchStocksByMarketTypeRequestBody
    ): Response<List<RawStock?>>
}