package com.cmoney.backend2.notes.service.api.notesapi.getnotesbytags

import com.cmoney.backend2.notes.service.api.notesapi.variable.Note
import com.google.gson.annotations.SerializedName

data class GetNotesByTagsResponseBody (
        @SerializedName("Notes")
        val notes: List<Note?>?
)