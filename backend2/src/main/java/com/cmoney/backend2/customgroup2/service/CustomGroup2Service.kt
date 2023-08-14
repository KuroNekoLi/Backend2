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
    @POST
    suspend fun searchStocks(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Header("Accept-Language") language: String,
        @Body requestBody: SearchStocksRequestBody
    ): Response<List<RawStock?>>

    /**
     * 根據關鍵字、回傳語系、市場類別搜尋股市標的
     */
    @RecordApi
    @POST
    suspend fun searchStocksByMarketType(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Header("Accept-Language") language: String,
        @Body requestBody: SearchStocksByMarketTypeRequestBody
    ): Response<List<RawStock?>>

    /**
     * 取得自選股文件
     *
     * @param authorization 權限JWT Token
     * @param filters 篩選條件，以逗號區隔，條件格式為{欄位名稱:欄位數值}
     */
    @RecordApi
    @GET
    suspend fun getCustomGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query(value = "filters") filters: String
    ): Response<Documents>

    /**
     * 取得自選股文件
     *
     * @param authorization 權限JWT Token
     *
     * @return 指定自選股文件
     */
    @RecordApi
    @GET
    suspend fun getCustomGroupBy(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Document>

    /**
     * 創建自選股文件
     *
     * @param authorization 權限JWT Token
     * @param baseDocument 基礎文件內容
     * @return 新文件ID
     */
    @RecordApi
    @POST
    suspend fun createCustomGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body baseDocument: Document
    ): Response<CreateCustomGroupResponseBody>

    /**
     * 更新自選股文件
     *
     * @param authorization 權限JWT Token
     */
    @RecordApi
    @PUT
    suspend fun updateCustomGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body newGroup: Document
    ): Response<Void>

    /**
     * 刪除自選股文件
     *
     * @param authorization 權限JWT Token
     *
     */
    @RecordApi
    @DELETE
    suspend fun deleteCustomGroup(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<Void>

    /**
     * 取得使用者設定文件
     *
     * @param authorization 權限JWT Token
     * @return 使用者設定
     */
    @RecordApi
    @GET
    suspend fun getUserConfiguration(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): Response<UserConfigurationDocument>

    /**
     * 更新使用者設定文件
     *
     * @param authorization 權限JWT Token
     * @return 是否成功
     */
    @RecordApi
    @PUT
    suspend fun updateUserConfiguration(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body newUserConfigurationDocument: UserConfigurationDocument
    ): Response<Void>
}