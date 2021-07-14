package com.cmoney.backend2.base.model.request

import android.util.Base64
import org.json.JSONObject

/**
 * 授權連接Token
 */
class AccessToken(originContent: String = CONTENT_DEFAULT) : JwtToken<AccessToken.Payload>(originContent) {

    private val payload: Payload

    init {
        val payloadJson = try {
            JSONObject(String(Base64.decode(originContent.split(".").getOrNull(1).orEmpty(), Base64.URL_SAFE)))
        }catch (e: Exception) {
            JSONObject()
        }
        payload = Payload(
            memberId = payloadJson.optString("sub"),
            appId = payloadJson.optString("app_id"),
            tokenId = payloadJson.optString("token_id"),
            isGuest = payloadJson.optBoolean("is_guest", false),
            issuedTime = payloadJson.optLong("nbf"),
            expiredTime = payloadJson.optLong("exp"),
            issuer = payloadJson.optString("iss"),
            clientId = payloadJson.optString("aud")
        )
    }

    override fun getPayload(): Payload {
        return payload
    }

    override fun isEmpty(): Boolean {
        return getMemberId().isEmpty()
    }

    fun getMemberId() = payload.memberId

    fun getAppId() = payload.appId

    fun getTokenId() = payload.tokenId

    fun getIsGuest() = payload.isGuest

    fun getIssuedTime() = payload.issuedTime

    fun getExpiredTime() = payload.expiredTime

    fun getIssuer() = payload.issuer

    fun getClientId() = payload.clientId

    /**
     * 載體
     *
     * @property memberId 會員ID
     * @property appId App的ID
     * @property tokenId Token的ID
     * @property isGuest 是否為訪客登入
     * @property issuedTime 發行時間
     * @property expiredTime 過期時間
     * @property issuer 發行者
     * @property clientId 客戶端ID
     */
    data class Payload(
        val memberId: String,
        val appId: String,
        val tokenId: String,
        val isGuest: Boolean,
        val issuedTime: Long,
        val expiredTime: Long,
        val issuer: String,
        val clientId: String
    )
}