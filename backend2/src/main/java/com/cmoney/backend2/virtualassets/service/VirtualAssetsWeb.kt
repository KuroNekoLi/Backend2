package com.cmoney.backend2.virtualassets.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist.GetExchangeProductListResponseBody
import com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime.GetGroupLastExchangeTimeResponseBody

interface VirtualAssetsWeb {
    @Deprecated("ApiParam no longer required.")
    suspend fun getExchangeProductList(
        apiParam: MemberApiParam
    ): Result<GetExchangeProductListResponseBody>

    /**
     * 取得可兌換商品清單
     */
    suspend fun getExchangeProductList(): Result<GetExchangeProductListResponseBody>

    @Deprecated("ApiParam no longer required.")
    suspend fun getGroupLastExchangeTime(
        exchangeIds: List<Long>,
        apiParam: MemberApiParam
    ): Result<GetGroupLastExchangeTimeResponseBody>

    /**
     * 批次取得會員最後一次兌換指定商品的時間
     *
     * @param exchangeIds 批次兌換編號
     */
    suspend fun getGroupLastExchangeTime(
        exchangeIds: List<Long>
    ): Result<GetGroupLastExchangeTimeResponseBody>

    @Deprecated("ApiParam no longer required.")
    suspend fun exchange(
        exchangeId: Long,
        apiParam: MemberApiParam
    ): Result<Unit>

    /**
     * 會員兌換指定商品
     *
     * @param exchangeId 兌換編號
     */
    suspend fun exchange(exchangeId: Long): Result<Unit>
}