package com.cmoney.backend2.forumocean.service.api.notify.get

import com.google.gson.annotations.SerializedName


/**
 * 通知資訊的所有欄位的 ResponseBody
 *
 *
 * @property memberIds 會員Id 的StringArray 只存在特定通知類型
 * @property commodityKeys 股票代號 的StringArray 只存在特定通知類型
 * @property groupId 社團Id 的 StringArray 只存在特定通知類型
 * @property body 通知內文
 * @property mergeKey 合併主鍵
 * @property notifyType 通知類型
 * @property read 是否已看過
 * @property redirectInfo 轉導資訊 jsonString 根據不同通知類型會有不同的轉導資訊
 * @property title 通知標題
 * @property updateTime 更新時間
 * @property new 是否為新
 *
 * @see RedirectInfo 轉導資訊類別
 * @see NotifyRedirectInfoParser 轉導資訊工具
 */
data class GetNotifyResponseBody(
    @SerializedName("memberIds")
    val memberIds : List<String>?,
    @SerializedName("commodityKeys")
    val commodityKeys : List<String>?,
    @SerializedName("groupId")
    val groupId : List<String>?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("mergeKey")
    val mergeKey: String?,
    @SerializedName("notifyType")
    val notifyType: String?,
    @SerializedName("read")
    val read: Boolean?,
    @SerializedName("redirectInfo")
    val redirectInfo: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updateTime")
    val updateTime: Long?,
    @SerializedName("new")
    val new : Any?
)