package com.cmoney.backend2.ocean.service.api.getbadgescollection

import com.google.gson.annotations.SerializedName

data class GetBadgesCollectionRequestBody(
    @SerializedName("Guid")
    val guid : String,
    @SerializedName("AppId")
    val appId: Int
)