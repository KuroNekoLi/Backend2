package com.cmoney.backend2.identityprovider.service

import com.cmoney.backend2.identityprovider.service.api.gettoken.GetTokenResponseBody
import com.cmoney.backend2.identityprovider.service.api.revoke.RevokeResponseBody

interface IdentityProviderWeb {

    suspend fun isTokenLatest(): Result<Boolean>

    /**
     * @param hashedPassword MD5 hashed password
     */
    suspend fun loginByEmail(account: String, hashedPassword: String): Result<GetTokenResponseBody>

    /**
     * @param hashedPassword MD5 hashed password
     */
    suspend fun loginByCellphone(cellphone: String, hashedPassword: String): Result<GetTokenResponseBody>

    suspend fun loginByFacebook(accessToken: String): Result<GetTokenResponseBody>

    suspend fun loginByGoogle(accessToken: String): Result<GetTokenResponseBody>

    suspend fun loginByFirebaseAnonymousToken(anonymousToken: String): Result<GetTokenResponseBody>

    suspend fun refreshToken(refreshToken: String): Result<GetTokenResponseBody>

    suspend fun revokeToken(token: String): Result<RevokeResponseBody>
}