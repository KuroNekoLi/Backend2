package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.customgroup2.service.api.createcustomgroup.CreateCustomGroupResponseBody
import com.cmoney.backend2.customgroup2.service.api.data.Document
import com.cmoney.backend2.customgroup2.service.api.data.RawStock
import com.cmoney.backend2.customgroup2.service.api.data.UserConfigurationDocument
import com.cmoney.backend2.customgroup2.service.api.getcustomgroup.Documents
import com.cmoney.backend2.customgroup2.service.api.searchstocks.SearchStocksRequestBody
import com.cmoney.backend2.customgroup2.service.api.searchstocksbymarkettype.SearchStocksByMarketTypeRequestBody
import retrofit2.Response
import retrofit2.http.*

interface CustomGroup2Service {

    /**
     * 根據關鍵字、回傳語系搜尋股市標的
     */
    @RecordApi
    @POST("CustomGroupService/api/searchstocksbyappid")
    suspend fun searchStocks(
        @Header("Authorization") authorization: String,
        @Header("Accept-Language") language: String,
        @Body requestBody: SearchStocksRequestBody
    ): Response<List<RawStock?>>

    /**
     * 根據關鍵字、回傳語系、市場類別搜尋股市標的
     */
    @RecordApi
    @POST("CustomGroupService/api/searchstocksbycommoditytype")
    suspend fun searchStocksByMarketType(
        @Header("Authorization") authorization: String,
        @Header("Accept-Language") language: String,
        @Body requestBody: SearchStocksByMarketTypeRequestBody
    ): Response<List<RawStock?>>

    /**
     * 取得自選股文件
     *
     * @param authorization 權限JWT Token
     * @param filters 篩選條件
     */
    @RecordApi
    @GET("custom-group/api")
    suspend fun getCustomGroup(
        @Header("Authorization") authorization: String,
        @QueryMap filters: Map<String, String>
    ): Response<Documents>

    /**
     * 取得自選股文件
     *
     * @param authorization 權限JWT Token
     * @param id 指定文件ID
     * @return 指定自選股文件
     */
    @RecordApi
    @GET("custom-group/api/{id}")
    suspend fun getCustomGroup(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Response<Document>

    /**
     * 創建自選股文件
     *
     * @param authorization 權限JWT Token
     * @param baseDocument 基礎文件內容
     * @return 新文件ID
     */
    @RecordApi
    @POST("custom-group/api")
    suspend fun createCustomGroup(
        @Header("Authorization") authorization: String,
        @Body baseDocument: Document
    ): Response<CreateCustomGroupResponseBody>

    /**
     * 更新自選股文件
     *
     * @param authorization 權限JWT Token
     * @param id 指定文件ID
     */
    @RecordApi
    @PUT("custom-group/api/{id}")
    suspend fun updateCustomGroup(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body newGroup: Document
    ): Response<Void>

    /**
     * 刪除自選股文件
     *
     * @param authorization 權限JWT Token
     * @param id 指定文件ID
     */
    @RecordApi
    @DELETE("custom-group/api/{id}")
    suspend fun deleteCustomGroup(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Response<Void>

    /**
     * 取得使用者設定文件
     *
     * @param authorization 權限JWT Token
     * @return 使用者設定
     */
    @RecordApi
    @GET("custom-group/api/_configuration")
    suspend fun getUserConfiguration(
        @Header("Authorization") authorization: String
    ): Response<UserConfigurationDocument>

    /**
     * 更新使用者設定文件
     *
     * @param authorization 權限JWT Token
     * @return 是否成功
     */
    @RecordApi
    @PUT("custom-group/api/_configuration")
    suspend fun updateUserConfiguration(
        @Header("Authorization") authorization: String,
        @Body newUserConfigurationDocument: UserConfigurationDocument
    ): Response<Void>
}