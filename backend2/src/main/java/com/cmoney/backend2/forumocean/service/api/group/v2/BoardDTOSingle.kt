package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class BoardDTOSingle(
    @SerializedName("auth")
    val auth: Auth?,
    @SerializedName("boardType")
    val boardType: String?,
    @SerializedName("groupId")
    val groupId: Int?,
    @SerializedName("isDefault")
    val isDefault: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rolesAuth")
    val rolesAuth: List<RolesAuth>?
)