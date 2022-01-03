package com.cmoney.backend2.forumocean.service.api.variable.response

import com.google.gson.annotations.SerializedName

enum class GroupPositionInfo {
    @SerializedName("1")
    VISITOR,
    @SerializedName("2")
    NON_MEMBER,
    @SerializedName("4")
    NORMAL,
    @SerializedName("64")
    MANAGEMENT,
    @SerializedName("128")
    PRESIDENT
}