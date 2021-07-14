package com.cmoney.backend2.common.service.api.changenickname

import com.google.gson.annotations.SerializedName

data class ChangeNicknameResponse(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?,
    @SerializedName("NickName")
    val nickname : String?
)