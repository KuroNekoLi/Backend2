package com.cmoney.backend2.notification.service.api.statistics.updatearrived

import com.google.gson.annotations.SerializedName

/**
 *
 * @property sn 序號
 * @property platform ios為1 android為2 華為5
 * @property deviceToken 推播Token
 * @property appId App編號
 * @property analyticsId 分析用Id
 * @property title 標題
 * @property content 內容
 */
data class UpdateArrivedRequestBody(
    @SerializedName("Sn")
    val sn: Long,
    @SerializedName("Platform")
    val platform: Int,
    @SerializedName("DeviceToken")
    val deviceToken: String,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("AnalyticsId")
    val analyticsId: Long,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Content")
    val content: String?
)