package com.cmoney.backend2.virtualtrading2.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.createaccount.CreateAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccountratio.GetAccountRatioRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getaccountratio.GetAccountRatioResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.getallaccount.GetAllAccountResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate.CreateDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate.DeleteDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate.GetAllDelegateRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getalldelegate.GetAllDelegateResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getallsuccessdeal.GetAllSuccessDealResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail.GetDelegateDetailRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getdelegatedetail.GetDelegateDetailResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetAllInventoryRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getinventory.GetAllInventoryResponseBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail.GetSuccessDealDetailRequestBody
import com.cmoney.backend2.virtualtrading2.service.api.tseotc.getsuccessdealdetail.GetSuccessDealDetailResponseBody
import retrofit2.Response
import retrofit2.http.*

interface VirtualTrading2Service {
    /**
     * 創建帳戶
     */
    @RecordApi
    @POST
    suspend fun createAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CreateAccountRequestBody
    ): Response<CreateAccountResponseBody>

    /**
     * 建立上市上櫃委託單
     */
    @RecordApi
    @POST
    suspend fun createTseOtcDelegate(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: CreateDelegateRequestBody
    ): Response<CreateDelegateResponseBody>

    /**
     * 刪除上市上櫃委託單
     */
    @RecordApi
    @POST
    suspend fun deleteTseOtcDelegate(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: DeleteDelegateRequestBody
    ): Response<DeleteDelegateResponseBody>

    /**
     * 取得帳號資訊
     */
    @RecordApi
    @POST
    suspend fun getAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetAccountRequestBody
    ): Response<GetAccountResponseBody>

    /**
     * 取得所有帳號資訊
     */
    @RecordApi
    @POST
    suspend fun getAllAccount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetAllAccountRequestBody
    ): Response<GetAllAccountResponseBody>

    /**
     * 取得帳號報酬率
     */
    @RecordApi
    @POST
    suspend fun getAccountRatio(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetAccountRatioRequestBody
    ): Response<GetAccountRatioResponseBody>

    /**
     * 取得上市櫃所有的委託單
     */
    @RecordApi
    @POST
    suspend fun getTseOtcAllDelegate(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetAllDelegateRequestBody
    ): Response<GetAllDelegateResponseBody>

    /**
     * 取得上市櫃的委託單細節
     */
    @RecordApi
    @POST
    suspend fun getTseOtcDelegateDetail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetDelegateDetailRequestBody
    ): Response<GetDelegateDetailResponseBody>

    /**
     * 取得上市櫃所有的成交單
     */
    @RecordApi
    @POST
    suspend fun getTseOtcAllSuccessDeal(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetAllSuccessDealRequestBody
    ): Response<GetAllSuccessDealResponseBody>

    /**
     * 取得上市櫃的成交單細節
     */
    @RecordApi
    @POST
    suspend fun getTseOtcSuccessDealDetail(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetSuccessDealDetailRequestBody
    ): Response<GetSuccessDealDetailResponseBody>

    /**
     * 取得上市櫃的庫存
     */
    @RecordApi
    @POST
    suspend fun getTseOtcAllInventory(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: GetAllInventoryRequestBody
    ): Response<GetAllInventoryResponseBody>
}