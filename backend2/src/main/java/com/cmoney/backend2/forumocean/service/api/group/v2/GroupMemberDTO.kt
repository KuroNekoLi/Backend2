package com.cmoney.backend2.forumocean.service.api.group.v2

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GroupMemberDTO(
    @SerializedName("image")
    val image: String?,
    @SerializedName("memberId")
    val memberId: Int?,
    @SerializedName("nickName")
    val nickName: String?,
    @SerializedName("roles")
    val roleIds: List<Int>?
)