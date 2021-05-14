package com.cmoney.backend2.base.model.request

import com.google.gson.annotations.SerializedName

/**
 * 打Api的基本參數
 */
@Deprecated("使用Setting介面來實作自己的參數")
sealed class ApiParam

/**
 * API會員參數
 */
@Deprecated("使用Setting介面來實作自己的參數")
data class MemberApiParam(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("AuthToken")
    val authToken: String
): ApiParam()

/**
 * API訪客參數
 */
@Deprecated("使用Setting介面來實作自己的參數")
data class GuestApiParam(
    @SerializedName("AppId")
    val appId: Int
): ApiParam()