package com.cmoney.backend2.virtualtrading2.service.api.tseotc.createdelegate

import com.google.gson.annotations.SerializedName

/**
 * 建立委託單回應
 *
 * @property isSuccess 委託是否成功
 * @property message 訊息
 * @property delegateId 委託單號
 *
 */
data class CreateDelegateResponseBody(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("OrderNo")
    val delegateId: Long?
)