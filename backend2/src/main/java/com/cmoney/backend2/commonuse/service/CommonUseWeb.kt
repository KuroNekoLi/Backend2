package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.commonuse.service.api.historyevent.HistoryEvents
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreference
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType

interface CommonUseWeb {

    val baseHost: String

    /**
     * get remoteConfigLabel from [host], return empty string if response is null
     */
    suspend fun getRemoteConfigLabel(host: String = baseHost): Result<String>

    /**
     * 更新用戶選擇的投資屬性
     *
     * @param investmentPreferenceType 用戶選擇的投資屬性
     * @return 用戶選擇的投資屬性
     */
    suspend fun updateInvestmentPreference(
        host: String = baseHost,
        investmentPreferenceType: InvestmentPreferenceType
    ): Result<InvestmentPreferenceType>

    /**
     * 取得用戶選擇的投資屬性
     *
     * @return 用戶選擇的投資屬性清單
     */
    suspend fun getInvestmentPreferences(host: String = baseHost): Result<List<InvestmentPreference>>

    /**
     * 取得商品的歷史推播事件紀錄
     *
     * @param commodityIds 欲取的商品清單
     * @param endCursor 取得下筆分頁的 key, 第一筆預設為 null
     * @return 歷史推播事件清單
     */
    suspend fun getCommodityHistoryEvent(
        host: String = baseHost,
        commodityIds: List<String>,
        endCursor: String? = null
    ): Result<HistoryEvents>
}
