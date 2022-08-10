package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 角色
 */
@Keep
data class MemberRoles(
    /**
     * 角色ID
     */
    @SerializedName("roles")
    val roles: List<Int>?
)