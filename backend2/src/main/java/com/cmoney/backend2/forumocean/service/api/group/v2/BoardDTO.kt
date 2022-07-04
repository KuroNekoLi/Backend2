package com.cmoney.backend2.forumocean.service.api.group.v2


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BoardDTO(
    @SerializedName("canRead")
    val canRead: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isDefault")
    val isDefault: Boolean?,
    @SerializedName("name")
    val name: String?
)