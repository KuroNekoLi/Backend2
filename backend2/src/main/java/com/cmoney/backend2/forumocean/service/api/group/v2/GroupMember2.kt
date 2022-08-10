package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 社團成員物件 第二種
 */
data class GroupMember2(
    /**
     * memberId
     */
    @SerializedName("id")
    val id: Int?,
    /**
     * 角色ID
     */
    @SerializedName("roles")
    val roles: List<Int>?
)