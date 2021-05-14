package com.cmoney.backend2.base.model.log

import com.google.gson.annotations.SerializedName

/**
 * cmoneyapi-trace-context的紀錄，每隻都需要使用。
 *
 * @property appId App的Id
 * @property platform 平台
 * @property appVersion App版本
 * @property manufacturer 製造商
 * @property model 手機型號
 * @property osVersion 系統版本
 */
data class ApiLog(
    @SerializedName("appId")
    val appId : Int,
    @SerializedName("platform")
    val platform: Int,
    @SerializedName("appVersion")
    val appVersion: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("osVersion")
    val osVersion: String
)