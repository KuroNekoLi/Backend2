package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBody
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBody

interface IdentityProviderWeb {

    /**
     * Is AccessToken latest
     *
     * @return true表示token是最新發行的，false表示token不是最新發行的
     */
    suspend fun isTokenLatest(): Result<Boolean>

    /**
     * login by email
     *
     * @param hashedPassword MD5 hashed password
     * @return 登入結果
     */
    suspend fun loginByEmail(account: String, hashedPassword: String): Result<GetTokenResponseBody>

    /**
     * login by cellphone
     *
     * @param hashedPassword MD5 hashed password
     * @return 登入結果
     */
    suspend fun loginByCellphone(cellphone: String, hashedPassword: String): Result<GetTokenResponseBody>

    /**
     * Login by facebook
     *
     * @param accessToken facebook 授權Token
     * @return 登入結果
     */
    suspend fun loginByFacebook(accessToken: String): Result<GetTokenResponseBody>

    /**
     * Login by google
     *
     * @param accessToken google 授權Token
     * @return 登入結果
     */
    suspend fun loginByGoogle(accessToken: String): Result<GetTokenResponseBody>

    /**
     * Login by firebase anonymous token
     *
     * @param anonymousToken firebase 匿名登入Token
     * @return 登入結果
     */
    suspend fun loginByFirebaseAnonymousToken(anonymousToken: String): Result<GetTokenResponseBody>

    /**
     * Login by pkce
     *
     * @param redirectUri deeplink的URI
     * @param codeVerifier PKCE要求時本地產生的驗證字串
     * @param code PKCE取得的授權碼
     * @return 登入結果
     */
    suspend fun loginByPkce(redirectUri: String, codeVerifier: String, code: String): Result<GetTokenResponseBody>

    /**
     * 合作的三方廠商的登入
     *
     * @param providerToken 合作廠商的Token
     *
     */
    suspend fun loginByCMoneyThirdParty(providerToken: String): Result<GetTokenResponseBody>


    /**
     * Refresh token
     *
     * @param refreshToken 前一次登入取得的RefreshToken
     * @return 登入結果
     */
    suspend fun refreshToken(refreshToken: String): Result<GetTokenResponseBody>

    /**
     * Revoke token
     *
     * @param token 欲撤銷的accessToken
     * @return 撤銷授權結果
     */
    suspend fun revokeToken(token: String): Result<RevokeResponseBody>
}