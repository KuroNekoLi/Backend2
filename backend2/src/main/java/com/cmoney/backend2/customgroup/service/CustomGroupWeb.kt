package com.cmoney.backend2.customgroup.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.customgroup.service.api.addcustomgroup.NewCustomGroup
import com.cmoney.backend2.customgroup.service.api.common.CustomGroupType
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder.SingleGroupWithOrder
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist.CustomGroupWithOrderAndList
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksResponseBody
import com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder.UpdateCustomGroupOrderComplete

interface CustomGroupWeb {

    /**
     * 取得自訂群組清單不含內容
     */
    suspend fun getCustomGroupListIncludeOrder(
        groupType: CustomGroupType = CustomGroupType.Stock
    ): Result<List<SingleGroupWithOrder>>

    /**
     * 取得指定群組內的股票清單
     */
    suspend fun getCustomGroupContent(
        docNo: Int
    ): Result<List<String>>

    /**
     * 取得使用者自訂群組含順序清單及股票清單
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getCustomGroupWithOrderAndList(
        apiParam: MemberApiParam,
        groupType: CustomGroupType = CustomGroupType.Stock
    ): Result<CustomGroupWithOrderAndList>

    /**
     * 取得使用者自訂群組含順序清單及股票清單
     */
    @Deprecated("使用 getCustomGroupListIncludeOrderAndContent()")
    suspend fun getCustomGroupWithOrderAndList(groupType: CustomGroupType = CustomGroupType.Stock): Result<CustomGroupWithOrderAndList>

    /**
     * 取得使用者自訂群組含順序清單及股票清單
     */
    suspend fun getCustomGroupListIncludeOrderAndContent(groupType: CustomGroupType = CustomGroupType.Stock): Result<CustomGroupWithOrderAndList>

    /**
     * 更新自選股清單的內容和名字
     *
     * @param docNo 自選股編號
     * @param docName 自選股名稱
     *
     * @return 是否成功
     */
    @Deprecated("ApiParam no longer required")
    suspend fun updateCustomList(
        apiParam: MemberApiParam,
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        docName: String,
        list: List<String>
    ): Result<Boolean>

    /**
     * 更新自選股清單的內容和名字
     *
     * @param docNo 自選股編號
     * @param docName 自選股名稱
     *
     * @return 是否成功
     */
    @Deprecated("使用updateCustomGroupNameAndContent")
    suspend fun updateCustomList(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        docName: String,
        list: List<String>
    ): Result<Boolean>


    /**
     * 更新自選股清單的內容和名字
     *
     * @param groupType 自選股類型
     * @param docNo 自選股編號
     * @param docName 自選股名稱
     *
     * @return 是否成功
     */
    suspend fun updateCustomGroupNameAndContent(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        docName: String,
        list: List<String>
    ): Result<Boolean>

    /**
     * 新增自選股清單
     */
    @Deprecated("ApiParam no longer required")
    suspend fun addCustomGroup(
        apiParam: MemberApiParam,
        groupType: CustomGroupType = CustomGroupType.Stock,
        docName: String
    ): Result<NewCustomGroup>

    /**
     * 新增自選股清單
     */
    suspend fun addCustomGroup(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docName: String
    ): Result<NewCustomGroup>

    /**
     * 刪除自選股清單
     *
     * @return 是否成功
     */
    @Deprecated("ApiParam no longer required")
    suspend fun deleteCustomGroup(
        apiParam: MemberApiParam,
        docNo: Int
    ): Result<Boolean>

    /**
     * 刪除自選股清單
     *
     * @return 是否成功
     */
    suspend fun deleteCustomGroup(
        docNo: Int
    ): Result<Boolean>

    /**
     * 更改自選股清單名稱
     */
    @Deprecated("ApiParam no longer required")
    suspend fun renameCustomGroup(
        apiParam: MemberApiParam,
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        newDocName: String
    ): Result<Boolean>

    /**
     * 更改自選股清單名稱
     */
    suspend fun renameCustomGroup(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        newDocName: String
    ): Result<Boolean>

    /**
     * 更新自選股清單排序
     */
    @Deprecated("ApiParam no longer required")
    suspend fun updateCustomGroupOrder(
        apiParam: MemberApiParam,
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNoList: List<Int>
    ): Result<UpdateCustomGroupOrderComplete>

    /**
     * 更新自選股清單排序
     */
    suspend fun updateCustomGroupOrder(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNoList: List<Int>
    ): Result<UpdateCustomGroupOrderComplete>

    /**
     * 搜尋關鍵字找股票
     *
     * @param keyword 要求搜尋相符的股票代號 或 股票名稱
     */
    suspend fun searchStocks(
        keyword: String
    ): Result<SearchStocksResponseBody>

}