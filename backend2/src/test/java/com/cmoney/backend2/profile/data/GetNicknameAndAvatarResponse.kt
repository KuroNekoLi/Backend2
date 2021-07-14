package com.cmoney.backend2.profile.data

import com.google.gson.annotations.SerializedName

data class GetNicknameAndAvatarResponse(
    @SerializedName("id")
    val memberId: Long?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("image")
    val image: String?
)