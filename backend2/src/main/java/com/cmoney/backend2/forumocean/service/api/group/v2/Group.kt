package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 社團
 */
data class Group(
    /**
     * 建立時間
     */
    @SerializedName("createTime")
    val createTime: Long?,
    /**
     * 預設看板ID
     */
    @SerializedName("defaultBoard")
    val defaultBoard: Int?,
    /**
     * 社團敘述
     */
    @SerializedName("description")
    val description: String?,
    /**
     * 社團角色
     */
    @SerializedName("groupRoles")
    val groupRoles: List<GroupRole>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("isPending")
    val isPending: Boolean?,
    @SerializedName("memberCount")
    val memberCount: Int?,
    @SerializedName("memberRoles")
    val memberRoles: List<Int>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("needApproval")
    val needApproval: Boolean?
)