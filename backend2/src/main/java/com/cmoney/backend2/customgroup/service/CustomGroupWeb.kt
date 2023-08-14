package com.cmoney.backend2.customgroup.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.customgroup.service.api.addcustomgroup.NewCustomGroup
import com.cmoney.backend2.customgroup.service.api.common.CustomGroupType
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder.SingleGroupWithOrder
import com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist.CustomGroupWithOrderAndList
import com.cmoney.backend2.customgroup.service.api.searchstocks.SearchStocksResponseBody
import com.cmoney.backend2.customgroup.service.api.updatecustomgrouporder.UpdateCustomGroupOrderComplete

interface CustomGroupWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得自訂群組清單不含內容
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getCustomGroupListIncludeOrder(
        groupType: CustomGroupType = CustomGroupType.Stock,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<List<SingleGroupWithOrder>>

    /**
     * 取得指定群組內的股票清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getCustomGroupContent(
        docNo: Int,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<List<String>>

    /**
     * 取得使用者自訂群組含順序清單及股票清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getCustomGroupListIncludeOrderAndContent(
        groupType: CustomGroupType = CustomGroupType.Stock,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<CustomGroupWithOrderAndList>

    /**
     * 更新自選股清單的內容和名字
     *
     * @param groupType 自選股類型
     * @param docNo 自選股編號
     * @param docName 自選股名稱
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 是否成功
     */
    suspend fun updateCustomGroupNameAndContent(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        docName: String,
        list: List<String>,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<Boolean>

    /**
     * 新增自選股清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun addCustomGroup(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docName: String,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<NewCustomGroup>

    /**
     * 刪除自選股清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 是否成功
     */
    suspend fun deleteCustomGroup(
        docNo: Int,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<Boolean>

    /**
     * 更改自選股清單名稱
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun renameCustomGroup(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNo: Int,
        newDocName: String,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<Boolean>

    /**
     * 更新自選股清單排序
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun updateCustomGroupOrder(
        groupType: CustomGroupType = CustomGroupType.Stock,
        docNoList: List<Int>,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/CustomerGroup/CustomGroup.ashx"
    ): Result<UpdateCustomGroupOrderComplete>

    /**
     * 搜尋關鍵字找股票
     *
     * @param keyword 要求搜尋相符的股票代號 或 股票名稱
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun searchStocks(
        keyword: String,
        domain: String = manager.getCustomGroupSettingAdapter().getDomain(),
        url: String = "${domain}CustomGroupService/api/SearchStocks"
    ): Result<SearchStocksResponseBody>
}