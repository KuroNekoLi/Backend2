package com.cmoney.backend2.chat.service.api.chatroomsetting.response

import com.google.gson.annotations.SerializedName

/**
 * 聊天室設定
 *
 * @property id 標號
 * @property properties 設定內容
 */
data class ChatRoomSettingResponseBody(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("properties")
    val properties: ChatRoomSettingProperties?
)