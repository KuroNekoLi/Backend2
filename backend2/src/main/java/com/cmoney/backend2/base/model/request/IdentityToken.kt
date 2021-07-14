package com.cmoney.backend2.base.model.request

import android.util.Base64
import org.json.JSONObject

/**
 * 會員資訊辨識Token
 */
class IdentityToken(originContent: String = CONTENT_DEFAULT) : JwtToken<IdentityToken.Payload>(originContent) {
    private val payload: Payload

    init {
        val payloadJson = try {
            JSONObject(String(Base64.decode(originContent.split(".").getOrNull(1).orEmpty(), Base64.URL_SAFE)))
        }catch (e: Exception) {
            JSONObject()
        }
        payload = Payload(
            memberId = payloadJson.optString("sub"),
            memberGuid = payloadJson.optString("user_guid"),
            memberNickname = payloadJson.optString("nickname"),
            issuedTime = payloadJson.optLong("nbf"),
            expiredTime = payloadJson.optLong("exp"),
            issuer = payloadJson.optString("iss"),
            clientId = payloadJson.optString("aud"),
            isNewUser = payloadJson.optBoolean("is_new_user", false)
        )
    }

    override fun getPayload(): Payload {
        return payload
    }

    override fun isEmpty(): Boolean {
        return getMemberId().isEmpty() || getMemberGuid().isEmpty()
    }

    fun getMemberId() = payload.memberId

    fun getMemberGuid() = payload.memberGuid

    fun getMemberNickname() = payload.memberNickname

    fun getIssuedTime() = payload.issuedTime

    fun getExpiredTime() = payload.expiredTime

    fun getIssuer() = payload.issuer

    fun getClientId() = payload.clientId

    fun getIsNewUser() = payload.isNewUser

    /**
     * 載體
     *
     * @property memberId 會員ID
     * @property memberGuid 會員Guid
     * @property memberNickname 會員暱稱
     * @property issuedTime 發行時間
     * @property expiredTime 過期時間
     * @property issuer 發行者
     * @property clientId 客戶端ID
     * @property isNewUser 是否為新註冊用戶
     */
    data class Payload(
        val memberId: String,
        val memberGuid: String,
        val memberNickname: String,
        val issuedTime: Long,
        val expiredTime: Long,
        val issuer: String,
        val clientId: String,
        val isNewUser: Boolean
    )
}