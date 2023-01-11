package com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate

import com.google.gson.annotations.SerializedName

/**
 * 刪除委託單回應
 *
 * @property isSuccess 委託是否成功
 * @property message 訊息
 * @property delegateId 委託單號
 *
 */
data class DeleteDelegateResponseBody(
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("orderNo")
    val delegateId: Long?
)