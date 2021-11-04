package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.base.di.BACKEND2_SETTING
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.chat.service.ChatRoomWeb
import com.cmoney.backend2.chat.service.api.variable.Subject
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class ChatRoomServiceCase(
    private val roomId: Long = 1,
    private val chatUrl: String = "http://192.168.10.103/",
    private val serverUrl: String = "http://192.168.10.187/"
) : ServiceCase {

    private val chatRoomWebImpl by inject<ChatRoomWeb>()
    private val setting by inject<Setting>(BACKEND2_SETTING)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun testAll() {
        setDomain(chatUrl)
        chatRoomWebImpl.getAvailableRooms().logResponse(TAG)
        chatRoomWebImpl.getAllUser(roomId).logResponse(TAG)
        chatRoomWebImpl.getOnlineUserCount(roomId).logResponse(TAG)
        val userProfileResponseBody = chatRoomWebImpl.getUserProfile().getOrElse {
            it.printStackTrace()
            return
        }
        setDomain(serverUrl)
        setDomain(chatUrl)
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

    private fun setDomain(domain: String) {
        setting.domainUrl = domain
    }

    companion object {
        private const val TAG = "ChatRoomServiceCase"
    }
}