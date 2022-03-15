package com.cmoney.backend2.vtwebapi.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardRequestBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.GetCardInstanceSnsResponseBody
import com.cmoney.backend2.vtwebapi.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.vtwebapi.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getattendgroup.GetAttendGroupResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getstockinventorylist.GetStockInventoryListResponseBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardResponseBody
import retrofit2.Response
import retrofit2.http.*

interface VirtualTradeService {
    /**
     * 取得會員所有帳戶資訊
     */
    @RecordApi
    @GET
    suspend fun getAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("destMemberPk") destMemberPk: Long?,
        @Query("skipCount") skipCount: Int?,
        @Query("fetchSize") fetchSize: Int?,
        @Query("needGroupAccount") needGroupAccount: Boolean?,
        @Query("needExtendInfo") needExtendInfo: Boolean?
    ): Response<List<GetAccountResponseBody>>

    /**
     * 創建一個投資帳戶
     */
    @RecordApi
    @POST
    suspend fun createAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CreateAccountRequestBody
    ): Response<Void>

    /**
     * 依照卡片類型回傳會員持有的卡片序號
     */
    @RecordApi
    @GET
    suspend fun getCardInstanceSns(
        @Url url: String,
        @Header("Authorization") authorization: String,
    ): Response<GetCardInstanceSnsResponseBody>

    /**
     * 購買使用者道具卡
     */
    @RecordApi
    @POST
    suspend fun purchaseProductCard(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: PurchaseProductCardRequestBody
    ): Response<PurchaseProductCardResponseBody>

    /**
     * 取得我參加的競技場
     */
    @RecordApi
    @GET
    suspend fun getAttendGroup(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("fetchIndex") fetchIndex: Int?,
        @Query("fetchSize") fetchSize: Int?
    ): Response<List<GetAttendGroupResponseBody>>

    /**
     * 取得指定帳號股票庫存列表
     */
    @RecordApi
    @GET
    suspend fun getStockInventoryList(
        @Url url: String,
        @Header("Authorization") authorization: String,
    ): Response<List<GetStockInventoryListResponseBody>>
}