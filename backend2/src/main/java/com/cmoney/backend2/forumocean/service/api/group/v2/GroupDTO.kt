package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GroupDTO(
    @SerializedName("createTime")
    val createTime: Long?,
    @SerializedName("defaultBoard")
    val defaultBoard: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("groupRoles")
    val groupRoles: List<GroupRoleDTO>?,
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