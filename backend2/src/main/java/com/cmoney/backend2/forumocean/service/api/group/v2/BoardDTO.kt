package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class BoardDTO(
    @SerializedName("boardType")
    val boardType: String?,
    @SerializedName("canRead")
    val canRead: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isDefault")
    val isDefault: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("groupId")
    val groupId: Long?
)