package com.cmoney.backend2.media.service.api.getmediadetail

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetMediaDetailResponseWithError(
    @SerializedName("MediaId")
    val mediaId: Long?,
    @SerializedName("MediaUrl")
    val mediaUrl: String?,
    @SerializedName("MediaTitle")
    val mediaTitle: String?,
    @SerializedName("MediaDescription")
    val mediaDescription: String?,
    @SerializedName("MediaOverview")
    val mediaOverview: String?,
    @SerializedName("HasAuth")
    val hasAuth: Boolean?,
    @SerializedName("ProductId")
    val productId: String?
) : CMoneyError(), IWithError<GetMediaDetailResponse> {
    override fun toRealResponse(): GetMediaDetailResponse {
        return GetMediaDetailResponse(
            mediaId,
            mediaUrl,
            mediaTitle,
            mediaDescription,
            mediaOverview,
            hasAuth,
            productId
        )
    }
}