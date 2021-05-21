package com.cmoney.backend2.identityprovider.service.api.revoke

/**
 *
 * @property tokenType Token類型
 * @property success 是否成功
 */
data class RevokeResponseBody(
    val tokenType: String?,
    val success: Boolean?
)