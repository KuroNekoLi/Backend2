package com.cmoney.backend2.additioninformationrevisit.service.api.request

import com.google.gson.annotations.SerializedName

/**
 * DTO for use id for request apis.
 */
data class RequestIds(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String
)