package com.cmoney.backend2.notes.service.api.getnotesbytags

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetNotesByTagsResponseWithError(
    @SerializedName("Notes")
    val notes: List<Note?>?
): CMoneyError(), IWithError<List<Note?>?> {

    override fun toRealResponse(): List<Note> {
        return notes?.mapNotNull { it } ?: emptyList()
    }
}