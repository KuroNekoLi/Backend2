package com.cmoney.backend2.chat.service

import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.chat.service.api.chatroomsetting.request.ChatRoomSettingUpdateProperties
import com.cmoney.backend2.chat.service.api.chatroomsetting.response.ChatRoomSettingResponseBody
import com.cmoney.backend2.chat.service.api.getallreport.request.ReportInfo
import com.cmoney.backend2.chat.service.api.gethistorymessage.request.HistroyMessageRequestParam
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.Message
import com.cmoney.backend2.chat.service.api.getsubjectallrequests.response.AuthorizationRequestResponseBody
import com.cmoney.backend2.chat.service.api.getuserprofile.response.UserProfileResponseBody
import com.cmoney.backend2.chat.service.api.reportsomeone.request.ReportSomeone
import com.cmoney.backend2.chat.service.api.updateuserprofile.request.UpdateUserProfile
import com.cmoney.backend2.chat.service.api.updateuserprofile.response.UpdateUserProfileResponseBody
import com.cmoney.backend2.chat.service.api.variable.Role
import com.cmoney.backend2.chat.service.api.variable.RuleSet
import com.cmoney.backend2.chat.service.api.variable.Subject
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import java.io.File

class ChatRoomWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: ChatRoomService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ChatRoomWeb {

    override suspend fun getUserProfile(
        domain: String,
        url: String
    ): Result<UserProfileResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getUserProfileSelf(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getUserCurrentSubjectRoles(
        subject: Subject,
        subjectId: Long,
        domain: String,
        url: String
    ): Result<List<String>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getUserCurrentSubjectRoles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(gson)
                .mapNotNull { value -> value }
        }
    }

    override suspend fun lookUpSubjectAllRoles(
        subject: Subject,
        subjectId: Long,
        domain: String,
        url: String
    ): Result<Map<String, List<String>>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.lookUpSubjectAllRoles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .mapValues {
                    it.value.mapNotNull { value ->
                        value
                    }
                }
        }
    }

    override suspend fun updateProfile(
        name: String,
        imageUrl: String,
        domain: String,
        url: String
    ): Result<UpdateUserProfileResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.updateUserProfile(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                userProfile = UpdateUserProfile(name, imageUrl)
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun bindUserProfileRuleSet(
        target: String,
        userId: Long,
        ruleSetId: Long,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.bindRuleSet(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun addRole(
        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.addRole(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun deleteRole(
        subject: Subject,
        subjectId: Long,
        userId: Long,
        role: Role,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.deleteRole(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getSubjectAllAuthorizationRequests(
        subject: Subject,
        subjectId: Long,
        domain: String,
        url: String
    ): Result<List<AuthorizationRequestResponseBody>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getSubjectAllAuthorizationRequests(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getBindingSubjectRuleSets(
        subject: Subject,
        subjectId: Long,
        domain: String,
        url: String
    ): Result<List<RuleSet>> = withContext(dispatcher.io()) {
        runCatching {
            service.getBindingSubjectRuleSets(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getAvailableRooms(
        domain: String,
        url: String
    ): Result<List<ChatRoomSettingResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getAvailableRoom(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull { it }
        }
    }

    override suspend fun getTargetRoom(
        id: Long,
        domain: String,
        url: String
    ): Result<ChatRoomSettingResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getTargetRoomSetting(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkResponseBody(gson)
        }
    }

    override suspend fun updateTargetRoom(
        id: Long,
        updateProperties: ChatRoomSettingUpdateProperties,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.updateChatRoomSetting(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                updateProperties = updateProperties
            ).checkResponseBody(gson)
            Unit
        }
    }

    override suspend fun joinChatRoom(
        roomId: Long,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.requestJoinChatRoom(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        runCatching {
            service.getHistoryMessageLatest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestParam = HistroyMessageRequestParam(count = fetchCount).putQueryMap(
                    mutableMapOf()
                )
            )
                .checkResponseBody(gson)
                .mapNotNull { rawMessage ->
                    rawMessage?.asReal()
                }
        }
    }

    override suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean,
        domain: String,
        url: String
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val requestParam = if (isStart) {
                HistroyMessageRequestParam(count = fetchCount, startTime = time).putQueryMap(
                    mutableMapOf()
                )
            } else {
                HistroyMessageRequestParam(count = fetchCount, endTime = time).putQueryMap(
                    mutableMapOf()
                )
            }
            val response = service.getHistoryMessageLatest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestParam = requestParam
            )
            response.checkResponseBody(gson)
                .mapNotNull { rawMessage ->
                    rawMessage?.asReal()
                }
        }
    }

    override suspend fun getHistoryMessageFromLatest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long,
        domain: String,
        url: String
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        runCatching {
            val param = HistroyMessageRequestParam(
                count = fetchCount,
                startTime = startTime,
                endTime = endTime
            )
            val response = service.getHistoryMessageLatest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestParam = param.putQueryMap(mutableMapOf())
            )
            response.checkResponseBody(gson)
                .mapNotNull { rawMessage ->
                    rawMessage?.asReal()
                }
        }
    }

    override suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getHistoryMessageOldest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestParam = HistroyMessageRequestParam(count = fetchCount).putQueryMap(
                    mutableMapOf()
                )
            )
            response.checkResponseBody(gson)
                .mapNotNull { rawMessage ->
                    rawMessage?.asReal()
                }
        }
    }

    override suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        time: Long,
        isStart: Boolean,
        domain: String,
        url: String
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        runCatching {
            val requestParam = if (isStart) {
                HistroyMessageRequestParam(count = fetchCount, startTime = time).putQueryMap(
                    mutableMapOf()
                )
            } else {
                HistroyMessageRequestParam(count = fetchCount, endTime = time).putQueryMap(
                    mutableMapOf()
                )
            }
            val response = service.getHistoryMessageOldest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestParam = requestParam
            )
            response.checkResponseBody(gson)
                .mapNotNull { rawMessage ->
                    rawMessage?.asReal()
                }
        }
    }

    override suspend fun getHistoryMessageFromOldest(
        roomId: Long,
        fetchCount: Int,
        startTime: Long,
        endTime: Long,
        domain: String,
        url: String
    ): Result<List<Message>> = withContext(dispatcher.io()) {
        runCatching {
            val requestParam = HistroyMessageRequestParam(
                count = fetchCount,
                startTime = startTime,
                endTime = endTime
            )
            service.getHistoryMessageOldest(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestParam = requestParam.putQueryMap(mutableMapOf())
            )
                .checkResponseBody(gson)
                .mapNotNull { rawMessage ->
                    rawMessage?.asReal()
                }
        }
    }

    override suspend fun getMessageById(id: Long, domain: String, url: String): Result<Message> =
        withContext(dispatcher.io()) {
            runCatching {
                val message = service.getMessageById(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkResponseBody(gson)
                    .asReal()
                requireNotNull(message)
            }
        }

    override suspend fun deleteMessage(
        roomId: Long,
        id: Long,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.deleteMessage(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun uploadImage(
        photoFile: File,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val requestFile = photoFile.asRequestBody("multipart/form-data".toMediaType())
            val body =
                MultipartBody.Part.createFormData("formFile", photoFile.name, requestFile)
            service.uploadImage(
                url = url,
                formFile = body
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getAllUser(
        chatRoomId: Long,
        domain: String,
        url: String
    ): Result<List<Long>> = withContext(dispatcher.io()) {
        runCatching {
            service.getAllUser(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull {
                    it
                }
        }
    }

    override suspend fun getOnlineUserCount(
        chatRoomId: Long,
        domain: String,
        url: String
    ): Result<Long> = withContext(dispatcher.io()) {
        runCatching {
            service.getOnlineUserCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
        }
    }

    override suspend fun getTargetUserProfile(
        vararg userId: Long,
        domain: String,
        url: String
    ): Result<List<UserProfileResponseBody>> = withContext(dispatcher.io()) {
        runCatching {
            service.getUserProfile(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .mapNotNull { it }
        }
    }

    override suspend fun reportSomeone(
        chatRoomId: Long,
        userId: Long,
        messageId: Long,
        description: String,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.reportSomeone(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ReportSomeone(
                    description = description,
                    messageId = messageId,
                    userId = userId
                )
            ).checkIsSuccessful()
                .requireBody()

        }
    }

    override suspend fun getAllReport(
        chatRoomId: Long,
        domain: String,
        url: String
    ): Result<List<ReportInfo>> = withContext(dispatcher.io()) {
        runCatching {
            service.getAllReport(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
            ).checkIsSuccessful()
                .requireBody()

        }
    }

    override suspend fun deleteReport(
        roomId: Long,
        reportId: Long,
        domain: String,
        url: String
    ): Result<ResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.deleteReport(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                id = reportId
            ).checkIsSuccessful()
                .requireBody()
        }
    }
}