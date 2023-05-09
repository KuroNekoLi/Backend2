package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.api.variable.Subject
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class ChatRoomServiceCase(
    private val roomId: Long = 1
) : ServiceCase {

    private val chatRoomWeb by inject<ChatRoomWeb>()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun testAll() {
        val userProfileResponseBody = chatRoomWeb.getUserProfile().getOrElse {
            it.printStackTrace()
            return
        }
        chatRoomWeb.getUserCurrentSubjectRoles(Subject.CHAT_ROOM, roomId)
            .logResponse(TAG)
//        chatRoomWeb.getAvailableRooms().logResponse(TAG)
//        chatRoomWeb.updateTargetRoom(roomId, ChatRoomSettingUpdateProperties(description = "", announcement = listOf()))
//        chatRoomWeb.getTargetRoom(roomId).logResponse(TAG)
//        chatRoomWeb.getAllUser(roomId).logResponse(TAG)
//        chatRoomWeb.getOnlineUserCount(roomId).logResponse(TAG)
//        val userProfileResponseBody = chatRoomWeb.getUserProfile().getOrElse {
//            it.printStackTrace()
//            return
//        }
//        chatRoomWeb.updateProfile(
//            "New nick name", "New image path"
//        )
//            .logResponse(TAG)
//        val userId = userProfileResponseBody.id ?: return
//        chatRoomWeb.getBindingSubjectRuleSets(Subject.USER_PROFILE, userId)
//            .logResponse(TAG)

//        chatRoomWeb.getUserCurrentSubjectRoles(Subject.CHAT_ROOM, roomId)
//            .logResponse(TAG)
//        chatRoomWeb.lookUpSubjectAllRoles(Subject.CHAT_ROOM, roomId)
//            .logResponse(TAG)
//        chatRoomWeb.getHistoryMessageFromLatest(roomId, 30).logResponse(TAG)
//        chatRoomWeb.getHistoryMessageFromOldest(roomId, 30).logResponse(TAG)
//        chatRoomWeb.getMessageById(1).logResponse(TAG)
//        chatRoomWeb.reportSomeone(roomId, 10002, 800, "測試檢舉用")
//            .logResponse(TAG)
//        chatRoomWeb.getAllReport(roomId).logResponse(TAG)

        // 如果有需要可以添加
//        chatRoomWebImpl.deleteMessage( roomId, messageId)
    }

    companion object {
        private const val TAG = "ChatRoomServiceCase"
    }
}