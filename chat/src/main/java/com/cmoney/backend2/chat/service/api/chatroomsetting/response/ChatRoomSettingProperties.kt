package com.cmoney.backend2.chat.service.api.chatroomsetting.response

import com.google.gson.annotations.SerializedName

data class ChatRoomSettingProperties(
    @SerializedName("@Roles")
    val roles: List<String?>?
)