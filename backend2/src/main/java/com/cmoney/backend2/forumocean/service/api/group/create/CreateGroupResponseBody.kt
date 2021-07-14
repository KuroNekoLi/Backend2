package com.cmoney.backend2.forumocean.service.api.group.create

import com.google.gson.annotations.SerializedName

data class CreateGroupResponseBody(
    @SerializedName("groupId")
    val groupId : Long?
)
