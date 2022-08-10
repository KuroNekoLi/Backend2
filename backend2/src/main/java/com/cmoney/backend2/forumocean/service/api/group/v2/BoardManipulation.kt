package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 修改看板
 */
@Keep
data class BoardManipulation(
    /**
     * 看板名稱
     */
    @SerializedName("name")
    val name: String?,
    /**
     * 角色權限
     */
    @SerializedName("rolesAuth")
    val rolesAuth: List<RolesAuth>?
)