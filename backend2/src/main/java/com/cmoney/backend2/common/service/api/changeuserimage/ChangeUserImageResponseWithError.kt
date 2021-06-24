package com.cmoney.backend2.common.service.api.changeuserimage

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class ChangeUserImageResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?,
    @SerializedName("NewImagePath")
    val imagePath : String?
)  : CMoneyError(), IWithError<ChangeUserImageResponse> {
    override fun toRealResponse(): ChangeUserImageResponse {
        return ChangeUserImageResponse(isSuccess, imagePath)
    }
}