package com.cmoney.backend2.notes.service.api.getpopularandpaynotes

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.notes.service.api.getpopularandpaynotes.GetPopularAndPayNotesResponseBodyWithError.Note
import com.google.gson.annotations.SerializedName

data class GetPopularAndPayNotesResponseBodyWithError(
    @SerializedName("Notes")
    val notes: List<Note?>?
) : CMoneyError(), IWithError<List<Note?>?> {
    data class Note(
        @SerializedName("CoAuthorName")
        val coAuthorName: String?,
        @SerializedName("CreateTime")
        val createTime: Long?,
        @SerializedName("CurrentTime")
        val currentTime: Long?,
        @SerializedName("Image")
        val image: String?,
        @SerializedName("NoteId")
        val noteId: Long?,
        @SerializedName("Price")
        val price: Int?,
        @SerializedName("Title")
        val title: String?,
        @SerializedName("ViewCount")
        val viewCount: Int?
    )

    override fun toRealResponse(): List<Note?>? {
        return notes
    }
}