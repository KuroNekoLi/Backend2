package com.cmoney.backend2.forumocean.service.api.role


import com.google.gson.annotations.SerializedName

data class GetMembersByRoleResponse(
    @SerializedName("memberIds")
    val memberIds: List<Long>?
)