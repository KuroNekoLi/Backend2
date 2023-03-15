package com.cmoney.backend2.virtualtrading2.service.api.data.account.response

import com.google.gson.annotations.SerializedName

/**
 * 帳號資訊
 *
 * @property accountId 帳戶編號
 * @property name 名稱
 * @property groupId 競技場編號
 * @property memberId 會員編號
 * @property defaultFunds 預設帳戶資金
 * @property funds 帳戶資金
 * @property isNeedFee 需要收取手續費
 * @property isNeedTax 需要收取交易稅
 * @property canWatch 是否可查看
 * @property isDefault 是否為預設帳戶
 * @property isDelete 是否已刪除
 * @property accountType 現金交易: 1 / 信用交易: 2 / 權證: 4 / 證券: 5 / 期貨: 8 / 選擇權: 16 / 期權: 24
 * @property createTime 創建時間
 * @property updateTime 更新時間
 * @property viewTime 上次查看時間
 * @property accountPayType 免費帳戶: 0 / 道具卡租用: 1 / 道具卡租用已到期: 2 / 競技帳戶: 3 / 競技帳戶凍結: 4
 * @property maxReadSn 未讀數
 * @property isEmail 是否要接收Email
 * @property averageTradingCountInMonth 月平均交易數
 * @property totalPunishment 帳戶懲罰
 * @property tradedWarrantTime 最初交易權證時間
 * @property extendFunds 擴充現金
 * @property stockIncomeLoss 股票損益
 * @property warrantIncomeLoss 權證損益
 * @property futureIncomeLoss 期貨損益
 * @property optionIncomeLoss 選擇權損益
 * @property borrowFunds 帳戶融資金額
 * @property borrowLimit 帳戶融資借貸上限
 *
 */
data class AccountInfo(
    @SerializedName("account")
    val accountId: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("groupId")
    val groupId: Long?,
    @SerializedName("memberId")
    val memberId: Long?,
    @SerializedName("defaultFunds")
    val defaultFunds: String?,
    @SerializedName("funds")
    val funds: String?,
    @SerializedName("needFee")
    val isNeedFee: Boolean?,
    @SerializedName("needTax")
    val isNeedTax: Boolean?,
    @SerializedName("canWatch")
    val canWatch: Boolean?,
    @SerializedName("isDefault")
    val isDefault: Boolean?,
    @SerializedName("isDelete")
    val isDelete: Boolean?,
    @SerializedName("accountType")
    val accountType: Int?,
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("updateTime")
    val updateTime: Long?,
    @SerializedName("viewTime")
    val viewTime: Long?,
    @SerializedName("accountPayType")
    val accountPayType: Int?,
    @SerializedName("maxReadSn")
    val maxReadSn: Long?,
    @SerializedName("isEmail")
    val isEmail: Boolean?,
    @SerializedName("avgMonthOrderCount")
    val averageTradingCountInMonth: String?,
    @SerializedName("totalPunishment")
    val totalPunishment: Double?,
    @SerializedName("tradedWarrantDate")
    val tradedWarrantTime: Long?,
    @SerializedName("extendFunds")
    val extendFunds: String?,
    @SerializedName("stockIncomeLoss")
    val stockIncomeLoss: String?,
    @SerializedName("warrantIncomeLoss")
    val warrantIncomeLoss: String?,
    @SerializedName("tmxIncomeLoss")
    val futureIncomeLoss: String?,
    @SerializedName("optIncomeLoss")
    val optionIncomeLoss: String?,
    @SerializedName("borrowFunds")
    val borrowFunds: String?,
    @SerializedName("borrowLimit")
    val borrowLimit: String?
)