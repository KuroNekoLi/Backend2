package com.cmoney.backend2.identityprovider.service.api.gettoken

/**
 *
 * @property accessToken 連接Token，打API用
 * @property expiresIn 多久會過期
 * @property idToken 會員資訊Token
 * @property refreshToken 重新取得Token
 * @property tokenType Token類型
 */
data class GetTokenResponseBody(
    val accessToken: String?,
    val expiresIn: Int?,
    val idToken: String?,
    val refreshToken: String?,
    val tokenType: String?
)