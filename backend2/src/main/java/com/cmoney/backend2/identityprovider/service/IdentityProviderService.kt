package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBodyWithError
import com.cmoney.backend2.identityprovider.service.api.islatest.IsLatestResponseBodyWithError
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBodyWithError
import retrofit2.Response
import retrofit2.http.*

interface IdentityProviderService {
    /**
     * 確認Token是否為最新
     *
     */
    @RecordApi
    @GET
    suspend fun isTokenLatest(
        @Url url: String,
        @Header("Authorization")
        accessToken: String
    ): Response<IsLatestResponseBodyWithError>

    /**
     * 請求令牌端點
     *
     * @param grantType 請求的 OAuth 流程
     * @param clientId 客戶端應用程式編號
     * @param scope 請求的資源範圍
     * @param clientSecret 客戶端應用程式密碼
     * @param account 帳號
     * @param hashedPassword 密碼MD5
     * @param loginMethod 登入方式
     * @param providerToken 使用者第三方token登入
     * @param provider 第三方登入方式
     * @param code 從Authorization endpoint得到的code
     * @param redirectUri 登入結束後要返回的網址
     * @param refreshToken 用來取得新的access token
     * @param codeVerifier PKCE要求時帶的隨機驗證字串，[CodeVerifier](https://datatracker.ietf.org/doc/html/rfc7636#page-8)
     *
     */
    @RecordApi
    @POST
    @FormUrlEncoded
    suspend fun getIdentityToken(
        @Url url: String,
        @Header("x-cmapi-trace-context")
        xApiLog: String,
        @Field(value = "grant_type")
        grantType: String,
        @Field(value = "client_id")
        clientId: String,
        @Field(value = "scope")
        scope: String? = null,
        @Field(value = "client_secret")
        clientSecret: String? = null,
        @Field(value = "account")
        account: String? = null,
        @Field(value = "hashed_password")
        hashedPassword: String? = null,
        @Field(value = "token")
        providerToken: String? = null,
        @Field(value = "provider")
        provider: String? = null,
        @Field(value = "login_method")
        loginMethod: String? = null,
        @Field(value = "code")
        code: String? = null,
        @Field(value = "redirect_uri")
        redirectUri: String? = null,
        @Field(value = "refresh_token")
        refreshToken: String? = null,
        @Field(value = "code_verifier")
        codeVerifier: String? = null
    ): Response<GetTokenResponseBodyWithError>

    /**
     * 撤回令牌端點
     *
     * @param clientId 客戶端應用程式編號
     * @param clientSecret 客戶端應用程式密碼
     * @param token 想要撤回的token
     * @param tokenType token的類型，目前只支援refresh_token。
     * @return
     */
    @RecordApi
    @POST
    @FormUrlEncoded
    suspend fun revokeIdentityToken(
        @Url url: String,
        @Header("Authorization")
        accessToken: String,
        @Field(value = "client_id")
        clientId: String,
        @Field(value = "client_secret")
        clientSecret: String? = null,
        @Field(value = "token")
        token: String,
        @Field(value = "token_type_hint")
        tokenType: String
    ): Response<RevokeResponseBodyWithError>
}