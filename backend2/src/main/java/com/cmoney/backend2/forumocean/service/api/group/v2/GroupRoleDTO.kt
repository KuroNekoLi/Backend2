package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GroupRoleDTO(
    @SerializedName("default")
    val default: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
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