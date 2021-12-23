package com.cmoney.backend2.brokerdatatransmission.service.api.encryptionkey

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class GetEncryptionKeyResponseWithError(
    @SerializedName("publicKeyCryptography")
    val publicKeyCryptography: String?
) : CMoneyError(), IWithError<GetEncryptionKeyResponse> {
    override fun toRealResponse(): GetEncryptionKeyResponse {
        return GetEncryptionKeyResponse(publicKeyCryptography)
    }
}
