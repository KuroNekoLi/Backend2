package com.cmoney.backend2.ocean.service.api.createannouncement

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CreateAnnouncementResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<CreateAnnouncementResponse> {
    override fun toRealResponse(): CreateAnnouncementResponse {
        return CreateAnnouncementResponse(isSuccess)
    }
}
