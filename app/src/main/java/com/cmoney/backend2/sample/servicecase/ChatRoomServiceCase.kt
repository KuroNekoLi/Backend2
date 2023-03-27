package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.chat.di.BACKEND2_CHAT_DEBUG
import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.api.chatroomsetting.request.ChatRoomSettingUpdateProperties
import com.cmoney.backend2.chat.service.api.variable.Subject
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject
import org.koin.core.qualifier.StringQualifier

class ChatRoomServiceCase(
    private val roomId: Long = 1,
    chatQualifier: StringQualifier = BACKEND2_CHAT_DEBUG
) : ServiceCase {

    private val chatRoomWebImpl by inject<ChatRoomWeb>(chatQualifier)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun testAll() {
        chatRoomWebImpl.getAvailableRooms().logResponse(TAG)
        chatRoomWebImpl.updateTargetRoom(roomId, ChatRoomSettingUpdateProperties(description = "", announcement = listOf()))
        chatRoomWebImpl.getTargetRoom(roomId).logResponse(TAG)
        chatRoomWebImpl.getAllUser(roomId).logResponse(TAG)
        chatRoomWebImpl.getOnlineUserCount(roomId).logResponse(TAG)
        val userProfileResponseBody = chatRoomWebImpl.getUserProfile().getOrElse {
            it.printStackTrace()
            return
        }
        chatRoomWebImpl.updateProfile(
            "New nick name", "New image path"
        )
            .logResponse(TAG)
        val userId = userProfileResponseBody.id ?: return
        chatRoomWebImpl.getBindingSubjectRuleSets(Subject.USER_PROFILE, userId)
            .logResponse(TAG)
        chatRoomWebImpl.getTargetUserProfile(userId).logResponse(TAG)
        chatRoomWebImpl.getUserCurrentSubjectRoles(Subject.CHAT_ROOM, roomId)
            .logResponse(TAG)
        chatRoomWebImpl.lookUpSubjectAllRoles(Subject.CHAT_ROOM, roomId)
            .logResponse(TAG)
        chatRoomWebImpl.getHistoryMessageFromLatest(roomId, 30).logResponse(TAG)
        chatRoomWebImpl.getHistoryMessageFromOldest(roomId, 30).logResponse(TAG)
        chatRoomWebImpl.getMessageById(1).logResponse(TAG)
        chatRoomWebImpl.reportSomeone(roomId, 10002, 800, "測試檢舉用")
            .logResponse(TAG)
        chatRoomWebImpl.getAllReport(roomId).logResponse(TAG)

        // 如果有需要可以添加
//        chatRoomWebImpl.deleteMessage( roomId, messageId)
    }

    companion object {
        private const val TAG = "ChatRoomServiceCase"
    }
}