package com.cmoney.backend2.notes.service.api.notesapi.variable


import com.google.gson.annotations.SerializedName

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