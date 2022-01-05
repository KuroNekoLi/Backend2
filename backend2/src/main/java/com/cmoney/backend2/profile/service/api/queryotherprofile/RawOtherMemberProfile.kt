package com.cmoney.backend2.profile.service.api.queryotherprofile


import com.google.gson.annotations.SerializedName

data class RawOtherMemberProfile(
    @SerializedName("badges")
    val badges: List<Int>?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("communityRoles")
    val communityRoles: List<Int>?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isBindingCellphone")
    val isBindingCellphone: Boolean?,
    @SerializedName("level")
    val level: Int?,
    @SerializedName("nickname")
    val nickname: String?
)