package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 社團角色
 */
data class GroupRole(
    /**
     * 預設角色
     */
    @SerializedName("default")
    val default: Boolean?,
    /**
     * 角色ID
     */
    @SerializedName("id")
    val id: Int?,
    /**
     * 角色名稱
     */
    @SerializedName("name")
    val name: String?,
    /**
     * 角色類別
     */
    @SerializedName("type")
    val type: String?
) {
    /**
     * The type enum for the type of this role.
     */
    fun roleType(): Role {
        return when (type) {
            Role.OWNER.value -> Role.OWNER
            Role.MANAGER.value -> Role.MANAGER
            Role.NON_MEMBER.value -> Role.NON_MEMBER
            Role.NORMAL_MEMBER.value -> Role.NORMAL_MEMBER
            else -> Role.NON_MEMBER
        }
    }
}