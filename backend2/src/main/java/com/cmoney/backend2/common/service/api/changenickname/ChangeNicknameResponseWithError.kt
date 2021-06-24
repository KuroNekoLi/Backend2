package com.cmoney.backend2.common.service.api.changenickname

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class ChangeNicknameResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?,
    @SerializedName("NickName")
    val nickname : String?
)  : CMoneyError(), IWithError<ChangeNicknameResponse> {
    override fun toRealResponse(): ChangeNicknameResponse {
        return ChangeNicknameResponse(isSuccess, nickname)
    }
}