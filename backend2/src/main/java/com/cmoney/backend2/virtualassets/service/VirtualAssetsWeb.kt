package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody

interface VirtualAssetsWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得可兌換商品清單
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getExchangeProductList(
        domain: String = manager.getVirtualAssetsSettingAdapter().getDomain(),
        url: String = "${domain}VirtualAssets/BonusExchange/Product/${manager.getAppId()}"
    ): Result<GetExchangeProductListResponseBody>

    /**
     * 批次取得會員最後一次兌換指定商品的時間
     *
     * @param exchangeIds 批次兌換編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getGroupLastExchangeTime(
        exchangeIds: List<Long>,
        domain: String = manager.getVirtualAssetsSettingAdapter().getDomain(),
        url: String = "${domain}VirtualAssets/BonusExchange/Product/MoreLastExchangeTime"
    ): Result<GetGroupLastExchangeTimeResponseBody>

    /**
     * 會員兌換指定商品
     *
     * @param exchangeId 兌換編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun exchange(
        exchangeId: Long,
        domain: String = manager.getVirtualAssetsSettingAdapter().getDomain(),
        url: String = "${domain}VirtualAssets/BonusExchange/Product/Exchange/${exchangeId}"
    ): Result<Unit>
}