package com.cmoney.backend2.notes.service.api.getnotesbycoauthorIds

import com.google.gson.annotations.SerializedName

class GetNotesByCoAuthorIdsRequestBody(
    @SerializedName("CoAuthorIds") val coAuthorIds: List<Long>,
    @SerializedName("BaseNoteId") val baseNoteId: Long,
    @SerializedName("FetchSize") val fetchSize: Int
)
