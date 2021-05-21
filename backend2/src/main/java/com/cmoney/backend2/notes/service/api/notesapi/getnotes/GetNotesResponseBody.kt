package com.cmoney.backend2.notes.service.api.notesapi.getnotes


import com.cmoney.backend2.notes.service.api.notesapi.variable.Note
import com.google.gson.annotations.SerializedName

data class GetNotesResponseBody(
    @SerializedName("Notes")
    val notes: List<Note?>?
)