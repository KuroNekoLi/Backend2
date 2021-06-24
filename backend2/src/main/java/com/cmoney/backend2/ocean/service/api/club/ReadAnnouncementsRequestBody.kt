package com.cmoney.backend2.ocean.service.api.club

import com.google.gson.annotations.SerializedName

/**
 * 拿到所有公告RequestBody
 * @property appId Int
 * @property guid String
 * @constructor
 */
data class ReadAnnouncementsRequestBody(
    @SerializedName("AppId")
    val appId : Int,
    @SerializedName("Guid")
    val guid : String

)