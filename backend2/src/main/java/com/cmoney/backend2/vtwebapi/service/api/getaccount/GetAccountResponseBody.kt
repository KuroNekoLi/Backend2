package com.cmoney.backend2.vtwebapi.service.api.getaccount

import com.cmoney.backend2.vtwebapi.service.api.getattendgroup.ArenaAdInfo
import com.google.gson.annotations.SerializedName

/**
 * @property cardInstanceSn 卡片SN
 * @property payType 帳戶租用狀態
 *                   免費帳戶 = 0
 *                   道具卡租用 = 1
 *                   道具卡租用已到期 = 2
 *                   競技帳戶 = 3
 *                   競賽帳戶凍結 = 4
 * @property account 帳號
 * @property name 帳戶名稱
 * @property groupId 競賽Id
 * @property memberPK CMoney會員Id
 * @property defaultFunds 初始現金
 * @property extendFunds 擴充現金
 * @property funds 可動用現金
 * @property allPrePayment 未成交委託金額
 * @property inventoryValue 證券資產
 * @property foInventoryValue 部位權益(期權)
 * @property ratio 投資報酬率(%)
 * @property isTracked 是否已追蹤
 * @property beTrackedCount 帳戶被追蹤人數
 * @property accountType 帳戶交易類型，可複選
 *                       沒有指定：0
 *                       現金交易：1
 *                       信用交易 : 2
 *                       權證 : 4
 *                       證劵 : 5
 *                       股票 : 7
 *                       期貨 : 8
 *                       選擇權 : 16
 *                       期權 : 24
 * @property maintenanceRate 整戶維持率(%)(市櫃)
 * @property activeDate 帳戶啟用日期
 * @property needFee 是否啟用手續費機制
 * @property needTax 是否啟用證交稅機制
 * @property canWatch 是否開放觀看帳戶
 * @property isDefault 此帳戶是否為預設
 * @property isDelete 刪除
 * @property ratioRankType 報酬率排行變動類型(1-5)
 * @property viewTime 最後登入時間
 * @property avgMonthOrderCount 月均委託量(有成交的委託單)
 * @property isEmail 是否發送通知Email
 * @property punishment 懲罰
 * @property dividendMemoney 信用借款(市櫃)
 * @property tradedWarrantDate 最初交易權證時間
 * @property incomeLoss 帳戶總損益
 * @property totalFunds 帳戶總值
 * @property borrowLimit 帳戶融資借貸上限
 * @property borrowFunds 帳戶融資金額
 * @property arenaAdInfoList 帳號所屬競技場綁定的廣告清單
 */
data class GetAccountResponseBody(
    @SerializedName("cardInstaceSn")
    val cardInstanceSn: Long,
    @SerializedName("payType")
    val payType: Int,
    @SerializedName("account")
    val account: Long,
    @SerializedName("name")
    val name: String?,
    @SerializedName("groupId")
    val groupId: Long,
    @SerializedName("memberPK")
    val memberPK: Long,
    @SerializedName("defaultFunds")
    val defaultFunds: Double,
    @SerializedName("extendFunds")
    val extendFunds: Double,
    @SerializedName("funds")
    val funds: Double,
    @SerializedName("allPrePayment")
    val allPrePayment: Double,
    @SerializedName("inventoryValue")
    val inventoryValue: Double,
    @SerializedName("foInventoryValue")
    val foInventoryValue: Double,
    @SerializedName("ratio")
    val ratio: Double,
    @SerializedName("isTracked")
    val isTracked: Boolean,
    @SerializedName("beTrackedCount")
    val beTrackedCount: Int,
    @SerializedName("accountType")
    val accountType: Int,
    @SerializedName("maintenanceRate")
    val maintenanceRate: Double,
    @SerializedName("activeDate")
    val activeDate: String,
    @SerializedName("needFee")
    val needFee: Boolean,
    @SerializedName("needTax")
    val needTax: Boolean,
    @SerializedName("canWatch")
    val canWatch: Boolean,
    @SerializedName("isDefault")
    val isDefault: Boolean,
    @SerializedName("isDelete")
    val isDelete: Boolean,
    @SerializedName("ratioRankType")
    val ratioRankType: Int,
    @SerializedName("viewTime")
    val viewTime: String,
    @SerializedName("avgMonthOrderCount")
    val avgMonthOrderCount: Double,
    @SerializedName("isEmail")
    val isEmail: Boolean,
    @SerializedName("punishment")
    val punishment: Double,
    @SerializedName("dividendMemoney")
    val dividendMemoney: Double,
    @SerializedName("tradedWarrantDate")
    val tradedWarrantDate: Int,
    @SerializedName("incomeLoss")
    val incomeLoss: Double,
    @SerializedName("totalFunds")
    val totalFunds: Double,
    @SerializedName("borrowLimit")
    val borrowLimit: Double,
    @SerializedName("BorrowFunds")
    val borrowFunds: Double,
    @SerializedName("arenaAdInfoList")
    val arenaAdInfoList: List<ArenaAdInfo>?
)