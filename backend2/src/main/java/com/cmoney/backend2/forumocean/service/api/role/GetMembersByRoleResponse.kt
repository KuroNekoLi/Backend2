package com.cmoney.backend2.forumocean.service.api.role


import com.google.gson.annotations.SerializedName

/**
 * 依角色類型取得會員名單response.
 *
 * @param memberIds 使用者Ids
 */
data class GetMembersByRoleResponse(
    @SerializedName("memberIds")
    val memberIds: List<Long>?
)