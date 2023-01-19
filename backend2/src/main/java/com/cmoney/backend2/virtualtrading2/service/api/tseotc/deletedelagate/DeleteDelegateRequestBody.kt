package com.cmoney.backend2.virtualtrading2.service.api.tseotc.deletedelagate


import com.google.gson.annotations.SerializedName

/**
 * 刪除委託單請求
 *
 * @property accountId 帳號編號
 * @property groupId 競技場編號
 * @property delegateId 委託單編號
 *
 */
data class DeleteDelegateRequestBody(
    @SerializedName("AccountId")
    val accountId: Long,
    @SerializedName("GroupId")
    val groupId: Long,
    @SerializedName("TargetOrdNo")
    val delegateId: Long
)