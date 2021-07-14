package com.cmoney.backend2.billing.service.api.isPurchasable

import com.google.gson.annotations.SerializedName

/**
 * 身份認證相關資料
 *
 * @property appId App編號
 * @property guid 會員guid
 */
data class IsPurchasableRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)