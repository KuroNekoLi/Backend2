package com.cmoney.backend2.billing.service.api.getdevelpoerpayload

import com.google.gson.annotations.SerializedName

/**
 * 取得購買辨別標記的要求ResponseBody
 *
 * @property id 購買標記的Id
 */
data class GetDeveloperPayloadResponseBody(
    @SerializedName("id")
    val id: Long?
)