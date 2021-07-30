package com.cmoney.backend2.customgroup2.service

import com.cmoney.backend2.customgroup2.service.api.data.Language
import com.cmoney.backend2.customgroup2.service.api.data.MarketType
import com.cmoney.backend2.customgroup2.service.api.data.Stock

interface CustomGroup2Web {

    /**
     * 根據關鍵字、回傳語系[Language]搜尋股市標的
     *
     * @param keyword 關鍵字
     * @param language 回傳語系
     * @return 搜尋到的股市標的
     */
    suspend fun searchStocks(keyword: String, language: Language): Result<List<Stock>>

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
    ): Result<List<Stock>>
}