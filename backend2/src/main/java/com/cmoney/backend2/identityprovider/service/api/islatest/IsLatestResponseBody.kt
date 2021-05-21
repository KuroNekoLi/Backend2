package com.cmoney.backend2.identityprovider.service.api.islatest

/**
 * Token是否最新
 *
 * @property isSuccess true代表最新，否則是舊的。
 */
data class IsLatestResponseBody(
    val isSuccess: Boolean
)