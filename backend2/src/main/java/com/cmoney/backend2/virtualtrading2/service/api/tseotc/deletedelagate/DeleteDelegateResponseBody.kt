package com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate

import com.google.gson.annotations.SerializedName

/**
 * 刪除委託單回應
 *
 * @property delegateId 委託單號
 *
 */
data class DeleteDelegateResponseBody(
    @SerializedName("OrderNo")
    val delegateId: Long?
)