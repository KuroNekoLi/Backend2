package com.cmoney.backend2.media.service.api.getmediadetail

data class GetMediaDetailResponse(
    val mediaId: Long?,
    val mediaUrl: String?,
    val mediaTitle: String?,
    val mediaDescription: String?,
    val mediaOverview: String?,
    val hasAuth: Boolean?,
    val productId: String?
)