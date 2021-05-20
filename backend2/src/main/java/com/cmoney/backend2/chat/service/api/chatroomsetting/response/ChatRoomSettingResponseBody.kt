package com.cmoney.backend2.chat.service.api.chatroomsetting.response

import com.google.gson.annotations.SerializedName

data class ChatRoomSettingResponseBody(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("properties")
    val properties: ChatRoomSettingProperties?
)