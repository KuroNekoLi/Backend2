package com.cmoney.backend2.chat.service.api.gethistorymessage.response

import com.cmoney.backend2.chat.extension.safeRun
import org.json.JSONObject

sealed class Message(
    open val chatroomId: Long,
    open val content: Content,
    open val id: Long,
    open val senderId: Long,
    open val timestamp: Long,
    open val type: String
) {

    companion object {
        private const val PROPERTY_ID = "id"
        private const val PROPERTY_TYPE = "type"
        private const val PROPERTY_CONTENT = "content"
        private const val PROPERTY_SENDER_ID = "senderId"
        private const val PROPERTY_TIMESTAMP = "timestamp"
        private const val PROPERTY_CHATROOM_ID = "chatroomId"

        fun fromJson(json: JSONObject): Message? {
            val id = json.safeRun {
                getLong(PROPERTY_ID)
            } ?: return null
            val chatroomId = json.safeRun {
                getLong(PROPERTY_CHATROOM_ID)
            } ?: return null
            val senderId = json.safeRun {
                getLong(PROPERTY_SENDER_ID)
            } ?: return null
            val timeStamp = json.safeRun {
                getLong(PROPERTY_TIMESTAMP)
            } ?: return null
            val type = json.safeRun {
                getString(PROPERTY_TYPE)
            } ?: return null
            val contentJson = json.safeRun {
                getJSONObject(PROPERTY_CONTENT)
            } ?: return null
            return when (val content = Content.fromJson(type, contentJson)) {
                is Content.Delete -> {
                    DeleteMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                is Content.Empty -> {
                    EmptyMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                is Content.Exception -> {
                    ExceptionMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                is Content.Image -> {
                    ImageMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                is Content.Reload -> {
                    ReloadMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                is Content.Sticker -> {
                    StickerMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                is Content.Text -> {
                    TextMessage(
                        id = id,
                        chatroomId = chatroomId,
                        content = content,
                        type = type,
                        senderId = senderId,
                        timestamp = timeStamp
                    )
                }
                null -> {
                    null
                }
            }
        }
    }
}

data class TextMessage(
    override val chatroomId: Long,
    override val content: Content.Text,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class StickerMessage(
    override val chatroomId: Long,
    override val content: Content.Sticker,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class ImageMessage(
    override val chatroomId: Long,
    override val content: Content.Image,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class EmptyMessage(
    override val chatroomId: Long,
    override val content: Content.Empty,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class ReloadMessage(
    override val chatroomId: Long,
    override val content: Content.Reload,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class DeleteMessage(
    override val chatroomId: Long,
    override val content: Content.Delete,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class ExceptionMessage(
    override val chatroomId: Long,
    override val content: Content.Exception,
    override val id: Long,
    override val senderId: Long,
    override val timestamp: Long,
    override val type: String
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)