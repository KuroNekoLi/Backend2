package com.cmoney.backend2.ocean.service.api.uploadchannelimage

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UploadChannelImageResponseBodyWithError (
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
): CMoneyError(), IWithError<UploadChannelImageResponseBody> {
    override fun toRealResponse(): UploadChannelImageResponseBody {
        return UploadChannelImageResponseBody(isSuccess)
    }
}