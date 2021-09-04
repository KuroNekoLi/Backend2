package com.cmoney.backend2.chat.service

import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.chat.service.api.chatroomsetting.response.ChatRoomSettingResponseBody
import com.cmoney.backend2.chat.service.api.getallreport.request.ReportInfo
import com.cmoney.backend2.chat.service.api.gethistorymessage.request.HistroyMessageRequestParam
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.Message
import com.cmoney.backend2.chat.service.api.getuserprofile.response.UserProfileResponseBody
import com.cmoney.backend2.chat.service.api.reportsomeone.request.ReportSomeone
import com.cmoney.backend2.chat.service.api.updateuserprofile.request.UpdateUserProfile
import com.cmoney.backend2.chat.service.api.updateuserprofile.response.UpdateUserProfileResponseBody
import com.cmoney.backend2.chat.service.api.variable.Role
import com.cmoney.backend2.chat.service.api.variable.RuleSet
import com.cmoney.backend2.chat.service.api.variable.Subject
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

class ChatRoomWebImpl(
    private val chatRoomService: ChatRoomService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : ChatRoomWeb {

    override suspend fun getUserCurrentSubjectRoles(
        subject: Subject,
        subjectId: Long
    ) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getUserCurrentSubjectRoles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                subject = subject.subjectName,
                subjectId = subjectId
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull { value -> value }
        }
    }

    override suspend fun lookUpSubjectAllRoles(
        subject: Subject,
        subjectId: Long
    ): Result<Map<String, List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.lookUpSubjectAllRoles(
                setting.accessToken.createAuthorizationBearer(),
                subject.subjectName,
                subjectId
            ).checkIsSuccessful()
                .requireBody()
                .mapValues {
                    it.value.mapNotNull { value ->
                        value
                    }
                }
        }
    }

    override suspend fun bindUserProfileRuleSet(
        target: String,
        userId: Long,
        ruleSetId: Long
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.bindRuleSet(
                setting.accessToken.createAuthorizationBearer(),
                target,
                userId,
                ruleSetId
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun addRole(
        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.addRole(
                setting.accessToken.createAuthorizationBearer(),
                subject.subjectName,
                subjectId,
                userId,
                role.definitionName
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun deleteRole(
        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.deleteRole(
                setting.accessToken.createAuthorizationBearer(),
                subject.subjectName,
                subjectId,
                userId,
                role.definitionName
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getSubjectAllAuthorizationRequests(
        subject: Subject,
        subjectId: Long
    ) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getSubjectAllAuthorizationRequests(
                setting.accessToken.createAuthorizationBearer(),
                subject.subjectName,
                subjectId
            )
                .checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getBindingSubjectRuleSets(
        subject: Subject,
        subjectId: Long
    ): Result<List<RuleSet>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getBindingSubjectRuleSets(
                setting.accessToken.createAuthorizationBearer(),
                subject.subjectName,
                subjectId
            )
                .checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getUserProfile(): Result<UserProfileResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                chatRoomService.getUserProfileSelf(
                    setting.accessToken.createAuthorizationBearer()
                )
                    .checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun updateProfile(
        name: String,
        imageUrl: String
    ): Result<UpdateUserProfileResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.updateUserProfile(
                setting.accessToken.createAuthorizationBearer(),
                UpdateUserProfile(name, imageUrl)
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun joinChatRoom(roomId: Long): Result<ResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                chatRoomService.requestJoinChatRoom(
                    setting.accessToken.createAuthorizationBearer(),
                    Subject.CHAT_ROOM.subjectName,
                    roomId,
                    Role.USER.definitionName
                )
                    .checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun getAvailableRooms(): Result<List<ChatRoomSettingResponseBody>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                chatRoomService.getAvailableRoom(
                    setting.accessToken.createAuthorizationBearer()
                ).checkIsSuccessful()
                    .requireBody()
                    .mapNotNull { it }
            }
        }

    override suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getHistoryMessageLatest(
                setting.accessToken.createAuthorizationBearer(),
                roomId,
                HistroyMessageRequestParam(count = fetchCount).putQueryMap(mutableMapOf())
            )
                .checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = if (isStart) {
                chatRoomService.getHistoryMessageLatest(
                    setting.accessToken.createAuthorizationBearer(),
                    roomId,
                    HistroyMessageRequestParam(count = fetchCount, startTime = time).putQueryMap(
                        mutableMapOf()
                    )
                )
            } else {
                chatRoomService.getHistoryMessageLatest(
                    setting.accessToken.createAuthorizationBearer(),
                    roomId,
                    HistroyMessageRequestParam(count = fetchCount, endTime = time).putQueryMap(
                        mutableMapOf()
                    )
                )
            }
            response.checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val param = HistroyMessageRequestParam(
                count = fetchCount,
                startTime = startTime,
                endTime = endTime
            )
            chatRoomService.getHistoryMessageLatest(
                setting.accessToken.createAuthorizationBearer(),
                roomId,
                param.putQueryMap(mutableMapOf())
            )
                .checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getHistoryMessageOldest(
                setting.accessToken.createAuthorizationBearer(),
                roomId,
                HistroyMessageRequestParam(count = fetchCount).putQueryMap(mutableMapOf())
            )
                .checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = if (isStart) {
                chatRoomService.getHistoryMessageOldest(
                    setting.accessToken.createAuthorizationBearer(),
                    roomId,
                    HistroyMessageRequestParam(count = fetchCount, startTime = time).putQueryMap(
                        mutableMapOf()
                    )
                )
            } else {
                chatRoomService.getHistoryMessageOldest(
                    setting.accessToken.createAuthorizationBearer(),
                    roomId,
                    HistroyMessageRequestParam(count = fetchCount, endTime = time).putQueryMap(
                        mutableMapOf()
                    )
                )
            }
            response.checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val param = HistroyMessageRequestParam(
                count = fetchCount,
                startTime = startTime,
                endTime = endTime
            )
            chatRoomService.getHistoryMessageOldest(
                setting.accessToken.createAuthorizationBearer(),
                roomId,
                param.putQueryMap(mutableMapOf())
            )
                .checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getMessageById(id: Long) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getMessageById(
                authorization = setting.accessToken.createAuthorizationBearer(),
                messageId = id
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun deleteMessage(
        roomId: Long,
        id: Long
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.deleteMessage(
                authorization = setting.accessToken.createAuthorizationBearer(),
                roomId = roomId,
                messageId = id
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun uploadImage(photoFile: File): Result<ResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val requestFile = photoFile.asRequestBody("multipart/form-data".toMediaType())
                val body =
                    MultipartBody.Part.createFormData("formFile", photoFile.name, requestFile)
                chatRoomService.uploadImage(body)
                    .checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun getAllUser(chatRoomId: Long): Result<List<Long>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                chatRoomService.getAllUser(
                    setting.accessToken.createAuthorizationBearer(),
                    chatRoomId
                ).checkIsSuccessful()
                    .requireBody()
                    .mapNotNull {
                        it
                    }
            }
        }

    override suspend fun getOnlineUserCount(chatRoomId: Long): Result<Long> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                chatRoomService.getOnlineUserCount(
                    setting.accessToken.createAuthorizationBearer(),
                    chatRoomId
                )
                    .checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun getTargetUserProfile(
        vararg userId: Long
    ): Result<List<UserProfileResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.getUserProfile(
                setting.accessToken.createAuthorizationBearer(),
                userId.joinToString(",")
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull { it }
        }
    }

    override suspend fun reportSomeone(
        chatRoomId: Long,
        userId: Long,
        messageId: Long,
        description: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.reportSomeone(
                setting.accessToken.createAuthorizationBearer(),
                chatRoomId,
                ReportSomeone(
                    description = description,
                    messageId = messageId,
                    userId = userId
                )
            )
                .checkIsSuccessful()
                .requireBody()

        }
    }

    override suspend fun getAllReport(chatRoomId: Long): Result<List<ReportInfo>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                chatRoomService.getAllReport(
                    setting.accessToken.createAuthorizationBearer(),
                    chatRoomId
                )
                    .checkIsSuccessful()
                    .requireBody()

            }
        }

    override suspend fun deleteReport(
        roomId: Long,
        reportId: Long
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            chatRoomService.deleteReport(
                setting.accessToken.createAuthorizationBearer(),
                roomId = roomId,
                id = reportId
            )
                .checkIsSuccessful()
                .requireBody()
        }
    }
}