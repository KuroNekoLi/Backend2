package com.cmoney.backend2.ocean.service.api.removeannouncement

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class RemoveAnnouncementResponseWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
) : CMoneyError(), IWithError<RemoveAnnouncementResponse> {
    override fun toRealResponse(): RemoveAnnouncementResponse {
        return RemoveAnnouncementResponse(isSuccess)
    }
}
