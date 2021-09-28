package com.cmoney.backend2.notes.service.api.getnotesbytags

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("NoteId")
    val noteId: Long?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Image")
    val image: String?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("ViewCount")
    val viewCount: Int?,
    @SerializedName("Price")
    val price: Int?,
    @SerializedName("CoAuthorName")
    val coAuthorName: String?,
    @SerializedName("Tags")
    val tags: List<Tag>?
) {
    data class Tag(
        @SerializedName("Id")
        val id: Long?,
        @SerializedName("Name")
        val name: String?
    )
}
