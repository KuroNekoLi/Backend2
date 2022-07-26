package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RolesAuth(
    @SerializedName("canRead")
    val canRead: Boolean?,
    @SerializedName("roleId")
    val roleId: Int?
)