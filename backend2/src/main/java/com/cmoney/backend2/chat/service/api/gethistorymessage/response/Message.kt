package com.cmoney.backend2.chat.service.api.gethistorymessage.response

sealed class Message(
    open val chatroomId: Long?,
    open val content: Content?,
    open val id: Long?,
    open val senderId: Long?,
    open val timestamp: Long?,
    open val type: String?
)

data class TextMessage(
    override val chatroomId: Long?,
    override val content: Content.Text?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class StickerMessage(
    override val chatroomId: Long?,
    override val content: Content.Sticker?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class ImageMessage(
    override val chatroomId: Long?,
    override val content: Content.Image?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class EmptyMessage(
    override val chatroomId: Long?,
    override val content: Content.Empty?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class ReloadMessage(
    override val chatroomId: Long?,
    override val content: Content.Reload?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class DeleteMessage(
    override val chatroomId: Long?,
    override val content: Content.Delete?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)

data class ExceptionMessage(
    override val chatroomId: Long?,
    override val content: Content.Exception?,
    override val id: Long?,
    override val senderId: Long?,
    override val timestamp: Long?,
    override val type: String?
): Message(
    chatroomId = chatroomId,
    content = content,
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type
)