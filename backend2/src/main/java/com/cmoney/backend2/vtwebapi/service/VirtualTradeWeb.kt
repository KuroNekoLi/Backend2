package com.cmoney.backend2.vtwebapi.service

import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.vtwebapi.service.api.getaccount.GetAccountResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getattendgroup.GetAttendGroupResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns.GetCardInstanceSnsResponseBody
import com.cmoney.backend2.vtwebapi.service.api.getstockinventorylist.GetStockInventoryListResponseBody
import com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard.PurchaseProductCardResponseBody

/**
 * @property setting 網路基本設定
 * @property
 */
interface VirtualTradeWeb {

    val setting: Setting

    /**
     * 取得會員所有帳戶資訊
     *
     * @param domain 呼叫的server網域
     * @param destMemberPk 目標會員 PK(不帶就是取自己)
     * @param skipCount 跳過數量
     * @param fetchSize 取得量(預設不帶取全部)
     * @param needGroupAccount 是否包含競賽帳號
     * @param needExtendInfo 是否需要帳號額外資訊(含問帳戶數)
     */
    suspend fun getAccount(
        domain: String = setting.domainUrl,
        destMemberPk: Long,
        skipCount: Int,
        fetchSize: Int,
        needGroupAccount: Boolean,
        needExtendInfo: Boolean
    ): Result<List<GetAccountResponseBody>>

    /**
     * 創建一個投資帳戶
     *
     * @param domain 呼叫的server網域
     * @param type 創建帳號類型 現股：1 期權：2
     * @param isn 使用的道具卡卡號，沒有道具卡填0(免費創建)
     */
    suspend fun createAccount(
        domain: String = setting.domainUrl,
        type: Int,
        isn: Long
    ): Result<Unit>

    /**
     * 依照卡片類型回傳會員持有的卡片序號
     *
     * @param domain 呼叫的server網域
     * @param productSn 卡片類型編號
     */
    suspend fun getCardInstanceSns(
        domain: String = setting.domainUrl,
        productSn: Long
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
        domain: String = setting.domainUrl,
        giftFromMember: Int,
        ownerMemberPk: Int,
        productSn: Long
    ): Result<PurchaseProductCardResponseBody>

    /**
     * 取得我參加的競技場
     *
     * @param domain 呼叫的server網域
     * @param fetchIndex 取得起始索引(index:0)
     * @param fetchSize 取得量(預設取全部)
     */
    suspend fun getAttendGroup(
        domain: String = setting.domainUrl,
        fetchIndex: Int,
        fetchSize: Int
    ): Result<List<GetAttendGroupResponseBody>>

    /**
     * 取得指定帳號股票庫存列表
     *
     * @param domain 呼叫的server網域
     * @param account 帳號ID
     */
    suspend fun getStockInventoryList(
        domain: String = setting.domainUrl,
        account: Long
    ): Result<List<GetStockInventoryListResponseBody>>
}