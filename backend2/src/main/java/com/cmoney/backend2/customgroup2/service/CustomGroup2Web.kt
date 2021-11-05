package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.base.model.request.Language
import com.cmoney.backend2.customgroup2.service.api.data.*

interface CustomGroup2Web {

    /**
     * 根據關鍵字、回傳語系[Language]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param language 回傳語系
     * @return 搜尋到的股市標的
     */
    suspend fun searchStocks(keyword: String, language: Language): Result<List<Stock>> = searchStocks(keyword, listOf(language))

    /**
     * 根據關鍵字、回傳語系[Language]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param languages 回傳語系
     * @return 搜尋到的股市標的
     */
    suspend fun searchStocks(keyword: String, languages: List<Language>): Result<List<Stock>>

    /**
     * 根據關鍵字、回傳語系[Language]、市場類別[MarketType]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param language 回傳語系
     * @param marketTypes 預期的市場類別集合
     * @return 搜尋到的股市標的
     */
    suspend fun searchStocksByMarketTypes(
        keyword: String,
        language: Language,
        marketTypes: List<MarketType>
    ): Result<List<Stock>> = searchStocksByMarketTypes(keyword, listOf(language), marketTypes)

    /**
     * 根據關鍵字、回傳語系[Language]、市場類別[MarketType]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param languages 回傳語系
     * @param marketTypes 預期的市場類別集合
     * @return 搜尋到的股市標的
     */
    suspend fun searchStocksByMarketTypes(
        keyword: String,
        languages: List<Language>,
        marketTypes: List<MarketType>
    ): Result<List<Stock>>

    /**
     * 取得自選股群組，預設群組類型為StockGroup
     *
     * @param marketType 市場類型
     * @return 自選股群組集合
     */
    suspend fun getCustomGroup(
        marketType: DocMarketType
    ): Result<List<CustomGroup>>

    /**
     * 取得自選股群組(指定ID)
     *
     * @param id 指定的群組ID
     * @return 自選股群組
     */
    suspend fun getCustomGroup(
        id: String
    ): Result<CustomGroup>

    /**
     * 更新自選股群組(指定ID)
     *
     * @param newGroup 新的自選股群組(ID須保持一致)
     * @return 是否成功
     */
    suspend fun updateCustomGroup(
        newGroup: CustomGroup
    ): Result<Unit>

    /**
     * 創建自選股群組
     *
     * @param displayName 群組顯示名稱
     * @return 新群組
     */
    suspend fun createCustomGroup(
        displayName: String,
        marketType: DocMarketType
    ): Result<CustomGroup>

    /**
     * 刪除自選股模組(指定ID)
     *
     * @param id 指定的群組ID
     * @return 是否成功
     */
    suspend fun deleteCustomGroup(
        id: String
    ): Result<Unit>

    /**
     * 取得使用者設定文件
     *
     * @return 使用者設定
     */
    suspend fun getUserConfiguration(): Result<UserConfiguration>

    /**
     * 更新使用者設定文件
     *
     * @param customGroups 自選股群組
     * @return 是否成功
     */
    suspend fun updateConfiguration(customGroups: List<CustomGroup>): Result<Unit>
}