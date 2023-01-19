package com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate

import com.google.gson.annotations.SerializedName

/**
 * 建立委託單回應
 *
 * @property delegateId 委託單號
 *
 */
data class CreateDelegateResponseBody(
    @SerializedName("OrderNo")
    val delegateId: Long?
)