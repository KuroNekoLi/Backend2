package com.cmoney.backend2.forumocean.service.api.variable.request

import com.google.gson.annotations.SerializedName

enum class GroupJoinType(val value : Int) {
    @SerializedName("-1")
    Invitation(-1),
    @SerializedName("0")
    Private(0),
    @SerializedName("1")
    Public(1)
}
