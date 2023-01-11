package com.cmoney.backend2.virtualtrading2.web.tseotc.deletedelegate

/**
 * 建立委託單請求
 *
 * @property accountId 帳號編號
 * @property groupId 競技場編號
 * @property delegateId 委託單編號
 */
data class DeleteDelegateRequest(
    val accountId: Long,
    val groupId: Long,
    val delegateId: Long
)