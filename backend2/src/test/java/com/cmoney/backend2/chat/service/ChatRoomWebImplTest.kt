package com.cmoney.backend2.chat.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.chat.service.api.chatroomsetting.request.ChatRoomSettingUpdateProperties
import com.cmoney.backend2.chat.service.api.chatroomsetting.response.ChatRoomSettingResponseBody
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.RawContent
import com.cmoney.backend2.chat.service.api.gethistorymessage.response.RawMessage
import com.cmoney.backend2.chat.service.api.getuserprofile.response.UserProfileResponseBody
import com.cmoney.backend2.chat.service.api.updateuserprofile.response.UpdateUserProfileResponseBody
import com.cmoney.backend2.chat.service.api.variable.Role
import com.cmoney.backend2.chat.service.api.variable.RuleSet
import com.cmoney.backend2.chat.service.api.variable.Subject
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.reflect.TypeToken
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ChatRoomWebImplTest {

    private val testScope = TestScope()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: ChatRoomService
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: ChatRoomWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = ChatRoomWebImpl(
            gson = gson,
            manager = manager,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getChatRoomSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getUserProfile_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/UserProfile/"
        val urlSlot = slot<String>()
        val responseBody = UserProfileResponseBody(
            id = null, properties = null
        )
        coEvery {
            service.getUserProfileSelf(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(responseBody)
        web.getUserProfile()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUserProfile_success() = testScope.runTest {
        val responseBody = UserProfileResponseBody(
            id = 1, properties = null
        )
        coEvery {
            service.getUserProfileSelf(
                url = any(),
                authorization = any(),
            )
        } returns Response.success(responseBody)

        val result = web.getUserProfile()
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.id).isEqualTo(1)
        Truth.assertThat(data.properties).isNull()
    }

    @Test(expected = ServerException::class)
    fun getUserProfile_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getUserProfileSelf(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())

        val result = web.getUserProfile()
        result.getOrThrow()
    }

    @Test
    fun `getUserCurrentSubjectRoles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Role/Current/0"
        val urlSlot = slot<String>()
        coEvery {
            service.getUserCurrentSubjectRoles(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(emptyList())
        web.getUserCurrentSubjectRoles(subject = Subject.CHAT_ROOM, subjectId = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUserCurrentSubjectRoles_success() = testScope.runTest {
        val subject = Subject.CHAT_ROOM
        val subjectId = 0L
        coEvery {
            service.getUserCurrentSubjectRoles(
                url = any(),
                authorization = any(),
            )
        } returns Response.success(listOf("visit", "user"))
        val result = web.getUserCurrentSubjectRoles(subject = subject, subjectId = subjectId)
            .getOrThrow()
        Truth.assertThat(result).isEqualTo(listOf("visit", "user"))
    }

    @Test(expected = ServerException::class)
    fun getUserCurrentSubjectRoles_failure_ServerException() = testScope.runTest {
        val subject = Subject.CHAT_ROOM
        val subjectId = 0L
        coEvery {
            service.getUserCurrentSubjectRoles(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getUserCurrentSubjectRoles(subject = subject, subjectId = subjectId)
            .getOrThrow()
    }

    @Test
    fun `lookUpSubjectAllRoles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Role/Lookup/0"
        val urlSlot = slot<String>()
        coEvery {
            service.lookUpSubjectAllRoles(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(emptyMap())
        web.lookUpSubjectAllRoles(subject = Subject.CHAT_ROOM, subjectId = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun lookUpSubjectAllRoles_success() = testScope.runTest {
        val responseJson = "{\"1\":[\"visit\"],\"3\":[\"dev\",\"@Owner\",\"user\"]}"
        val responseBody = gson.fromJson<Map<String, List<String>>>(
            responseJson,
            object : TypeToken<Map<String, List<String>>>() {}.type
        )
        val subject = Subject.CHAT_ROOM
        val subjectId = 0L
        coEvery {
            service.lookUpSubjectAllRoles(
                url = any(),
                authorization = any(),
            )
        } returns Response.success(responseBody)
        val result = web.lookUpSubjectAllRoles(subject = subject, subjectId = subjectId)
            .getOrThrow()
        Truth.assertThat(result)
            .isEqualTo(mapOf("1" to listOf("visit"), "3" to listOf("dev", "@Owner", "user")))
    }

    @Test(expected = ServerException::class)
    fun lookUpSubjectAllRoles_failure_ServerException() = testScope.runTest {
        val subject = Subject.CHAT_ROOM
        val subjectId = 0L
        coEvery {
            service.lookUpSubjectAllRoles(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.lookUpSubjectAllRoles(subject = subject, subjectId = subjectId)
            .getOrThrow()
    }

    @Test
    fun `updateProfile_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/UserProfile"
        val urlSlot = slot<String>()
        coEvery {
            service.updateUserProfile(
                url = capture(urlSlot),
                authorization = any(),
                userProfile = any(),
            )
        } returns Response.success(
            UpdateUserProfileResponseBody(
                userName = null,
                userImageUrl = null
            )
        )
        web.updateProfile(name = "", imageUrl = "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateProfile_success() = testScope.runTest {
        val responseBodyJson =
            "{\"userImageUrl\":\"New image path\",\"userName\":\"New nick name\",\"hasSetRole\":true,\"hasSetRule\":true}"
        val responseBody =
            gson.fromJson(responseBodyJson, UpdateUserProfileResponseBody::class.java)
        coEvery {
            service.updateUserProfile(
                url = any(),
                authorization = any(),
                userProfile = any(),
            )
        } returns Response.success(responseBody)
        val result = web.updateProfile(name = "", imageUrl = "")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun updateProfile_failure_ServerException() = testScope.runTest {
        coEvery {
            service.updateUserProfile(
                url = any(),
                authorization = any(),
                userProfile = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.updateProfile(name = "", imageUrl = "").getOrThrow()
    }

    @Test
    fun `bindUserProfileRuleSet_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Binding/0/0"
        val urlSlot = slot<String>()
        coEvery {
            service.bindRuleSet(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success("{}".toResponseBody())
        web.bindUserProfileRuleSet(
            target = Subject.CHAT_ROOM.subjectName,
            userId = 0,
            ruleSetId = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun bindUserProfileRuleSet_success() = testScope.runTest {
        val ruleSetId = 0L
        coEvery {
            service.bindRuleSet(
                url = any(),
                authorization = any()
            )
        } returns Response.success("[]".toResponseBody())
        val result = web.bindUserProfileRuleSet(
            target = Subject.CHAT_ROOM.subjectName,
            userId = 0,
            ruleSetId = ruleSetId
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun bindUserProfileRuleSet_failure_ServerException() = testScope.runTest {
        val ruleSetId = 0L

        coEvery {
            service.bindRuleSet(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.bindUserProfileRuleSet(
            target = Subject.CHAT_ROOM.subjectName,
            userId = 0,
            ruleSetId = ruleSetId
        )
            .getOrThrow()
    }

    @Test
    fun `addRole_check url`() = testScope.runTest {
        val roles = mapOf(
            Role.OWNER to "@owner",
            Role.LIMIT to "limit",
            Role.VISIT to "visit",
            Role.USER to "user",
            Role.SUPERVISOR to "supervisor",
            Role.ADMIN to "admin",
            Role.DEV to "dev"
        )
        roles.forEach { (role, definitionName) ->
            val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Role/1/0/${definitionName}"
            val urlSlot = slot<String>()
            coEvery {
                service.addRole(
                    url = capture(urlSlot),
                    authorization = any(),
                )
            } returns Response.success("{}".toResponseBody())
            web.addRole(subject = Subject.CHAT_ROOM, subjectId = 1, userId = 0, role = role)
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }
    }

    @Test
    fun addRole_success() = testScope.runTest {
        coEvery {
            service.addRole(
                url = any(),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.addRole(subject = Subject.CHAT_ROOM, subjectId = 1, userId = 0, role = Role.USER)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun addRole_failure_ServerException() = testScope.runTest {
        coEvery {
            service.addRole(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.addRole(subject = Subject.CHAT_ROOM, subjectId = 1, userId = 0, role = Role.USER)
            .getOrThrow()
    }

    @Test
    fun `deleteRole_check url`() = testScope.runTest {
        val roles = mapOf(
            Role.OWNER to "@owner",
            Role.LIMIT to "limit",
            Role.VISIT to "visit",
            Role.USER to "user",
            Role.SUPERVISOR to "supervisor",
            Role.ADMIN to "admin",
            Role.DEV to "dev"
        )
        roles.forEach { (role, definitionName) ->
            val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Role/1/0/${definitionName}"
            val urlSlot = slot<String>()
            coEvery {
                service.deleteRole(
                    url = capture(urlSlot),
                    authorization = any(),
                )
            } returns Response.success("{}".toResponseBody())
            web.deleteRole(subject = Subject.CHAT_ROOM, subjectId = 1, userId = 0, role = role)
            Truth.assertThat(urlSlot.captured).isEqualTo(expect)
        }
    }

    @Test
    fun deleteRole_success() = testScope.runTest {
        coEvery {
            service.deleteRole(
                url = any(),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.deleteRole(subject = Subject.CHAT_ROOM, subjectId = 1, userId = 0, role = Role.USER)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun deleteRole_failure_ServerException() = testScope.runTest {
        coEvery {
            service.deleteRole(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.deleteRole(subject = Subject.CHAT_ROOM, subjectId = 1, userId = 0, role = Role.USER)
            .getOrThrow()
    }

    @Test
    fun `getSubjectAllAuthorizationRequests_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Request/1"
        val urlSlot = slot<String>()
        coEvery {
            service.getSubjectAllAuthorizationRequests(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(emptyList())
        web.getSubjectAllAuthorizationRequests(subject = Subject.CHAT_ROOM, subjectId = 1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSubjectAllAuthorizationRequests_success() = testScope.runTest {
        coEvery {
            service.getSubjectAllAuthorizationRequests(
                url = any(),
                authorization = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getSubjectAllAuthorizationRequests(subject = Subject.CHAT_ROOM, subjectId = 1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getSubjectAllAuthorizationRequests_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getSubjectAllAuthorizationRequests(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getSubjectAllAuthorizationRequests(subject = Subject.CHAT_ROOM, subjectId = 1)
            .getOrThrow()
    }

    @Test
    fun `getBindingSubjectRuleSets_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Binding/1"
        val urlSlot = slot<String>()
        coEvery {
            service.getBindingSubjectRuleSets(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(emptyList())
        web.getBindingSubjectRuleSets(subject = Subject.CHAT_ROOM, subjectId = 1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBindingSubjectRuleSets_success() = testScope.runTest {
        val responseBodyJson =
            "[{\"id\":4,\"rules\":[{\"name\":\"Chatroom.Messages.Read\",\"role\":\"dev\",\"properties\":{\"Allow\":true}}]}]"
        val responseBody = gson.fromJson<List<RuleSet>>(
            responseBodyJson,
            object : TypeToken<List<RuleSet>>() {}.type
        )
        coEvery {
            service.getBindingSubjectRuleSets(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result =
            web.getBindingSubjectRuleSets(subject = Subject.CHAT_ROOM, subjectId = 1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getBindingSubjectRuleSets_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getBindingSubjectRuleSets(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getBindingSubjectRuleSets(subject = Subject.CHAT_ROOM, subjectId = 1)
            .getOrThrow()
    }

    @Test
    fun `getAvailableRooms_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom"
        val urlSlot = slot<String>()
        coEvery {
            service.getAvailableRoom(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(emptyList())
        web.getAvailableRooms()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAvailableRooms_success() = testScope.runTest {
        val responseBodyJson =
            "[{\"id\":1,\"properties\":{\"announcements\":[],\"@Roles\":[\"visit\"]}},{\"id\":2,\"properties\":{\"@Roles\":[\"visit\"]}},{\"id\":3,\"properties\":{\"name\":\"自由人聊天室\",\"description\":\"\",\"oldroomId\":4466,\"announcements\":[],\"@Roles\":[\"visit\",\"user\"]}}]"
        val responseBody = gson.fromJson<List<ChatRoomSettingResponseBody>>(
            responseBodyJson,
            object : TypeToken<List<ChatRoomSettingResponseBody>>() {}.type
        )
        coEvery {
            service.getAvailableRoom(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result =
            web.getAvailableRooms()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getAvailableRooms_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getAvailableRoom(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getAvailableRooms()
            .getOrThrow()
    }

    @Test
    fun `getTargetRoom_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1"
        val urlSlot = slot<String>()
        val responseBody = ChatRoomSettingResponseBody(id = null, properties = null)
        coEvery {
            service.getTargetRoomSetting(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success(responseBody)
        web.getTargetRoom(id = 1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTargetRoom_success() = testScope.runTest {
        val responseBodyJson =
            "{\"id\":1,\"properties\":{\"announcements\":[],\"@Roles\":[\"visit\"]}}"
        val responseBody = gson.fromJson<ChatRoomSettingResponseBody>(
            responseBodyJson,
            object : TypeToken<ChatRoomSettingResponseBody>() {}.type
        )
        coEvery {
            service.getTargetRoomSetting(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result =
            web.getTargetRoom(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getTargetRoom_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getTargetRoomSetting(
                url = any(),
                authorization = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getTargetRoom(1)
            .getOrThrow()
    }

    @Test
    fun `updateTargetRoom_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1"
        val urlSlot = slot<String>()
        val responseBody = ChatRoomSettingResponseBody(id = null, properties = null)
        coEvery {
            service.updateChatRoomSetting(
                url = capture(urlSlot),
                authorization = any(),
                updateProperties = any()
            )
        } returns Response.success(responseBody)
        web.updateTargetRoom(
            id = 1,
            updateProperties = ChatRoomSettingUpdateProperties(
                description = "",
                announcement = listOf()
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateTargetRoom_success() = testScope.runTest {
        val responseBodyJson =
            "{\"id\":1,\"properties\":{\"announcements\":[],\"@Roles\":[\"visit\"]}}"
        val responseBody = gson.fromJson<ChatRoomSettingResponseBody>(
            responseBodyJson,
            object : TypeToken<ChatRoomSettingResponseBody>() {}.type
        )
        coEvery {
            service.updateChatRoomSetting(
                url = any(),
                authorization = any(),
                updateProperties = any()
            )
        } returns Response.success(responseBody)
        val result =
            web.updateTargetRoom(
                id = 1,
                updateProperties = ChatRoomSettingUpdateProperties(
                    description = "",
                    announcement = listOf()
                )
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun updateTargetRoom_failure_ServerException() = testScope.runTest {
        coEvery {
            service.updateChatRoomSetting(
                url = any(),
                authorization = any(),
                updateProperties = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.updateTargetRoom(
            id = 1,
            updateProperties = ChatRoomSettingUpdateProperties(
                description = "",
                announcement = listOf()
            )
        ).getOrThrow()
    }

    @Test
    fun `joinChatRoom_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Authorization/Chatroom/Request/1/user"
        val urlSlot = slot<String>()
        coEvery {
            service.requestJoinChatRoom(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        web.joinChatRoom(
            roomId = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun joinChatRoom_success() = testScope.runTest {
        coEvery {
            service.requestJoinChatRoom(
                url = any(),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.joinChatRoom(
                roomId = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun joinChatRoom_failure_ServerException() = testScope.runTest {
        coEvery {
            service.requestJoinChatRoom(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.joinChatRoom(
            roomId = 1
        ).getOrThrow()
    }

    @Test
    fun `getHistoryMessageFromLatest_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/Latest"
        val urlSlot = slot<String>()
        coEvery {
            service.getHistoryMessageLatest(
                url = capture(urlSlot),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        web.getHistoryMessageFromLatest(
            roomId = 1,
            fetchCount = 100
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getHistoryMessageFromLatest_success() = testScope.runTest {
        coEvery {
            service.getHistoryMessageLatest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getHistoryMessageFromLatest_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getHistoryMessageLatest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getHistoryMessageFromLatest(
            roomId = 1,
            fetchCount = 100
        ).getOrThrow()
    }

    @Test
    fun `getHistoryMessageFromLatest isStart_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/Latest"
        val urlSlot = slot<String>()
        coEvery {
            service.getHistoryMessageLatest(
                url = capture(urlSlot),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        web.getHistoryMessageFromLatest(
            roomId = 1,
            fetchCount = 100,
            isStart = false,
            time = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getHistoryMessageFromLatest isStart_isStart is true_startTime is 1`() =
        testScope.runTest {
            val requestParamSlot = slot<Map<String, String>>()
            coEvery {
                service.getHistoryMessageLatest(
                    url = any(),
                    authorization = any(),
                    requestParam = capture(requestParamSlot)
                )
            } returns Response.success(emptyList())
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100,
                isStart = true,
                time = 1
            )
            Truth.assertThat(requestParamSlot.captured["count"]).isEqualTo("100")
            Truth.assertThat(requestParamSlot.captured["startTime"]).isEqualTo("1")
        }

    @Test
    fun `getHistoryMessageFromLatest isStart_isStart is false_endTime is 1`() =
        testScope.runTest {
            val requestParamSlot = slot<Map<String, String>>()
            coEvery {
                service.getHistoryMessageLatest(
                    url = any(),
                    authorization = any(),
                    requestParam = capture(requestParamSlot)
                )
            } returns Response.success(emptyList())
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100,
                isStart = false,
                time = 1
            )
            Truth.assertThat(requestParamSlot.captured["count"]).isEqualTo("100")
            Truth.assertThat(requestParamSlot.captured["endTime"]).isEqualTo("1")
        }

    @Test
    fun `getHistoryMessageFromLatest isStart_success`() = testScope.runTest {
        coEvery {
            service.getHistoryMessageLatest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100,
                isStart = false,
                time = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `getHistoryMessageFromLatest isStart_failure_ServerException`() = testScope.runTest {
        coEvery {
            service.getHistoryMessageLatest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getHistoryMessageFromLatest(
            roomId = 1,
            fetchCount = 100,
            isStart = false,
            time = 1
        ).getOrThrow()
    }


    @Test
    fun `getHistoryMessageFromLatest startTime endTime_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/Latest"
        val urlSlot = slot<String>()
        coEvery {
            service.getHistoryMessageLatest(
                url = capture(urlSlot),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        web.getHistoryMessageFromLatest(
            roomId = 1,
            fetchCount = 100,
            startTime = 1,
            endTime = 10
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getHistoryMessageFromLatest startTime endTime_startTime is 1 and endTime is 10_HistoryMessageRequestParam startTime is 1 and endTime is 10`() =
        testScope.runTest {
            val requestParamSlot = slot<Map<String, String>>()
            coEvery {
                service.getHistoryMessageLatest(
                    url = any(),
                    authorization = any(),
                    requestParam = capture(requestParamSlot)
                )
            } returns Response.success(emptyList())
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100,
                startTime = 1,
                endTime = 10
            )
            Truth.assertThat(requestParamSlot.captured["count"]).isEqualTo("100")
            Truth.assertThat(requestParamSlot.captured["startTime"]).isEqualTo("1")
            Truth.assertThat(requestParamSlot.captured["endTime"]).isEqualTo("10")
        }

    @Test
    fun `getHistoryMessageFromLatest startTime endTime_success`() = testScope.runTest {
        coEvery {
            service.getHistoryMessageLatest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100,
                startTime = 1,
                endTime = 10
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `getHistoryMessageFromLatest startTime endTime_failure_ServerException`() =
        testScope.runTest {
            coEvery {
                service.getHistoryMessageLatest(
                    url = any(),
                    authorization = any(),
                    requestParam = any()
                )
            } returns Response.error(400, "{}".toResponseBody())
            web.getHistoryMessageFromLatest(
                roomId = 1,
                fetchCount = 100,
                startTime = 1,
                endTime = 10
            ).getOrThrow()
        }

    @Test
    fun `getHistoryMessageFromOldest_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/Oldest"
        val urlSlot = slot<String>()
        coEvery {
            service.getHistoryMessageOldest(
                url = capture(urlSlot),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        web.getHistoryMessageFromOldest(
            roomId = 1,
            fetchCount = 100
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getHistoryMessageFromOldest_success() = testScope.runTest {
        coEvery {
            service.getHistoryMessageOldest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getHistoryMessageFromOldest_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getHistoryMessageOldest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getHistoryMessageFromOldest(
            roomId = 1,
            fetchCount = 100
        ).getOrThrow()
    }

    @Test
    fun `getHistoryMessageFromOldest isStart_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/Oldest"
        val urlSlot = slot<String>()
        coEvery {
            service.getHistoryMessageOldest(
                url = capture(urlSlot),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        web.getHistoryMessageFromOldest(
            roomId = 1,
            fetchCount = 100,
            isStart = false,
            time = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getHistoryMessageFromOldest isStart_isStart is true_startTime is 1`() =
        testScope.runTest {
            val requestParamSlot = slot<Map<String, String>>()
            coEvery {
                service.getHistoryMessageOldest(
                    url = any(),
                    authorization = any(),
                    requestParam = capture(requestParamSlot)
                )
            } returns Response.success(emptyList())
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100,
                isStart = true,
                time = 1
            )
            Truth.assertThat(requestParamSlot.captured["count"]).isEqualTo("100")
            Truth.assertThat(requestParamSlot.captured["startTime"]).isEqualTo("1")
        }

    @Test
    fun `getHistoryMessageFromOldest isStart_isStart is false_endTime is 1`() =
        testScope.runTest {
            val requestParamSlot = slot<Map<String, String>>()
            coEvery {
                service.getHistoryMessageOldest(
                    url = any(),
                    authorization = any(),
                    requestParam = capture(requestParamSlot)
                )
            } returns Response.success(emptyList())
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100,
                isStart = false,
                time = 1
            )
            Truth.assertThat(requestParamSlot.captured["count"]).isEqualTo("100")
            Truth.assertThat(requestParamSlot.captured["endTime"]).isEqualTo("1")
        }

    @Test
    fun `getHistoryMessageFromOldest isStart_success`() = testScope.runTest {
        coEvery {
            service.getHistoryMessageOldest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100,
                isStart = false,
                time = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `getHistoryMessageFromOldest isStart_failure_ServerException`() = testScope.runTest {
        coEvery {
            service.getHistoryMessageOldest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getHistoryMessageFromOldest(
            roomId = 1,
            fetchCount = 100,
            isStart = false,
            time = 1
        ).getOrThrow()
    }


    @Test
    fun `getHistoryMessageFromOldest startTime endTime_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/Oldest"
        val urlSlot = slot<String>()
        coEvery {
            service.getHistoryMessageOldest(
                url = capture(urlSlot),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        web.getHistoryMessageFromOldest(
            roomId = 1,
            fetchCount = 100,
            startTime = 1,
            endTime = 10
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getHistoryMessageFromOldest startTime endTime_startTime is 1 and endTime is 10_HistoryMessageRequestParam startTime is 1 and endTime is 10`() =
        testScope.runTest {
            val requestParamSlot = slot<Map<String, String>>()
            coEvery {
                service.getHistoryMessageOldest(
                    url = any(),
                    authorization = any(),
                    requestParam = capture(requestParamSlot)
                )
            } returns Response.success(emptyList())
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100,
                startTime = 1,
                endTime = 10
            )
            Truth.assertThat(requestParamSlot.captured["count"]).isEqualTo("100")
            Truth.assertThat(requestParamSlot.captured["startTime"]).isEqualTo("1")
            Truth.assertThat(requestParamSlot.captured["endTime"]).isEqualTo("10")
        }

    @Test
    fun `getHistoryMessageFromOldest startTime endTime_success`() = testScope.runTest {
        coEvery {
            service.getHistoryMessageOldest(
                url = any(),
                authorization = any(),
                requestParam = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100,
                startTime = 1,
                endTime = 10
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun `getHistoryMessageFromOldest startTime endTime_failure_ServerException`() =
        testScope.runTest {
            coEvery {
                service.getHistoryMessageOldest(
                    url = any(),
                    authorization = any(),
                    requestParam = any()
                )
            } returns Response.error(400, "{}".toResponseBody())
            web.getHistoryMessageFromOldest(
                roomId = 1,
                fetchCount = 100,
                startTime = 1,
                endTime = 10
            ).getOrThrow()
        }

    @Test
    fun `getMessageById_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/Message/1"
        val urlSlot = slot<String>()
        val message = RawMessage(
            chatroomId = null,
            content = null,
            id = null,
            senderId = null,
            state = null,
            timestamp = null,
            type = null
        )
        coEvery {
            service.getMessageById(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(message)
        web.getMessageById(
            id = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMessageById_success() = testScope.runTest {
        val message = RawMessage(
            chatroomId = 1,
            content = RawContent(
                originalContentUrl = null,
                previewImageUrl = null,
                width = null,
                height = null,
                text = null,
                packageId = null,
                stickerId = null,
                reason = null,
                messageId = null,
                exception = null,
                originalMessage = null,
                destination = null,
                type = "@Delete",
                content = null
            ),
            id = 1,
            senderId = null,
            state = null,
            timestamp = null,
            type = "@Delete"
        )
        coEvery {
            service.getMessageById(
                url = any(),
                authorization = any()
            )
        } returns Response.success(message)
        val result =
            web.getMessageById(
                id = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getMessageById_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getMessageById(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getMessageById(
            id = 1
        ).getOrThrow()
    }

    @Test
    fun `deleteMessage_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Message/1"
        val urlSlot = slot<String>()
        coEvery {
            service.deleteMessage(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        web.deleteMessage(
            roomId = 1,
            id = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteMessage_success() = testScope.runTest {
        coEvery {
            service.deleteMessage(
                url = any(),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.deleteMessage(
                roomId = 1,
                id = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun deleteMessage_failure_ServerException() = testScope.runTest {
        coEvery {
            service.deleteMessage(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.deleteMessage(
            roomId = 1,
            id = 1
        ).getOrThrow()
    }

    @Test
    fun `uploadImage_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Image/Upload"
        val urlSlot = slot<String>()
        coEvery {
            service.uploadImage(
                url = capture(urlSlot),
                formFile = any(),
            )
        } returns Response.success("{}".toResponseBody())
        web.uploadImage(
            photoFile = File(""),
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `uploadImage_input RequestBody's MediaType_MediaType is multipart-form-data`() =
        testScope.runTest {
            val expect = "multipart/form-data".toMediaType()
            val slot = slot<MultipartBody.Part>()
            coEvery {
                service.uploadImage(
                    url = any(),
                    formFile = capture(slot)
                )
            } returns Response.success("{}".toResponseBody())
            web.uploadImage(
                photoFile = File("")
            )
            Truth.assertThat(slot.captured.body.contentType()).isEqualTo(expect)
        }

    @Test
    fun uploadImage_success() = testScope.runTest {
        coEvery {
            service.uploadImage(
                url = any(),
                formFile = any(),
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.uploadImage(
                photoFile = File(""),
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun uploadImage_failure_ServerException() = testScope.runTest {
        coEvery {
            service.uploadImage(
                url = any(),
                formFile = any(),
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.uploadImage(
            photoFile = File(""),
        ).getOrThrow()
    }

    @Test
    fun `getAllUser_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Statistic/User"
        val urlSlot = slot<String>()
        coEvery {
            service.getAllUser(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(emptyList())
        web.getAllUser(
            chatRoomId = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAllUser_success() = testScope.runTest {
        coEvery {
            service.getAllUser(
                url = any(),
                authorization = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getAllUser(
                chatRoomId = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getAllUser_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getAllUser(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getAllUser(
            chatRoomId = 1
        ).getOrThrow()
    }

    @Test
    fun `getOnlineUserCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Statistic/OnlineUserCount"
        val urlSlot = slot<String>()
        coEvery {
            service.getOnlineUserCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(1)
        web.getOnlineUserCount(
            chatRoomId = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getOnlineUserCount_success() = testScope.runTest {
        coEvery {
            service.getOnlineUserCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success(1)
        val result =
            web.getOnlineUserCount(
                chatRoomId = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getOnlineUserCount_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getOnlineUserCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getOnlineUserCount(
            chatRoomId = 1
        ).getOrThrow()
    }

    @Test
    fun `getTargetUserProfile_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/UserProfile/1,2,3"
        val urlSlot = slot<String>()
        coEvery {
            service.getUserProfile(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(emptyList())
        web.getTargetUserProfile(
            userId = longArrayOf(1, 2, 3)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTargetUserProfile_success() = testScope.runTest {
        coEvery {
            service.getUserProfile(
                url = any(),
                authorization = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getTargetUserProfile(
                userId = longArrayOf(1, 2, 3)
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getTargetUserProfile_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getUserProfile(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getTargetUserProfile(
            userId = longArrayOf(1, 2, 3)
        ).getOrThrow()
    }

    @Test
    fun `reportSomeone_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Report"
        val urlSlot = slot<String>()
        coEvery {
            service.reportSomeone(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success("{}".toResponseBody())
        web.reportSomeone(
            chatRoomId = 1, userId = 0, messageId = 0, description = ""
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun reportSomeone_success() = testScope.runTest {
        coEvery {
            service.reportSomeone(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.reportSomeone(
                chatRoomId = 1, userId = 0, messageId = 0, description = ""
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun reportSomeone_failure_ServerException() = testScope.runTest {
        coEvery {
            service.reportSomeone(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.reportSomeone(
            chatRoomId = 1, userId = 0, messageId = 0, description = ""
        ).getOrThrow()
    }

    @Test
    fun `getAllReport_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Report"
        val urlSlot = slot<String>()
        coEvery {
            service.getAllReport(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(emptyList())
        web.getAllReport(
            chatRoomId = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAllReport_success() = testScope.runTest {
        coEvery {
            service.getAllReport(
                url = any(),
                authorization = any()
            )
        } returns Response.success(emptyList())
        val result =
            web.getAllReport(
                chatRoomId = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun getAllReport_failure_ServerException() = testScope.runTest {
        coEvery {
            service.getAllReport(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.getAllReport(
            chatRoomId = 1
        ).getOrThrow()
    }

    @Test
    fun `deleteReport_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}api/Chatroom/1/Report"
        val urlSlot = slot<String>()
        coEvery {
            service.deleteReport(
                url = capture(urlSlot),
                authorization = any(),
                id = any()
            )
        } returns Response.success("{}".toResponseBody())
        web.deleteReport(
            roomId = 1,
            reportId = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteReport_success() = testScope.runTest {
        coEvery {
            service.deleteReport(
                url = any(),
                authorization = any(),
                id = any()
            )
        } returns Response.success("{}".toResponseBody())
        val result =
            web.deleteReport(
                roomId = 1,
                reportId = 1
            )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun deleteReport_failure_ServerException() = testScope.runTest {
        coEvery {
            service.deleteReport(
                url = any(),
                authorization = any(),
                id = any()
            )
        } returns Response.error(400, "{}".toResponseBody())
        web.deleteReport(
            roomId = 1,
            reportId = 1
        ).getOrThrow()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }

}