package com.cmoney.backend2.notes.service.api.notesapi.getnotes

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.notes.service.api.notesapi.variable.Note
import com.google.gson.annotations.SerializedName

data class GetNotesResponseBodyWithError(
    @SerializedName("Notes")
    val notes: List<Note?>?
): CMoneyError(), IWithError<GetNotesResponseBody> {
    override fun toRealResponse(): GetNotesResponseBody {
        return GetNotesResponseBody(notes)
    }
}