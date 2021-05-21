package com.cmoney.backend2.notes.service.api.getnotesbytags

import com.google.gson.annotations.SerializedName

data class GetNotesByTagsRequestBody(
    @SerializedName("AppId")
    val appId: Int,
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("TagIds")
    val tagIds: List<Int>,
    @SerializedName("BaseNoteId")
    val baseNoteId: Long,
    @SerializedName("FetchSize")
    val fetchSize: Int
)