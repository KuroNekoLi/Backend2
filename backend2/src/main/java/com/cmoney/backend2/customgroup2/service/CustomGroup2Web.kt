package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.api.data.*

interface CustomGroup2Web {

    val manager: GlobalBackend2Manager

    /**
     * 根據關鍵字、回傳語系[Language]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param language 回傳語系
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchStocksV2(
        keyword: String,
        language: Language,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}CustomGroupService/api/searchstocksbyappid"
    ): Result<List<StockV2>>

    /**
     * 根據關鍵字、回傳語系[Language]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param languages 回傳語系列表
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchStocksV2(
        keyword: String,
        languages: List<Language>,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}CustomGroupService/api/searchstocksbyappid"
    ): Result<List<StockV2>>

    /**
     * 根據關鍵字、回傳語系[Language]、市場類別[MarketType]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param language 回傳語系
     * @param marketTypes 預期的市場類別集合
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchStocksByMarketTypesV2(
        keyword: String,
        language: Language,
        marketTypes: List<MarketTypeV2>,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}CustomGroupService/api/searchstocksbycommoditytype"
    ): Result<List<StockV2>>

    /**
     * 根據關鍵字、回傳語系[Language]、市場類別[MarketType]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param languages 回傳語系
     * @param marketTypes 預期的市場類別集合
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun searchStocksByMarketTypesV2(
        keyword: String,
        languages: List<Language>,
        marketTypes: List<MarketTypeV2>,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}CustomGroupService/api/searchstocksbycommoditytype"
    ): Result<List<StockV2>>

    /**
     * 取得自選股群組，預設群組類型為StockGroup
     *
     * @param marketType 市場類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 自選股群組集合
     */
    suspend fun getCustomGroup(
        marketType: DocMarketType,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api"
    ): Result<List<CustomGroup>>

    /**
     * 取得自選股群組(指定ID)
     *
     * @param id 指定的群組ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 自選股群組
     */
    suspend fun getCustomGroup(
        id: String,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api/${id}"
    ): Result<CustomGroup>

    /**
     * 更新自選股群組(指定ID)
     *
     * @param newGroup 新的自選股群組(ID須保持一致)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 是否成功
     */
    suspend fun updateCustomGroup(
        newGroup: CustomGroup,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api/${newGroup.id}"
    ): Result<Unit>

    /**
     * 創建自選股群組
     *
     * @param displayName 群組顯示名稱
     * @param marketType 市場類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 新群組
     */
    suspend fun createCustomGroup(
        displayName: String,
        marketType: DocMarketType,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api"
    ): Result<CustomGroup>

    /**
     * 刪除自選股模組(指定ID)
     *
     * @param id 指定的群組ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 是否成功
     */
    suspend fun deleteCustomGroup(
        id: String,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api/${id}"
    ): Result<Unit>

    /**
     * 取得使用者設定文件
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 使用者設定
     */
    suspend fun getUserConfiguration(
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api/_configuration"
    ): Result<UserConfiguration>

    /**
     * 更新使用者設定文件
     *
     * @param customGroups 自選股群組
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 是否成功
     */
    suspend fun updateConfiguration(
        customGroups: List<CustomGroup>,
        domain: String = manager.getCustomGroup2SettingAdapter().getDomain(),
        url: String = "${domain}custom-group/api/_configuration"
    ): Result<Unit>
}