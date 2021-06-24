package com.cmoney.backend2.ocean.service.api.getAnnouncements

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetAnnouncementsWithError(
    @SerializedName("Announcements")
    val announcements: List<Announcements>
) : CMoneyError(), IWithError<GetAnnouncementsResponse> {
    override fun toRealResponse(): GetAnnouncementsResponse {
        return GetAnnouncementsResponse(announcements)
    }
}