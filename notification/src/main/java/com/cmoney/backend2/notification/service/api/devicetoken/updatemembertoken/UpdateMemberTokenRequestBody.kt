package com.cmoney.backend2.notification.service.api.devicetoken.updatemembertoken

import com.google.gson.annotations.SerializedName

/**
 *
 * @property appId AppId編號
 * @property platform ios為1 android為2 華為5
 * @property deviceToken 推播Token
 * @property version 版本
 *
 */
data class UpdateMemberTokenRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Platform")
    val platform: Int,
    @SerializedName("DeviceToken")
    val deviceToken: String,
    @SerializedName("Version")
    val version: String
)