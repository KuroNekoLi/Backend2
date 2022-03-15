package com.cmoney.backend2.vtwebapi.service.api.getattendgroup


import com.google.gson.annotations.SerializedName

/**
 * 部位限制
 *
 * @property isActive 是否啟用
 * @property positionLimitType 部位限制類型
 *                             最低持股率：1
 *                             單一個股投資上限：2
 *                             每日當沖平倉：3
 * @property startUpTime 條件啟動時間
 * @property value 內容
 */
data class PositionLimit(
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("positionLimitType")
    val positionLimitType: Int?,
    @SerializedName("startUpTime")
    val startUpTime: Int?,
    @SerializedName("value")
    val value: String?
)