package com.cmoney.backend2.forumocean.service.api.variable.response

import com.google.gson.annotations.SerializedName

enum class GroupJoinTypeInfo {
    @SerializedName("-1")
    Invitation,
    @SerializedName("0")
    Private,
    @SerializedName("1")
    Public
}