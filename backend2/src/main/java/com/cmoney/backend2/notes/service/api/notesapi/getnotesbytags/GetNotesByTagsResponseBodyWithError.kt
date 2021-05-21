package com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.notes.service.api.notesapi.variable.Note
import com.google.gson.annotations.SerializedName

data class GetNotesByTagsResponseBodyWithError (
        @SerializedName("Notes")
        val notes: List<Note?>?
): CMoneyError(), IWithError<GetNotesByTagsResponseBody> {
    override fun toRealResponse(): GetNotesByTagsResponseBody {
        return GetNotesByTagsResponseBody(notes)
    }
}