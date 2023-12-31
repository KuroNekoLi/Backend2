package com.cmoney.backend2.virtualtrading2.service.api.createaccount

import com.google.gson.annotations.SerializedName

/**
 * 建立帳號回應
 *
 * @property accountId 帳戶資訊
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
 * @property tradedWarrantDate 最初交易權證時間
 * @property extendFunds 擴充現金
 * @property stockIncomeLoss 股票損益
 * @property warrantIncomeLoss 權證損益
 * @property futureIncomeLoss 期貨損益
 * @property optionIncomeLoss 選擇權損益
 * @property borrowFunds 帳戶融資金額
 * @property borrowLimit 帳戶融資借貸上限
 *
 */
data class CreateAccountResponseBody(
    @SerializedName("Account")
    val accountId: Long?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("GroupId")
    val groupId: Long?,
    @SerializedName("MemberId")
    val memberId: Long?,
    @SerializedName("DefaultFunds")
    val defaultFunds: String?,
    @SerializedName("Funds")
    val funds: String?,
    @SerializedName("NeedFee")
    val isNeedFee: Boolean?,
    @SerializedName("NeedTax")
    val isNeedTax: Boolean?,
    @SerializedName("CanWatch")
    val canWatch: Boolean?,
    @SerializedName("IsDefault")
    val isDefault: Boolean?,
    @SerializedName("IsDelete")
    val isDelete: Boolean?,
    @SerializedName("AccountType")
    val accountType: Int?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("UpdateTime")
    val updateTime: Long?,
    @SerializedName("ViewTime")
    val viewTime: Long?,
    @SerializedName("AccountPayType")
    val accountPayType: Int?,
    @SerializedName("MaxReadSn")
    val maxReadSn: Long?,
    @SerializedName("IsEmail")
    val isEmail: Boolean?,
    @SerializedName("AvgMonthOrderCount")
    val averageTradingCountInMonth: String?,
    @SerializedName("TotalPunishment")
    val totalPunishment: Double?,
    @SerializedName("TradedWarrantDate")
    val tradedWarrantDate: Long?,
    @SerializedName("ExtendFunds")
    val extendFunds: String?,
    @SerializedName("StockIncomeLoss")
    val stockIncomeLoss: String?,
    @SerializedName("WarrantIncomeLoss")
    val warrantIncomeLoss: String?,
    @SerializedName("TmxIncomeLoss")
    val futureIncomeLoss: String?,
    @SerializedName("OptIncomeLoss")
    val optionIncomeLoss: String?,
    @SerializedName("BorrowFunds")
    val borrowFunds: String?,
    @SerializedName("BorrowLimit")
    val borrowLimit: String?
)