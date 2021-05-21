package com.cmoney.backend2.billing.service.api.getdevelpoerpayload

import com.google.gson.annotations.SerializedName

/**
 * 取得購買辨別標記的RequestBody
 *
 * @property appId App的Id
 * @property guid 會員的ID
 */
data class GetDeveloperPayloadRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)