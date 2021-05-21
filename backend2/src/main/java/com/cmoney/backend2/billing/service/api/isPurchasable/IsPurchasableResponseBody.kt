package com.cmoney.backend2.billing.service.api.isPurchasable

import com.google.gson.annotations.SerializedName

/**
 * 是否開放購買的ResponseBody
 *
 * @param isPurchasable 是否開放購買
 */
data class IsPurchasableResponseBody(
    @SerializedName("isPurchasable")
    val isPurchasable: Boolean?
)