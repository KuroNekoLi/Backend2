package com.cmoney.backend2.forumocean.service.api.notify.get

import com.google.gson.annotations.SerializedName


/**
 * 通知資訊的所有欄位的 ResponseBody
 *
 *
 * @property mapping 特定通知類型額外提供的資料
 * @property body 通知內文
 * @property mergeKey 合併主鍵
 * @property notifyType 通知類型
 * @property read 是否已看過
 * @property deepLink 轉導資訊url
 * @property title 通知標題
 * @property updateTime 更新時間
 * @property new 是否為新
 *
 */
data class GetNotifyResponseBody(
    @SerializedName("mapping")
    val mapping : List<NotifyResponseBodyMappingDetail>?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("mergeKey")
    val mergeKey: String?,
    @SerializedName("type")
    val notifyType: String?,
    @SerializedName("read")
    val read: Boolean?,
    @SerializedName("deepLink")
    val deepLink: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updateTime")
    val updateTime: Long?,
    @SerializedName("new")
    val new : Boolean?
)

/**
 * @property key 供辨識的額外提供資訊 目前有memberIds commodityKeys groupId 三種
 * @property value key對應的值
 */
data class NotifyResponseBodyMappingDetail(
    @SerializedName("key")
    val key : String?,
    @SerializedName("value")
    val value : String?
)