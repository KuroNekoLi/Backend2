package com.cmoney.backend2.media.service.api.getmedialistbyappid


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("TagId")
    val tagId: Int?,
    @SerializedName("TagName")
    val tagName: String?
)