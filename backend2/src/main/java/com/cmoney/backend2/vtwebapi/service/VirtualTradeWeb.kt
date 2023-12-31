package com.cmoney.backend2.vtwebapi.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.vtwebapi.service.api.createaccount.AccountType
import com.cmoney.backend2.vtwebapi.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getattendgroup.GetAttendGroupResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.GetCardInstanceSnsResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.UsageType
import com.cmoney.backend2.vtwebapi.service.api.getstockinventorylist.GetStockInventoryListResponseBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardResponseBody

interface VirtualTradeWeb {

    /**
     * Backend2管理者
     */
    val globalBackend2Manager: GlobalBackend2Manager

    /**
     * 取得會員所有帳戶資訊
     *
     * @param domain 呼叫的server網域
     * @param destMemberPk 目標會員 PK(預設null->取自己)
     * @param skipCount 跳過數量(預設null)
     * @param fetchSize 取得量(預設null->取全部)
     * @param needGroupAccount 是否包含競賽帳號(預設null)
     * @param needExtendInfo 是否需要帳號額外資訊(含問帳戶數)(預設null)
     */
    suspend fun getAccount(
        domain: String = globalBackend2Manager.getVirtualTradeSettingAdapter().getDomain(),
        url: String = "${domain}vt.webapi/Account",
        destMemberPk: Long? = null,
        skipCount: Int? = null,
        fetchSize: Int? = null,
        needGroupAccount: Boolean? = null,
        needExtendInfo: Boolean? = null
    ): Result<List<GetAccountResponseBody>>

    /**
     * 創建一個投資帳戶
     *
     * @param domain 呼叫的server網域
     * @param type 創建帳號類型
     * @param isn 使用的道具卡卡號，沒有道具卡填0(免費創建)
     */
    suspend fun createAccount(
        domain: String = globalBackend2Manager.getVirtualTradeSettingAdapter().getDomain(),
        url: String = "${domain}vt.webapi/Account",
        type: AccountType,
        isn: Long
    ): Result<GetAccountResponseBody>

    /**
     * 依照卡片類型回傳會員持有的卡片序號
     *
     * @param domain 呼叫的server網域
     * @param productSn 卡片類型編號
     * @param productUsage 道具使用狀況
     */
    suspend fun getCardInstanceSns(
        domain: String = globalBackend2Manager.getVirtualTradeSettingAdapter().getDomain(),
        url: String = "${domain}vt.webapi/ByProductSn",
        productSn: Long,
        productUsage: UsageType
    ): Result<GetCardInstanceSnsResponseBody>

    /**
     * 購買使用者道具卡
     *
     * @param domain 呼叫的server網域
     * @param giftFromMember 這張卡是誰送的
     * @param ownerMemberPk 擁有者PK
     * @param productSn 卡片類型編號
     */
    suspend fun purchaseProductCard(
        domain: String = globalBackend2Manager.getVirtualTradeSettingAdapter().getDomain(),
        url: String = "${domain}vt.webapi/ProductCard/PurchaseProductCard",
        giftFromMember: Int,
        ownerMemberPk: Int,
        productSn: Long
    ): Result<PurchaseProductCardResponseBody>

    /**
     * 取得我參加的競技場
     *
     * @param domain 呼叫的server網域
     * @param fetchIndex 取得起始索引(index:0)(預設null)
     * @param fetchSize 取得量(預設null->取全部)
     */
    suspend fun getAttendGroup(
        domain: String = globalBackend2Manager.getVirtualTradeSettingAdapter().getDomain(),
        url: String = "${domain}vt.webapi/Group/Attend",
        fetchIndex: Int? = null,
        fetchSize: Int? = null
    ): Result<List<GetAttendGroupResponseBody>>

    /**
     * 取得指定帳號股票庫存列表
     *
     * @param domain 呼叫的server網域
     * @param account 帳號ID
     */
    suspend fun getStockInventoryList(
        account: Long,
        domain: String = globalBackend2Manager.getVirtualTradeSettingAdapter().getDomain(),
        url: String = "${domain}vt.webapi/Inventory/SecInventoryListByAccount/${account}",
    ): Result<List<GetStockInventoryListResponseBody>>
}