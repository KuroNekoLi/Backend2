package com.cmoney.backend2.forumocean.service.api.chatroom

import com.google.gson.annotations.SerializedName

data class GetUncheckChatRoomCountResponse(
    @SerializedName("uncheckChatroomCount")
    val uncheckChatroomCount: Int?
)