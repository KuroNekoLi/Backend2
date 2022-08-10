package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 角色權限
 */
data class RolesAuth(
    /**
     * 是否可讀
     */
    @SerializedName("canRead")
    val canRead: Boolean?,
    /**
     * 角色ID
     */
    @SerializedName("roleId")
    val roleId: Int?
)