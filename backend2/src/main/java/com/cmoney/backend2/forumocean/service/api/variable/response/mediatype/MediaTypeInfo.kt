package com.cmoney.backend2.forumocean.service.api.variable.response.mediatype

import com.cmoney.backend2.forumocean.service.api.schemas.v2.Media
import com.google.gson.annotations.SerializedName

/**
 *  為更精確反映後端API格式，請改用[Media]，或參照swagger另建類別於api.schema資料夾中
 */
@Deprecated("請改用 api.schemas.v2.Media")
data class MediaTypeInfo(
    @SerializedName("mediaType", alternate = ["MediaType"])
    val type : TypeInfo?,
    @SerializedName("url", alternate = ["Url"])
    val url : String?
)