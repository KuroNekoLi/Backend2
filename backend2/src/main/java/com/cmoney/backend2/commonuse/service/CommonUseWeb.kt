package com.cmoney.backend2.commonuse.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.commonuse.service.api.historyevent.HistoryEvents
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreference
import com.cmoney.backend2.commonuse.service.api.investmentpreference.InvestmentPreferenceType

interface CommonUseWeb {

    val manager: GlobalBackend2Manager

    /**
     * get remoteConfigLabel from [domain], return empty string if response is null
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun getRemoteConfigLabel(
        domain: String = manager.getCommonUseSettingAdapter().getDomain(),
        url: String = "${domain}commonuse/graphql"
    ): Result<String>

    /**
     * 更新用戶選擇的投資屬性
     *
     * @param investmentPreferenceType 用戶選擇的投資屬性
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 用戶選擇的投資屬性
     */
    suspend fun updateInvestmentPreference(
        investmentPreferenceType: InvestmentPreferenceType,
        domain: String = manager.getCommonUseSettingAdapter().getDomain(),
        url: String = "${domain}commonuse/graphql"
    ): Result<InvestmentPreferenceType>

    /**
     * 取得用戶選擇的投資屬性
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 用戶選擇的投資屬性清單
     */
    suspend fun getInvestmentPreferences(
        domain: String = manager.getCommonUseSettingAdapter().getDomain(),
        url: String = "${domain}commonuse/graphql"
    ): Result<List<InvestmentPreference>>

    /**
     * 取得商品的歷史推播事件紀錄
     *
     * @param commodityIds 欲取的商品清單
     * @param endCursor 取得下筆分頁的 key, 第一筆預設為 null
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 歷史推播事件清單
     */
    suspend fun getCommodityHistoryEvent(
        commodityIds: List<String>,
        endCursor: String? = null,
        domain: String = manager.getCommonUseSettingAdapter().getDomain(),
        url: String = "${domain}commonuse/graphql"
    ): Result<HistoryEvents>
}
