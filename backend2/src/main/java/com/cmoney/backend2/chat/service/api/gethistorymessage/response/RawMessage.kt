package com.cmoney.backend2.chat.service.api.gethistorymessage.response


import com.google.gson.annotations.SerializedName

data class RawMessage(
    @SerializedName("chatroomId")
    val chatroomId: Long?,
    @SerializedName("content")
    val content: RawContent?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("senderId")
    val senderId: Long?,
    @SerializedName("state")
    val state: Int?,
    @SerializedName("timestamp")
    val timestamp: Long?,
    @SerializedName("type")
    val type: String?
) {
    fun asReal(): Message? {
        return when (val content = content?.asReal(type = type)) {
            is Content.Delete -> {
                DeleteMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            is Content.Empty -> {
                EmptyMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            is Content.Exception -> {
                ExceptionMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            is Content.Image -> {
                ImageMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            is Content.Reload -> {
                ReloadMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            is Content.Sticker -> {
                StickerMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            is Content.Text -> {
                TextMessage(
                    id = id,
                    chatroomId = chatroomId,
                    content = content,
                    type = type,
                    senderId = senderId,
                    timestamp = timestamp
                )
            }
            null -> {
                null
            }
        }
    }
}