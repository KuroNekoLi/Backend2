package com.cmoney.backend2.forumocean.service.api.columnist

import com.google.gson.annotations.SerializedName

data class GetColumnistVipGroupResponse(
    @SerializedName("groupId")
    val groupId : Long?,
)
