package com.cmoney.backend2.ocean.service.api.club

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * 拿到可以解析錯誤的所有社團公告物件
 * @property list List<AnnouncementResult>?
 * @constructor
 */
data class AnnouncementListResponse(
    @SerializedName("Announcements")
    val list: List<AnnouncementResult>?
)

/**
 * 可以解析錯誤的所有社團公告物件
 * @property list List<AnnouncementResult>?
 * @constructor
 */
data class AnnouncementListResponseWithError(
    @SerializedName("Announcements")
    val list: List<AnnouncementResult>?
) : CMoneyError(), IWithError<AnnouncementListResponse> {
    override fun toRealResponse(): AnnouncementListResponse {
        return AnnouncementListResponse(
            list
        )
    }
}
