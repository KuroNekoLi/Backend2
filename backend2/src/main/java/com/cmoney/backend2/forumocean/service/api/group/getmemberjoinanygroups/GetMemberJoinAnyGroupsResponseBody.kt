package com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups

import com.google.gson.annotations.SerializedName

/**
 * 取得指定使用者是否加入或擁有任何社團
 *
 * @property isJoin 是否有 true:有 false:無
 */
data class GetMemberJoinAnyGroupsResponseBody(
    @SerializedName("isJoin")
    val isJoin: Boolean?
)