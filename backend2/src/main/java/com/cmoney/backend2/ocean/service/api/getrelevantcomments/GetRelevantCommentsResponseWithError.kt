package com.cmoney.backend2.ocean.service.api.getrelevantcomments

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetRelevantCommentsResponseWithError(
    @SerializedName("Data")
    val data: List<RelevantComment>
) : CMoneyError(), IWithError<GetRelevantCommentsResponse> {
    override fun toRealResponse(): GetRelevantCommentsResponse {
        return GetRelevantCommentsResponse(data)
    }
}
