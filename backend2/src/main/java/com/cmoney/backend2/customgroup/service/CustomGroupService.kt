package com.cmoney.backend2.customgroup.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.customgroup.service.api.addcustomgroup.NewCustomGroupWithError
import com.cmoney.backend2.customgroup.service.api.deletecustomgroup.DeleteCustomGroupCompleteWithError
import com.cmoney.backend2.customgroup.service.api.getcustomgroup.CustomGroupWithError
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder.CustomGroupWithOrderWithError
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist.CustomGroupWithOrderAndListWithError
import com.cmoney.backend2.customgroup.service.api.getcustomlist.CustomListWithError
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksRequestBody
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksResponseBody
import com.cmoney.backend2.customgroup.service.api.updatecustomgroupname.UpdateCustomGroupNameCompleteWithError
import com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder.UpdateCustomGroupOrderCompleteWithError
import com.cmoney.backend2.customgroup.service.api.updatecustomlist.UpdateCustomListCompleteWithError
import retrofit2.Response
import retrofit2.http.*

/**
 * MobileService-自選股
 */
interface CustomGroupService {

    /**
     * 服務1-1. 取得使用者自訂群組清單(如果使用者沒有會幫建5個清單)
     *
     * @param docType 群組類別，all: 全部，stock: 個股，broker: 券商，warrant: 權證，ustock: 美股，bond: 債券。
     *
     */
    @RecordApi(cmoneyAction = "getcustomgroup")
    @Deprecated("使用1-2 getCustomGroupWithOrder帶替")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun getCustomGroup(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getcustomgroup",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String
    ): Response<CustomGroupWithError>

    /**
     * 服務1-2. 取得使用者自訂群組含順序清單(如果使用者沒有會幫建5個清單)
     *
     * @param docType 群組類別，all: 全部，stock: 個股，broker: 券商，warrant: 權證，ustock: 美股，bond: 債券。
     *
     */
    @RecordApi(cmoneyAction = "getcustomgroupwithorder")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun getCustomGroupWithOrder(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getcustomgroupwithorder",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String
    ): Response<CustomGroupWithOrderWithError>

    /**
     * 服務2-1. 取得使用者目標群組內的股票清單
     *
     * @param docNo 自選股清單的編號
     */
    @RecordApi(cmoneyAction = "getcustomlist")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun getCustomGroupContent(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getcustomlist",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docno") docNo: Int
    ): Response<CustomListWithError>

    /**
     * 服務2-3. 取得使用者自訂群組含順序清單及股票清單
     *
     * @param docType 群組類別，all: 全部，stock: 個股，broker: 券商，warrant: 權證，ustock: 美股，bond: 債券。
     *
     */
    @RecordApi(cmoneyAction = "getcustomgroupwithorderandlist")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun getCustomGroupWithOrderAndList(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getcustomgroupwithorderandlist",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String
    ): Response<CustomGroupWithOrderAndListWithError>

    /**
     * 服務3 更新自選股清單的內容和名字
     *
     * @param docType 群組類別，all: 全部，stock: 個股，broker: 券商，warrant: 權證，ustock: 美股，bond: 債券。
     */
    @RecordApi(cmoneyAction = "updatecustomlist")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun updateCustomList(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "updatecustomlist",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String,
        @Field("docno") docNo: Int,
        @Field("docname") docName: String,
        @Field("list") list: String
    ): Response<UpdateCustomListCompleteWithError>

    /**
     * 服務4. 新增自選股群組
     */
    @RecordApi(cmoneyAction = "addcustomgroup")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun addCustomGroup(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "addcustomgroup",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String,
        @Field("docName") docName: String
    ): Response<NewCustomGroupWithError>

    /**
     * 服務5. 刪除自選股群組
     */
    @RecordApi(cmoneyAction = "deletecustomgroup")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun deleteCustomGroup(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "deletecustomgroup",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docNo") docNo: Int
    ): Response<DeleteCustomGroupCompleteWithError>

    /**
     * 服務6. 修改自選股群組名稱
     */
    @RecordApi(cmoneyAction = "updatecustomgroupname")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun updateCustomGroupName(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "updatecustomgroupname",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String,
        @Field("docNo") docNo: Int,
        @Field("docName") docName: String
    ): Response<UpdateCustomGroupNameCompleteWithError>

    /**
     * 服務7. 修改自選股群組順序
     *
     * @param orderMap 自選股群組順序，格式{docNo:Order,docNo:Order}，範例：{30444:3,1539393:4}}
     */
    @RecordApi(cmoneyAction = "updatecustomgrouporder")
    @FormUrlEncoded
    @POST("MobileService/ashx/CustomerGroup/CustomGroup.ashx")
    suspend fun updateCustomGroupOrder(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "updatecustomgrouporder",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int,
        @Field("docType") docType: String,
        @Field("ordermap") orderMap: String
    ): Response<UpdateCustomGroupOrderCompleteWithError>

    /**
     * 搜尋關鍵字找股票
     */
    @RecordApi
    @POST("CustomGroupService/api/SearchStocks")
    suspend fun searchStocks(
      @Header("Authorization") authorization: String,
      @Body requestBody: SearchStocksRequestBody
    ): Response<SearchStocksResponseBody>

}