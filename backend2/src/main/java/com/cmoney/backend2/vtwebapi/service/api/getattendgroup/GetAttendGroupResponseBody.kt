package com.cmoney.backend2.vtwebapi.service.api.getattendgroup


import com.google.gson.annotations.SerializedName

/**
 * @property account 會員在此競賽的帳戶
 * @property accountType 帳戶交易類型，可複選
 *                       沒有指定：0
 *                       現金交易：1
 *                       信用交易：2
 *                       權證：4
 *                       證劵：5
 *                       股票：7
 *                       期貨：8
 *                       選擇權：16
 *                       期權：24
 * @property arenaAdInfoList 競技場綁定的廣告清單
 * @property contactInfo 聯絡方式
 * @property defaultFunds 初始金額
 * @property description 競賽短描述
 * @property effectiveFunctions 卡片功能
 *                              不代表任何功能 : 0
 *                              使用額外帳戶 : 1
 *                              可看到委託單 : 2
 *                              可以看到交易紀錄 : 3
 *                              可以看到庫存 : 4
 *                              可以看到損益試算 : 5
 *                              飛鴿傳書 : 6
 *                              開啟競技場 (不可要求參賽者提供聯絡方式) : 7
 *                              開啟競技場 (可要求參賽者提供聯絡方式) : 8
 *                              新增證劵擴充金額 : 9
 *                              新增期權擴充金額 : 10
 * @property endTime 競賽結束時間
 * @property fee 手續稅率
 * @property feeDiscountRatio 手續費折扣
 * @property foFeeDiscountRatioObjs 期權商品折扣率清單
 * @property groupChannelId 競賽社團Id
 * @property groupId 賽程ID
 * @property instanceSn 綁定的道具卡卡號
 * @property isClose 是否已關閉賽程
 * @property isGroupManager 是否為競賽管理者
 * @property isPublic 是否公開競賽
 * @property isRelease 是否已發布
 * @property joinMethod 競賽加入方式
 *                      自由參加 = 1
 *                      需審核 = 2
 * @property link 競賽超連結
 * @property logoImg 競技Logo圖
 * @property managerPk 管理者Pk
 * @property marginInterest 融資利息稅率
 * @property maxMemberCount 人數上限
 * @property memberFunds 會員競技總資產
 * @property memberRank 會員競技排名
 * @property memberRatio 會員競技總報酬
 * @property memberRegStatus 會員在競賽的狀態
 *                           未報名 : -1
 *                           審核中 : 0
 *                           拒絕 : 1
 *                           允許 : 2
 * @property name 競賽名稱
 * @property otcMargin 融資上櫃成數
 * @property positionLimit 部位限制
 * @property priorityOrder 優先權排序
 * @property registrationEndDate 報名結束時間
 * @property registrationStartDate 報名開始時間
 * @property remainMemberCount 報名餘額
 * @property shortSellingEntrust 融券成數
 * @property shortSellingFee 借券費率
 * @property shortSellingInterest 融券利息稅率
 * @property signboardImg 競技宣傳圖
 * @property startTime 競賽起始時間
 * @property tradingTax 交易稅率
 * @property tseMargin 融資上市成數
 * @property warrantMargin 權證融資成數
 * @property warrantTradingTax 權證交易稅率
 */
data class GetAttendGroupResponseBody(
    @SerializedName("account")
    val account: Long?,
    @SerializedName("accountType")
    val accountType: Int?,
    @SerializedName("arenaAdInfoList")
    val arenaAdInfoList: List<ArenaAdInfo>?,
    @SerializedName("contactInfo")
    val contactInfo: String?,
    @SerializedName("defaultFunds")
    val defaultFunds: Double?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("effectiveFunctions")
    val effectiveFunctions: Int?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("fee")
    val fee: Double?,
    @SerializedName("feeDiscountRatio")
    val feeDiscountRatio: Double?,
    @SerializedName("foFeeDiscountRatioObjs")
    val foFeeDiscountRatioObjs: List<FoFeeDiscountRatioObj>?,
    @SerializedName("groupChannelId")
    val groupChannelId: Long?,
    @SerializedName("groupId")
    val groupId: Long?,
    @SerializedName("instanceSn")
    val instanceSn: Long?,
    @SerializedName("isClose")
    val isClose: Boolean?,
    @SerializedName("isGroupManager")
    val isGroupManager: Boolean?,
    @SerializedName("isPublic")
    val isPublic: Boolean?,
    @SerializedName("isRelease")
    val isRelease: Boolean?,
    @SerializedName("joinMethod")
    val joinMethod: Int?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("logoImg")
    val logoImg: String?,
    @SerializedName("managerPk")
    val managerPk: Long?,
    @SerializedName("marginInterest")
    val marginInterest: Double?,
    @SerializedName("maxMemberCount")
    val maxMemberCount: Int?,
    @SerializedName("memberFunds")
    val memberFunds: Double?,
    @SerializedName("memberRank")
    val memberRank: Int?,
    @SerializedName("memberRatio")
    val memberRatio: Double?,
    @SerializedName("memberRegStatus")
    val memberRegStatus: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("otcMargin")
    val otcMargin: Double?,
    @SerializedName("positionLimit")
    val positionLimit: List<PositionLimit>?,
    @SerializedName("priorityOrder")
    val priorityOrder: Int?,
    @SerializedName("registrationEndDate")
    val registrationEndDate: String?,
    @SerializedName("registrationStartDate")
    val registrationStartDate: String?,
    @SerializedName("remainMemberCount")
    val remainMemberCount: Int?,
    @SerializedName("shortSellingEntrust")
    val shortSellingEntrust: Double?,
    @SerializedName("shortSellingFee")
    val shortSellingFee: Double?,
    @SerializedName("shortSellingInterest")
    val shortSellingInterest: Double?,
    @SerializedName("signboardImg")
    val signboardImg: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("tradingTax")
    val tradingTax: Double?,
    @SerializedName("tseMargin")
    val tseMargin: Double?,
    @SerializedName("warrantMargin")
    val warrantMargin: Double?,
    @SerializedName("warrantTradingTax")
    val warrantTradingTax: Double?
)