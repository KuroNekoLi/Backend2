package com.cmoney.backend2.ocean.service.api.club

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 *  是否刪除公告成功response物件
 * @property isSuccess Boolean
 * @constructor
 */
data class IsRemoveAnnouncementSuccessResponse (
    @SerializedName("IsSuccess")
    val isSuccess : Boolean
)

/**
 * 是否刪除成功物件
 * @property isSuccess Boolean
 * @constructor
 */
data class IsRemoveAnnouncementSuccessWithError(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean
) : CMoneyError() , IWithError<IsRemoveAnnouncementSuccessResponse> {
    override fun toRealResponse(): IsRemoveAnnouncementSuccessResponse {
        return IsRemoveAnnouncementSuccessResponse(
            isSuccess = isSuccess
        )
    }
}
