package com.cmoney.backend2.notes.service.api.getpopularandpaynotes

import com.google.gson.annotations.SerializedName

data class GetPopularAndPayNotesRequestBody(
    @SerializedName("AppId") val appId: Int,
    @SerializedName("BlogId") val blogId: Long,
    @SerializedName("FetchSize") val fetchSize: Int
)