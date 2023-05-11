package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBody
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBody

interface IdentityProviderWeb {

    val manager: GlobalBackend2Manager

    /**
     * Is AccessToken latest
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return true表示token是最新發行的，false表示token不是最新發行的
     */
    suspend fun isTokenLatest(
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/session/isLatest"
    ): Result<Boolean>

    /**
     * login by email
     *
     * @param hashedPassword MD5 hashed password
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun loginByEmail(
        account: String,
        hashedPassword: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * login by cellphone
     *
     * @param hashedPassword MD5 hashed password
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun loginByCellphone(
        cellphone: String,
        hashedPassword: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * Login by facebook
     *
     * @param accessToken facebook 授權Token
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun loginByFacebook(
        accessToken: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * Login by google
     *
     * @param accessToken google 授權Token
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun loginByGoogle(
        accessToken: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * Login by firebase anonymous token
     *
     * @param anonymousToken firebase 匿名登入Token
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun loginByFirebaseAnonymousToken(
        anonymousToken: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * Login by pkce
     *
     * @param redirectUri deeplink的URI
     * @param codeVerifier PKCE要求時本地產生的驗證字串
     * @param code PKCE取得的授權碼
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun loginByPkce(
        redirectUri: String,
        codeVerifier: String,
        code: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * 合作的三方廠商的登入
     *
     * @param providerToken 合作廠商的Token
     * @param domain 網域名稱
     * @param url 完整的Url
     */
    suspend fun loginByCMoneyThirdParty(
        providerToken: String,
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>


    /**
     * Refresh token
     *
     * @param refreshToken 前一次登入取得的RefreshToken
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 登入結果
     */
    suspend fun refreshToken(
        refreshToken: String = manager.getRefreshToken(),
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/token"
    ): Result<GetTokenResponseBody>

    /**
     * Revoke token
     *
     * @param token 欲撤銷的refreshToken
     * @param domain 網域名稱
     * @param url 完整的Url
     * @return 撤銷授權結果
     */
    suspend fun revokeToken(
        token: String = manager.getRefreshToken(),
        domain: String = manager.getIdentityProviderSettingAdapter().getDomain(),
        url: String = "${domain}identity/revocation"
    ): Result<RevokeResponseBody>
}