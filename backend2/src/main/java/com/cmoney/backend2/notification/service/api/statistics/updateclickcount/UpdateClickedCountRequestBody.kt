package com.cmoney.backend2.notification.service.api.statistics.updateclickcount

import com.google.gson.annotations.SerializedName

/**
 * 增加點擊數要求物件
 *
 * @property sn 序號
 * @property platform ios為1 android為2 華為5
 * @property deviceToken 推播Token
 * @property appId App編號
 * @property title 標題
 * @property content 內容
 * @property analyticsLabels 用於統計與分析的labels
 * @property createTime 推播建立時間
 */
data class UpdateClickedCountRequestBody(
    @SerializedName("Sn")
    val sn: Long,
    @SerializedName("Platform")
    val platform: Int,
    @SerializedName("DeviceToken")
    val deviceToken: String,
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("AnalyticsLabels")
    val analyticsLabels: List<String>? = null,
    @SerializedName("CreateTime")
    val createTime: Long = 0
)