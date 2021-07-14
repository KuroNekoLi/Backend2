package com.cmoney.backend2.billing.service.api.getproductinfo

import com.google.gson.annotations.SerializedName

/**
 * 取得商品資訊的RequestBody
 *
 * @property appId App的Id
 * @property guid 會員的ID
 */
data class GetProductInfoRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)