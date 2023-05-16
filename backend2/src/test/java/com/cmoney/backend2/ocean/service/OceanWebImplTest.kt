package com.cmoney.backend2.ocean.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.ocean.service.api.answers.AnswersResultWithError
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.changememberstatus.Operation
import com.cmoney.backend2.ocean.service.api.channelquestions.ChannelQuestionnaire
import com.cmoney.backend2.ocean.service.api.channelquestionsactivation.ChannelQuestionsActivationResponseWithError
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubResponseWithError
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.club.AnnouncementListResponseWithError
import com.cmoney.backend2.ocean.service.api.club.IsCreateOrUpdateSuccessResponseWithError
import com.cmoney.backend2.ocean.service.api.club.IsRemoveAnnouncementSuccessWithError
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getblacklist.GetBlackListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getblocklist.GetBlockListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getchannellatestarticle.GetChannelLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getchannelquestions.GetChannelQuestionsResponseWithError
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubResponseBody
import com.cmoney.backend2.ocean.service.api.getmanagerlist.GetManagerListResponseWithError
import com.cmoney.backend2.ocean.service.api.getmasters.GetMastersResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getmasters.MasterType
import com.cmoney.backend2.ocean.service.api.getmemberclubs.GetMemberClubsResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getmemberstatuslist.GetMemberStatusListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getnotify.GetNotifyResponseBody
import com.cmoney.backend2.ocean.service.api.getnotify.Notify
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.GetRecommendClubsResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.RecommendClubsNeedInfo
import com.cmoney.backend2.ocean.service.api.getrelevantcomments.GetRelevantCommentsResponseWithError
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoResponseBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstocklatestarticle.GetStockLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstockpopulararticle.GetStockPopularArticleResponse
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountResponseBody
import com.cmoney.backend2.ocean.service.api.hadphoneauthentication.HadPhoneAuthResponse
import com.cmoney.backend2.ocean.service.api.invite.InviteResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelResponseBody
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.uploadchannelimage.UploadChannelImageResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.variable.Article
import com.cmoney.backend2.ocean.service.api.variable.ArticleNeedInfo
import com.cmoney.backend2.ocean.service.api.variable.ArticleResult
import com.cmoney.backend2.ocean.service.api.variable.ChannelInfoOption
import com.cmoney.backend2.ocean.service.api.variable.ChannelNeedInfo
import com.cmoney.backend2.ocean.service.api.variable.ChannelTypes
import com.cmoney.backend2.ocean.service.api.variable.Comment
import com.cmoney.backend2.ocean.service.api.variable.FilterType
import com.cmoney.backend2.ocean.service.api.variable.MemberPosition
import com.cmoney.backend2.ocean.service.api.variable.NotificationType
import com.cmoney.backend2.ocean.service.api.variable.Relation
import com.cmoney.backend2.ocean.service.api.variable.SuccessResultWithError
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class OceanWebImplTest {

    private val testScope = TestScope()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val service = mockk<OceanService>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: OceanWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = OceanWebImpl(
            manager = manager,
            service = service,
            gson = gson,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getOceanSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `changeAllBadge_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/ChangeAllBadge"
        val urlSlot = slot<String>()
        coEvery {
            service.changeAllBadge(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(SuccessResultWithError(isSuccess = true))

        web.changeAllBadge(
            false
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun changeAllBadge_success() = testScope.runTest {
        coEvery {
            service.changeAllBadge(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(SuccessResultWithError(isSuccess = true))

        val result = web.changeAllBadge(
            false
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun changeAllBadge_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)

        coEvery {
            service.changeAllBadge(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.changeAllBadge(
            false
        )
        checkServerException(result)
    }

    @Test
    fun `changeWearBadge_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/ChangeWearBadge"
        val urlSlot = slot<String>()
        coEvery {
            service.changeWearBadge(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(SuccessResultWithError(isSuccess = true))

        web.changeWearBadge(
            isWear = false,
            badgeIds = emptyList()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun changeWearBadge_success() = testScope.runTest {
        coEvery {
            service.changeWearBadge(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(SuccessResultWithError(isSuccess = true))

        val result = web.changeWearBadge(
            isWear = false,
            badgeIds = emptyList()
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun changeWearBadge_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)

        coEvery {
            service.changeWearBadge(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.changeWearBadge(
            isWear = false,
            badgeIds = emptyList()
        )
        checkServerException(result)
    }

    @Test
    fun `getBadgeAndRequirement_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/GetBadgeAndRequirement"
        val urlSlot = slot<String>()
        coEvery {
            service.getBadgeAndRequirement(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(JsonObject())

        web.getBadgeAndRequirement()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBadgeAndRequirement_success() = testScope.runTest {
        val responseBodyJson = """
            [
              {
                "BadgeId": 1,
                "Badge": {
                  "Description": "嗯，還是問看看好了",
                  "Enable": true,
                  "IconType": 1,
                  "Id": 1,
                  "Title": "愛舉手同學",
                  "Type": 0
                },
                "Requirement": {
                  "BadgeId": 1,
                  "Descriptioin": "提出1次問題，且有出現最佳解答",
                  "Metrics": [
                    {
                      "BadgeId": 1,
                      "Goal": 1,
                      "MetricsId": 1
                    }
                  ],
                  "Operator": 0
                }
              }
            ]
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, JsonArray::class.java)
        coEvery {
            service.getBadgeAndRequirement(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = web.getBadgeAndRequirement()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getBadgeAndRequirement_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, JsonObject::class.java)
        coEvery {
            service.getBadgeAndRequirement(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = web.getBadgeAndRequirement()
        checkServerException(result)
    }

    @Test
    fun `getBadgesCollection_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/GetBadgesCollection"
        val urlSlot = slot<String>()
        coEvery {
            service.getBadgesCollection(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(JsonObject())

        web.getBadgesCollection()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBadgesCollection_success() = testScope.runTest {
        val responseBodyJson = """
            [
              {
                "BadgeId": 0,
                "Read": true,
                "Wear": true,
                "Collectioned": true
              }
            ]
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, JsonArray::class.java)
        coEvery {
            service.getBadgesCollection(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getBadgesCollection()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getBadgesCollection_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, JsonObject::class.java)
        coEvery {
            service.getBadgesCollection(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getBadgesCollection()
        checkServerException(result)
    }

    @Test
    fun `getMetricsStats_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/GetMetricsStats"
        val urlSlot = slot<String>()
        coEvery {
            service.getMetricsStats(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(JsonObject())

        web.getMetricsStats()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMetricsStats_success() = testScope.runTest {
        val responseBodyJson = """
            [
              {
                "ChannelId": 0,
                "Count": 0,
                "MTime": "2023-05-12T08:33:54.217Z",
                "MetricsId": 0
              }
            ]
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, JsonArray::class.java)
        coEvery {
            service.getMetricsStats(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getMetricsStats()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMetricsStats_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, JsonObject::class.java)
        coEvery {
            service.getMetricsStats(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getMetricsStats()
        checkServerException(result)
    }

    @Test
    fun `getUnreadBadges_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/GetUnreadBadges"
        val urlSlot = slot<String>()
        coEvery {
            service.getUnreadBadges(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(JsonObject())

        web.getUnreadBadges()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUnreadBadges_success() = testScope.runTest {
        val responseBodyJson = """
            []
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, JsonArray::class.java)
        coEvery {
            service.getUnreadBadges(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getUnreadBadges()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getUnreadBadges_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, JsonObject::class.java)
        coEvery {
            service.getUnreadBadges(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getUnreadBadges()
        checkServerException(result)
    }

    @Test
    fun `channelWearBadge_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/ChannelWearBadge"
        val urlSlot = slot<String>()
        coEvery {
            service.channelWearBadge(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(JsonObject())

        web.channelWearBadge(channelIds = emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun channelWearBadge_success() = testScope.runTest {
        val responseBodyJson = """
            [
                {
                "Badges": [
                  0
                ],
                "ChannelId": 0
                }
            ]
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, JsonArray::class.java)
        coEvery {
            service.channelWearBadge(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.channelWearBadge(channelIds = listOf(1))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun channelWearBadge_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, JsonObject::class.java)
        coEvery {
            service.channelWearBadge(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.channelWearBadge(channelIds = listOf(1))
        checkServerException(result)
    }

    @Test
    fun `setBadgeRead_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Badge/SetBadgeRead"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.setBadgeRead(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.setBadgeRead(badgeId = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setBadgeRead_success() = testScope.runTest {
        val responseBodyJson = """
         {
          "IsSuccess": true
        }
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, SuccessResultWithError::class.java)
        coEvery {
            service.setBadgeRead(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.setBadgeRead(badgeId = 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun setBadgeRead_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)
        coEvery {
            service.setBadgeRead(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.setBadgeRead(badgeId = 0)
        checkServerException(result)
    }

    @Test
    fun `getChannelQuestions_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Questionnaire/GetChannelQuestions"
        val urlSlot = slot<String>()
        coEvery {
            service.getChannelQuestions(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(
            GetChannelQuestionsResponseWithError(
                isExist = false,
                questionnaire = null
            )
        )

        web.getChannelQuestions(channelId = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getChannelQuestions_success() = testScope.runTest {
        val responseBodyJson = """
        {
          "IsExist": true,
          "Questionnaire": {
            "IsActive": true,
            "Questions": [
              {
                "Id": 0,
                "AskQuestion": "string",
                "QuestionType": 1
              }
            ]
          }
        }
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetChannelQuestionsResponseWithError::class.java)
        coEvery {
            service.getChannelQuestions(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getChannelQuestions(channelId = 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getChannelQuestions_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetChannelQuestionsResponseWithError::class.java)
        coEvery {
            service.getChannelQuestions(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getChannelQuestions(channelId = 0)
        checkServerException(result)
    }

    @Test
    fun `channelQuestions_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Questionnaire/ChannelQuestions"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.channelQuestions(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.channelQuestions(
            questionnaire = ChannelQuestionnaire(
                channelId = 0,
                questions = listOf()
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun channelQuestions_success() = testScope.runTest {
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.channelQuestions(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.channelQuestions(
            questionnaire = ChannelQuestionnaire(
                channelId = 0,
                questions = listOf()
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun channelQuestions_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)
        coEvery {
            service.channelQuestions(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.channelQuestions(
            questionnaire = ChannelQuestionnaire(
                channelId = 0,
                questions = listOf()
            )
        )
        checkServerException(result)
    }

    @Test
    fun `channelQuestionsActivation_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Questionnaire/ChannelQuestionsActivation"
        val urlSlot = slot<String>()
        val responseBody = ChannelQuestionsActivationResponseWithError(
            isActive = true
        )
        coEvery {
            service.channelQuestionsActivation(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.channelQuestionsActivation(isActive = true, channelId = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun channelQuestionsActivation_success() = testScope.runTest {
        val responseBody = ChannelQuestionsActivationResponseWithError(
            isActive = true
        )
        coEvery {
            service.channelQuestionsActivation(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.channelQuestionsActivation(isActive = true, channelId = 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun channelQuestionsActivation_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, ChannelQuestionsActivationResponseWithError::class.java)
        coEvery {
            service.channelQuestionsActivation(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.channelQuestionsActivation(isActive = true, channelId = 0)
        checkServerException(result)
    }

    @Test
    fun `getAnswers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Questionnaire/GetAnswers"
        val urlSlot = slot<String>()
        val responseBody = AnswersResultWithError(
            memberAnswers = emptyList()
        )
        coEvery {
            service.getAnswers(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getAnswers(channelId = 0, memberChannelIds = emptyList(), questionIds = emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAnswers_success() = testScope.runTest {
        val responseBody = AnswersResultWithError(
            memberAnswers = emptyList()
        )
        coEvery {
            service.getAnswers(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result =
            web.getAnswers(channelId = 0, memberChannelIds = emptyList(), questionIds = emptyList())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getAnswers_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, AnswersResultWithError::class.java)
        coEvery {
            service.getAnswers(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result =
            web.getAnswers(channelId = 0, memberChannelIds = emptyList(), questionIds = emptyList())
        checkServerException(result)
    }

    @Test
    fun `answers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Questionnaire/Answers"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.answers(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.answers(answers = emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun answers_success() = testScope.runTest {
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.answers(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.answers(answers = emptyList())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun answers_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)
        coEvery {
            service.answers(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.answers(answers = emptyList())
        checkServerException(result)
    }

    @Test
    fun `hadPhoneAuthentication_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/MemberBadge/HadPhoneAuthentication"
        val urlSlot = slot<String>()
        val responseBody = HadPhoneAuthResponse(
            data = emptyList()
        )
        coEvery {
            service.hadPhoneAuthentication(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.hadPhoneAuthentication(channelId = emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun hadPhoneAuthentication_success() = testScope.runTest {
        val responseBody = HadPhoneAuthResponse(
            data = emptyList()
        )
        coEvery {
            service.hadPhoneAuthentication(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.hadPhoneAuthentication(channelId = emptyList())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun hadPhoneAuthentication_failure_ServerException() = testScope.runTest {
        val responseBody = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
            .toResponseBody()
        coEvery {
            service.hadPhoneAuthentication(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)

        val result = web.hadPhoneAuthentication(channelId = emptyList())
        checkServerException(result)
    }

    @Test
    fun `getStockLatestArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetStockLatestArticle"
        val urlSlot = slot<String>()
        val responseBody = GetStockLatestArticleResponse(
            articles = emptyList()
        )
        coEvery {
            service.getStockLatestArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getStockLatestArticle(
            baseArticleId = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            stockIdList = listOf(),
            articleNeedInfo = ArticleNeedInfo(),
            filterType = FilterType.ALL
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockLatestArticle_success() = testScope.runTest {
        val responseBody = GetStockLatestArticleResponse(
            articles = emptyList()
        )
        coEvery {
            service.getStockLatestArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getStockLatestArticle(
            baseArticleId = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            stockIdList = listOf(),
            articleNeedInfo = ArticleNeedInfo(),
            filterType = FilterType.ALL
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getStockLatestArticle_failure_ServerException() = testScope.runTest {
        val responseBody = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
            .toResponseBody()
        coEvery {
            service.getStockLatestArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)

        val result = web.getStockLatestArticle(
            baseArticleId = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            stockIdList = listOf(),
            articleNeedInfo = ArticleNeedInfo(),
            filterType = FilterType.ALL
        )
        checkServerException(result)
    }

    @Test
    fun `getChannelLatestArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetChannelLatestArticle"
        val urlSlot = slot<String>()
        val responseBody = GetChannelLatestArticleResponse(
            articles = emptyList()
        )
        coEvery {
            service.getChannelLatestArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getChannelLatestArticle(
            baseArticleId = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            articleNeedInfo = ArticleNeedInfo(),
            channelIdList = listOf()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getChannelLatestArticle_success() = testScope.runTest {
        val responseBody = GetChannelLatestArticleResponse(
            articles = emptyList()
        )
        coEvery {
            service.getChannelLatestArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getChannelLatestArticle(
            baseArticleId = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            articleNeedInfo = ArticleNeedInfo(),
            channelIdList = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getChannelLatestArticle_failure_ServerException() = testScope.runTest {
        val responseBody = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
            .toResponseBody()
        coEvery {
            service.getChannelLatestArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)

        val result = web.getChannelLatestArticle(
            baseArticleId = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            articleNeedInfo = ArticleNeedInfo(),
            channelIdList = listOf()
        )
        checkServerException(result)
    }

    @Test
    fun `changeCollectArticleState_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetArticle/ChangeCollectArticleState"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(true)
        coEvery {
            service.changeCollectArticleState(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        }.returns(Response.success(responseBody))

        web.changeCollectArticleState(1, true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `changeCollectArticleState_collect article success`() = testScope.runTest {
        val responseBody = SuccessResultWithError(true)
        coEvery {
            service.changeCollectArticleState(
                url = any(),
                authorization = any(),
                body = any()
            )
        }.returns(Response.success(responseBody))

        val result = web.changeCollectArticleState(1, true)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `changeCollectArticleState_cancel article success`() = testScope.runTest {
        val responseBody = SuccessResultWithError(false)
        coEvery {
            service.changeCollectArticleState(
                url = any(),
                authorization = any(),
                body = any()
            )
        }.returns(Response.success(responseBody))

        val result = web.changeCollectArticleState(1, true)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `changeCollectArticleState_collect article failure`() = testScope.runTest {
        val errorBody = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()
        val errorResponse =
            gson.fromJson(errorBody, SuccessResultWithError::class.java)
        coEvery {
            service.changeCollectArticleState(
                url = any(),
                authorization = any(),
                body = any()
            )
        }.returns(Response.success(errorResponse))

        val result = web.changeCollectArticleState(1, true)
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
    }

    @Test
    fun `impeachArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetArticle/ImpeachArticle"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.impeachArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.impeachArticle(
            articleId = 0,
            reason = ""
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun impeachArticle_success() = testScope.runTest {
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.impeachArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.impeachArticle(
            articleId = 0,
            reason = ""
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun impeachArticle_failure_ServerException() = testScope.runTest {
        val errorBody = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(errorBody, SuccessResultWithError::class.java)
        coEvery {
            service.impeachArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.impeachArticle(
            articleId = 0,
            reason = ""
        )
        checkServerException(result)
    }

    @Test
    fun `deleteArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetArticle/DeleteArticle"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.deleteArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.deleteArticle(
            articleId = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteArticle_success() = testScope.runTest {
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.deleteArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.deleteArticle(
            articleId = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun deleteArticle_failure_ServerException() = testScope.runTest {
        val errorBody = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(errorBody, SuccessResultWithError::class.java)
        coEvery {
            service.deleteArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.deleteArticle(
            articleId = 0
        )
        checkServerException(result)
    }

    @Test
    fun `getStockPopularArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetStockPopularArticle"
        val urlSlot = slot<String>()
        val responseBody = GetStockPopularArticleResponse(
            articles = listOf()
        )
        coEvery {
            service.getStockPopularArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getStockPopularArticle(
            skipCount = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            stockIdList = emptyList(),
            articleNeedInfo = ArticleNeedInfo(),
            filterType = FilterType.ALL
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockPopularArticle_success() = testScope.runTest {
        val responseBody = GetStockPopularArticleResponse(
            articles = listOf()
        )
        coEvery {
            service.getStockPopularArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getStockPopularArticle(
            skipCount = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            stockIdList = emptyList(),
            articleNeedInfo = ArticleNeedInfo(),
            filterType = FilterType.ALL
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getStockPopularArticle_failure_ServerException() = testScope.runTest {
        val responseBody = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
            .toResponseBody()
        coEvery {
            service.getStockPopularArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(400, responseBody)

        val result = web.getStockPopularArticle(
            skipCount = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false,
            stockIdList = emptyList(),
            articleNeedInfo = ArticleNeedInfo(),
            filterType = FilterType.ALL
        )
        checkServerException(result)
    }

    @Test
    fun `putOnBlackList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/ChannelBlackList/PutOnBlackList"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.putOnBlackList(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.putOnBlackList(105399L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun putOnBlackList_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            service.putOnBlackList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.putOnBlackList(105399L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test
    fun putOnBlackList_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.putOnBlackList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.putOnBlackList(105399L)
        checkServerException(result)
    }

    @Test
    fun `spinOffBlackList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/ChannelBlackList/SpinOffBlackList"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.spinOffBlackList(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.spinOffBlackList(105399L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun spinOffBlackList_success() = testScope.runTest {
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        coEvery {
            service.spinOffBlackList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = web.spinOffBlackList(105399L)
        Truth.assertThat(result.isSuccess).isTrue()
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test
    fun spinOffBlackList_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)

        coEvery {
            service.spinOffBlackList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.spinOffBlackList(105399L)
        checkServerException(result)
    }

    @Test
    fun `getBlackList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/ChannelBlackList/GetBlackList"
        val urlSlot = slot<String>()
        val responseBody = GetBlackListResponseBodyWithError(
            channelIds = listOf(222222)
        )
        coEvery {
            service.getBlackList(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.getBlackList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBlackList_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetBlackListResponseBodyWithError(
            channelIds = listOf(222222)
        )
        //設定api成功時的回傳
        coEvery {
            service.getBlackList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getBlackList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelIds).hasSize(1)
    }

    @Test
    fun getBlackList_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetBlackListResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.getBlackList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getBlackList()
        checkServerException(result)
    }

    @Test
    fun `getBlockList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/ChannelBlackList/GetBlockList"
        val urlSlot = slot<String>()
        val responseBody = GetBlockListResponseBodyWithError(
            channelIds = listOf(222222)
        )
        coEvery {
            service.getBlockList(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.getBlockList()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBlockList_success() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetBlockListResponseBodyWithError(
            channelIds = listOf(222222)
        )
        //設定api成功時的回傳
        coEvery {
            service.getBlockList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getBlockList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelIds).hasSize(1)
    }

    @Test
    fun getBlockList_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetBlockListResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.getBlockList(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getBlockList()
        checkServerException(result)
    }

    @Test
    fun `getNotify_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Notify/GetNotify"
        val urlSlot = slot<String>()
        val responseBody = GetNotifyResponseBody(emptyList())
        coEvery {
            service.getNotify(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.getNotify(
            isIncludeClub = false,
            lowerBoundNotifyTime = 0,
            notifyTypes = listOf(NotificationType.ReplySuggest),
            fetchCount = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNotify_success() = testScope.runTest {
        val responseBody = GetNotifyResponseBody(
            listOf(
                Notify(
                    articleId = null,
                    channelId = null,
                    content = null,
                    imageUrl = null,
                    isFollowed = null,
                    isReaded = null,
                    isSpecific = null,
                    memberChannelId = null,
                    notificationType = null,
                    notifyTime = null,
                    relatedArticleId = null,
                    sn = null,
                    title = null
                )
            )
        )

        coEvery {
            service.getNotify(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getNotify(
            isIncludeClub = false,
            lowerBoundNotifyTime = 0,
            notifyTypes = listOf(NotificationType.ReplySuggest),
            fetchCount = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.notifyList).hasSize(1)
    }

    @Test
    fun `getUnReadCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Notify/GetUnreadCount"
        val urlSlot = slot<String>()
        val responseBody = GetUnreadCountResponseBody(
            data = 25
        )
        coEvery {
            service.getUnreadCount(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.getUnreadCount(
            isIncludeClub = false,
            lowerBoundNotifyTime = 0,
            notifyTypes = listOf(NotificationType.ReplySuggest)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUnReadCount_success() = testScope.runTest {
        val responseBody = GetUnreadCountResponseBody(
            data = 25
        )

        coEvery {
            service.getUnreadCount(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getUnreadCount(

            isIncludeClub = false,
            lowerBoundNotifyTime = 0,
            notifyTypes = listOf(NotificationType.ReplySuggest)
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.data).isEqualTo(25)
    }

    @Test
    fun `setReaded_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Notify/SetReaded"
        val urlSlot = slot<String>()
        coEvery {
            service.setReaded(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)

        // 確認api是否成功
        web.setReaded(
            notifyIdAndIsSpecificPair = listOf()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setReaded_success() = testScope.runTest {
        coEvery {
            service.setReaded(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)

        // 確認api是否成功
        val result = web.setReaded(
            notifyIdAndIsSpecificPair = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `getSingleArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetSingleArticleV2"
        val urlSlot = slot<String>()
        val responseBody = GetSingleArticleResponseBody(
            data = null
        )
        coEvery {
            service.getSingleArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        web.getSingleArticle(
            articleId = 87030622L, articleNeedInfo = ArticleNeedInfo()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSingleArticle_success() = testScope.runTest {
        val articleId = 87030622L
        val responseBody = GetSingleArticleResponseBody(
            data = ArticleResult(
                article = Article(
                    articleFrom = null,
                    articleId = articleId,
                    articleType = null,
                    askInfo = null,
                    authorInfo = null,
                    clickCount = null,
                    content = null,
                    contentImage = null,
                    contentVideoUrl = null,
                    createTime = null,
                    dislikeCount = null,
                    isAnonymous = null,
                    isCollected = null,
                    isDisliked = null,
                    isFollowedAuthor = null,
                    isLiked = null,
                    likeCount = null,
                    collectCount = null,
                    locationChannelInfo = null,
                    replyCount = null,
                    retweetCount = null,
                    retweetOriginalArticle = null,
                    sourceUrl = null,
                    stockTags = listOf(),
                    tipInfo = null,
                    title = null,
                    multipleImages = listOf(),
                    stockInfos = listOf()
                ),
                commentResult = listOf()
            )
        )

        coEvery {
            service.getSingleArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getSingleArticle(
            articleId = articleId, articleNeedInfo = ArticleNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.data?.article?.articleId).isEqualTo(articleId)
    }

    @Test
    fun `getComments_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Comment/GetComments"
        val urlSlot = slot<String>()
        val responseBody = GetCommentsResponseBody(
            data = emptyList()
        )
        coEvery {
            service.getComments(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getComments(
            articleId = 0, upperBoundArticleId = 0, fetchCount = 20
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getComments_success() = testScope.runTest {
        val articleId = 87030622L
        val responseBody = GetCommentsResponseBody(
            data = listOf(
                Comment(
                    articleId = null,
                    articleType = null,
                    authorInfo = null,
                    content = null,
                    contentImage = null,
                    contentVideoUrl = null,
                    createTime = null,
                    dislikeCount = null,
                    hasAuthToReadReply = null,
                    isDisLike = null,
                    isLike = null,
                    likeCount = null
                )
            )
        )

        coEvery {
            service.getComments(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getComments(
            articleId = articleId, upperBoundArticleId = 0, fetchCount = 20
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.data?.size).isEqualTo(1)
    }

    @Test
    fun `getManagerList(single needInfo)_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetClub/GetMangerList"
        val urlSlot = slot<String>()
        val responseBody = GetManagerListResponseWithError(
            creator = null,
            managers = listOf()
        )
        coEvery {
            service.getManagerList(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getManagerList(channelId = 4277314, needInfo = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getManagerList(single needInfo)_success`() = testScope.runTest {
        //準備api成功時的回傳
        val responseBody = GetManagerListResponseWithError(
            creator = null,
            managers = listOf()
        )

        coEvery {
            service.getManagerList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getManagerList(channelId = 4277314, needInfo = 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `getManagerList(single needInfo)_failure`() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetManagerListResponseWithError::class.java)
        coEvery {
            service.getManagerList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = web.getManagerList(channelId = 4277314, needInfo = 0)
        checkServerException(result)
    }

    @Test
    fun `getManagerList(multiple needInfo)_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetClub/GetMangerList"
        val urlSlot = slot<String>()
        val responseBody = GetManagerListResponseWithError(
            creator = null,
            managers = listOf()
        )

        coEvery {
            service.getManagerList(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getManagerList(clubChannelId = 4277314, needInfo = ChannelNeedInfo())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getManagerList(multiple needInfo)_success`() = testScope.runTest {
        val responseBody = GetManagerListResponseWithError(
            creator = null,
            managers = listOf()
        )

        coEvery {
            service.getManagerList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        val result = web.getManagerList(clubChannelId = 4277314, needInfo = clubNeedInfo)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `getManagerList(multiple needInfo)_failure`() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetManagerListResponseWithError::class.java)

        coEvery {
            service.getManagerList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        val result = web.getManagerList(4277314, clubNeedInfo)
        checkServerException(result)
    }

    @Test
    fun `checkHasJoinedClub_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetClub/CheckHasJoinedClub"
        val urlSlot = slot<String>()
        val responseBody = HasJoinedClubResponseWithError(
            isJoin = true
        )

        coEvery {
            service.isJoinClub(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.isJoinedClub(2850768, Relation.ClubCreated)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkHasJoinedClub_isJoin_true() = testScope.runTest {
        val responseBody = HasJoinedClubResponseWithError(
            isJoin = true
        )

        coEvery {
            service.isJoinClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.isJoinedClub(2850768, Relation.ClubCreated)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isJoin).isTrue()
    }

    @Test
    fun checkHasJoinedClub_isJoin_false() = testScope.runTest {
        val responseBody = HasJoinedClubResponseWithError(
            isJoin = false
        )

        coEvery {
            service.isJoinClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.isJoinedClub(2850768, Relation.ClubCreated)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isJoin).isFalse()
    }

    @Test
    fun checkHasJoinedClub_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, HasJoinedClubResponseWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.isJoinClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.isJoinedClub(2850768, Relation.ClubCreated)
        checkServerException(result)
    }

    @Test
    fun `getOtherChannelInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/GetChannelInfo"
        val urlSlot = slot<String>()
        val responseBody = ChannelInfo.ChannelBaseInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null
        )

        coEvery {
            service.getOtherChannelInfo(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getOtherChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getOtherChannelInfo_uccess() = testScope.runTest {
        val responseBody = ChannelInfo.ChannelBaseInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null
        )

        coEvery {
            service.getOtherChannelInfo(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getOtherChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `getMemberChannelInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/GetChannelInfo"
        val urlSlot = slot<String>()
        val responseBody = ChannelInfo.MemberChannelInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null,
            answersCount = null,
            articleCount = null,
            attentionCount = null,
            diamondInfo = null,
            evaluationInfo = null,
            fansCount = null,
            isFollowed = null,
            levelInfo = null,
            likeCount = null,
            memberRegisterTime = null,
            viewerEvaluationInfo = null
        )

        coEvery {
            service.getMemberChannelInfo(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        web.getMemberChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberChannelInfo_success() = testScope.runTest {
        val responseBody = ChannelInfo.MemberChannelInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null,
            answersCount = null,
            articleCount = null,
            attentionCount = null,
            diamondInfo = null,
            evaluationInfo = null,
            fansCount = null,
            isFollowed = null,
            levelInfo = null,
            likeCount = null,
            memberRegisterTime = null,
            viewerEvaluationInfo = null
        )

        coEvery {
            service.getMemberChannelInfo(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getMemberChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `getClubChannelInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/GetChannelInfo"
        val urlSlot = slot<String>()
        val responseBody = ChannelInfo.ClubChannelInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null,
            memberClubInfo = null,
            viewerClubInfo = null,
            clubInfo = null
        )

        coEvery {
            service.getClubChannelInfo(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        web.getClubChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getClubChannelInfo_success() = testScope.runTest {
        val responseBody = ChannelInfo.ClubChannelInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null,
            memberClubInfo = null,
            viewerClubInfo = null,
            clubInfo = null
        )

        coEvery {
            service.getClubChannelInfo(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getClubChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `getRssSignalChannelInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/GetChannelInfo"
        val urlSlot = slot<String>()
        val responseBody = ChannelInfo.RssSignalChannelInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null,
            fansCount = null,
            likeCount = null,
            articleCount = null,
            isFollowed = null
        )

        coEvery {
            service.getRssSignalChannelInfo(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getRssSignalChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRssSignalChannelInfo_success() = testScope.runTest {
        val responseBody = ChannelInfo.RssSignalChannelInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null,
            fansCount = null,
            likeCount = null,
            articleCount = null,
            isFollowed = null
        )

        coEvery {
            service.getRssSignalChannelInfo(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getRssSignalChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `searchChannel_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/SearchChannel"
        val urlSlot = slot<String>()
        val responseBody = SearchChannelResponseBody(
            clubs = listOf()
        )

        coEvery {
            service.searchChannel(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.searchChannel(
            channelTypes = ChannelTypes.MemberClub,
            fetchCount = 0,
            keyword = ""
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchChannel_success() = testScope.runTest {
        val responseBody = SearchChannelResponseBody(
            clubs = listOf()
        )

        coEvery {
            service.searchChannel(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.searchChannel(

            channelTypes = ChannelTypes.MemberClub,
            fetchCount = 0,
            keyword = ""
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.clubs?.size).isEqualTo(0)
    }

    @Test
    fun `getFanListExcludeJoinedClub_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/GetFansListExcludeJoinedClub"
        val urlSlot = slot<String>()
        val responseBody = GetFansListExcludeJoinedClubResponseBody(
            channels = listOf()
        )

        coEvery {
            service.getFanListExcludeJoinedClub(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getFanListExcludeJoinedClub(

            needInfo = ChannelNeedInfo(),
            excludeClubChannelId = 0,
            fetchCount = 0,
            skipCount = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getFanListExcludeJoinedClub_success() =
        testScope.runTest {
            val responseBody = GetFansListExcludeJoinedClubResponseBody(
                channels = listOf()
            )

            coEvery {
                service.getFanListExcludeJoinedClub(
                    url = any(),
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(responseBody)

            val result = web.getFanListExcludeJoinedClub(
                needInfo = ChannelNeedInfo(),
                excludeClubChannelId = 0,
                fetchCount = 0,
                skipCount = 0
            )
            Truth.assertThat(result.isSuccess).isTrue()

            val data = result.getOrThrow()
            Truth.assertThat(data.channels?.size).isEqualTo(0)
        }

    @Test
    fun `getSimpleChannelInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetChannel/GetSimpleChannelInfo"
        val urlSlot = slot<String>()
        val responseBody = GetSimpleChannelInfoResponseBody(
            channelInfo = listOf()
        )

        coEvery {
            service.getSimpleChannelInfo(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getSimpleChannelInfo(
            channelIds = listOf()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSimpleChannelInfo_success() = testScope.runTest {
        val responseBody = GetSimpleChannelInfoResponseBody(
            channelInfo = listOf()
        )

        coEvery {
            service.getSimpleChannelInfo(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getSimpleChannelInfo(
            channelIds = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.channelInfo?.size).isEqualTo(0)
    }

    @Test
    fun `setEvaluation_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Evaluation/SetEvaluation"
        val urlSlot = slot<String>()
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )

        coEvery {
            service.setEvaluation(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.setEvaluation(105399L, "", 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setEvaluation_success() = testScope.runTest {
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )

        coEvery {
            service.setEvaluation(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.setEvaluation(105399L, "", 0)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isEqualTo(true)
    }

    @Test
    fun `checkHasEvaluated_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Evaluation/CheckHasEvaluated"
        val urlSlot = slot<String>()
        val responseBody = CheckHasEvaluatedResponseBodyWithError(
            hasEvaluated = true
        )

        coEvery {
            service.checkHasEvaluated(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.checkHasEvaluated(105399L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun checkHasEvaluated_success() = testScope.runTest {
        val responseBody = CheckHasEvaluatedResponseBodyWithError(
            hasEvaluated = true
        )

        coEvery {
            service.checkHasEvaluated(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.checkHasEvaluated(105399L)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.hasEvaluated).isEqualTo(true)
    }

    @Test
    fun `getEvaluationList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Evaluation/GetEvaluationList"
        val urlSlot = slot<String>()
        val responseBody = GetEvaluationListResponseBodyWithError(
            evaluations = listOf()
        )

        coEvery {
            service.getEvaluationList(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getEvaluationList(105399L, 10, 0, SortType.LatestToOldest)

        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getEvaluationList_success() = testScope.runTest {
        val responseBody = GetEvaluationListResponseBodyWithError(
            evaluations = listOf()
        )

        coEvery {
            service.getEvaluationList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getEvaluationList(105399L, 10, 0, SortType.LatestToOldest)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.evaluations?.size).isEqualTo(0)
    }

    private fun <T> checkServerException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    private fun <T> checkHttpException(result: Result<T>) {
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as HttpException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getCollectArticleList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetArticleList/GetCollectArticleList"
        val urlSlot = slot<String>()
        val responseBody = GetCollectArticleListResponseBodyWithError(
            articles = listOf()
        )

        coEvery {
            service.getCollectArticleList(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getCollectArticleList(0, 20, ArticleNeedInfo(), true)

        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCollectArticleList_success() = testScope.runTest {
        val responseBody = GetCollectArticleListResponseBodyWithError(
            articles = listOf()
        )

        coEvery {
            service.getCollectArticleList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getCollectArticleList(0, 20, ArticleNeedInfo(), true)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.articles?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getCollectArticleList_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetCollectArticleListResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.getCollectArticleList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.getCollectArticleList(0, 20, ArticleNeedInfo(), true)
        checkServerException(result)
    }

    @Test
    fun `getMasters_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Customization/GetMasters"
        val urlSlot = slot<String>()
        val responseBody = GetMastersResponseBodyWithError(
            masters = listOf()
        )
        coEvery {
            service.getMasters(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getMasters(MasterType.Popularity, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMasters_success() = testScope.runTest {
        val responseBody = GetMastersResponseBodyWithError(
            masters = listOf()
        )
        coEvery {
            service.getMasters(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getMasters(MasterType.Popularity, 20)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.masters?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getMasters_failure_HttpException() = testScope.runTest {
        //準備api成功時的回傳
        val json = ""
        //設定api成功時的回傳
        coEvery {
            service.getMasters(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = web.getMasters(MasterType.Popularity, 20)
        checkHttpException(result)
    }

    @Test
    fun `getAskLatestArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetAskLatestArticle"
        val urlSlot = slot<String>()
        val responseBody = GetAskLatestArticleResponseBody(
            articles = listOf()
        )

        coEvery {
            service.getAskLatestArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getAskLatestArticle(0, 20, listOf(), ArticleNeedInfo())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAskLatestArticle_success() = testScope.runTest {
        val responseBody = GetAskLatestArticleResponseBody(
            articles = listOf()
        )

        coEvery {
            service.getAskLatestArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getAskLatestArticle(0, 20, listOf(), ArticleNeedInfo())
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.articles?.isEmpty()).isTrue()
    }

    @Test
    fun getAskLatestArticle_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        coEvery {
            service.getAskLatestArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        val result = web.getAskLatestArticle(-1, 20, listOf(), ArticleNeedInfo())
        checkServerException(result)
    }

    @Test
    fun getAskLatestArticle_failure_401HttpException() = testScope.runTest {
        //設定api成功時的回傳
        coEvery {
            service.getAskLatestArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, "".toResponseBody())

        //確認api是否成功
        val result = web.getAskLatestArticle(
            0, 20, listOf("字串"),
            ArticleNeedInfo()
        )
        checkHttpException(result)
    }

    @Test
    fun `getStockMasterEvaluationList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Customization/GetStockMasterEvaluationList"
        val urlSlot = slot<String>()
        val responseBody = GetStockMasterEvaluationListResponseBodyWithError(
            masterEvaluationScores = listOf()
        )
        coEvery {
            service.getStockMasterEvaluationList(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getStockMasterEvaluationList(listOf("2330"))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockMasterEvaluationList_success() = testScope.runTest {
        val responseBody = GetStockMasterEvaluationListResponseBodyWithError(
            masterEvaluationScores = listOf()
        )
        coEvery {
            service.getStockMasterEvaluationList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getStockMasterEvaluationList(listOf("2330"))
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.masterEvaluationScores?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getStockMasterEvaluationList_failure() = testScope.runTest {
        val json = ""
        coEvery {
            service.getStockMasterEvaluationList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = web.getStockMasterEvaluationList(listOf("2330"))
        checkHttpException(result)
    }

    @Test
    fun `getStockMasterEvaluation_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Customization/GetStockMasterEvaluation"
        val urlSlot = slot<String>()
        val responseBody = GetStockMasterEvaluationResponseBodyWithError(
            avgScores = 7.2,
            channelId = 1000,
            closePr = null,
            trends = null
        )
        coEvery {
            service.getStockMasterEvaluation(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.getStockMasterEvaluation(
            "2330"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockMasterEvaluation_success() = testScope.runTest {
        val responseBody = GetStockMasterEvaluationResponseBodyWithError(
            avgScores = 7.2,
            channelId = 1000,
            closePr = null,
            trends = null
        )
        coEvery {
            service.getStockMasterEvaluation(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getStockMasterEvaluation(
            "2330"
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.avgScores).isEqualTo(7.2)
        Truth.assertThat(data.channelId).isEqualTo(1000)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getStockMasterEvaluation_failure() = testScope.runTest {
        //準備api成功時的回傳
        val json = ""
        //設定api成功時的回傳
        coEvery {
            service.getStockMasterEvaluation(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = web.getStockMasterEvaluation(
            "2330"
        )
        checkHttpException(result)
    }

    @Test
    fun `uploadChannelImage_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/File/UploadChannelImage"
        val urlSlot = slot<String>()
        val responseBody = UploadChannelImageResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.uploadChannelImage(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.uploadChannelImage(105498)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun uploadChannelImage_success() = testScope.runTest {
        val responseBody = UploadChannelImageResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.uploadChannelImage(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.uploadChannelImage(105498)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun uploadChannelImage_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 8001005,
            "Message": "上傳失敗"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, UploadChannelImageResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.uploadChannelImage(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.uploadChannelImage(105498)
        checkServerException(result)
    }

    @Test
    fun `createClub_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/CreateClub"
        val urlSlot = slot<String>()
        val responseBody = CreateClubResponseBodyWithError(
            channelId = 1000
        )
        coEvery {
            service.createClub(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.createClub("我的新社團", "新的呦", JoinMethod.Join)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createClub_success() = testScope.runTest {
        val responseBody = CreateClubResponseBodyWithError(
            channelId = 1000
        )
        coEvery {
            service.createClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.createClub("我的新社團", "新的呦", JoinMethod.Join)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun createClub_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 1001002,
            "Message": "沒有達成成立社團條件"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, CreateClubResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.createClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.createClub("我的新社團", "新的呦", JoinMethod.Join)
        checkServerException(result)
    }

    @Test
    fun `deleteClub_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/DeleteClub"
        val urlSlot = slot<String>()
        val responseBody = DeleteClubResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.deleteClub(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.deleteClub(0L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteClub_success() = testScope.runTest {
        val responseBody = DeleteClubResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.deleteClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.deleteClub(0L)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun deleteClub_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, DeleteClubResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.deleteClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.deleteClub(0L)
        checkServerException(result)
    }

    @Test
    fun `leaveClub_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/LeaveClub"
        val urlSlot = slot<String>()
        val responseBody = LeaveClubResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.leaveClub(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.leaveClub(0L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun leaveClub_success() = testScope.runTest {
        val responseBody = LeaveClubResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.leaveClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.leaveClub(0L)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun leaveClub_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, LeaveClubResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.leaveClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = web.leaveClub(0L)
        checkServerException(result)
    }

    @Test
    fun `invite_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/Invitation"
        val urlSlot = slot<String>()
        val responseBody = InviteResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.invite(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.invite(4277314, listOf(7869455))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun invite_success() = testScope.runTest {
        val responseBody = InviteResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.invite(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.invite(4277314, listOf(7869455))
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun invite_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, InviteResponseBodyWithError::class.java)

        coEvery {
            service.invite(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.invite(4277314, listOf(7869455))
        checkServerException(result)
    }

    @Test
    fun `joinClub_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/JoinClub"
        val urlSlot = slot<String>()
        val responseBody = JoinClubResponseBodyWithError(
            memberPosition = MemberPosition.Reviewing
        )
        coEvery {
            service.joinClub(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.joinClub(4277314L, "")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun joinClub_success() = testScope.runTest {
        val responseBody = JoinClubResponseBodyWithError(
            memberPosition = MemberPosition.Reviewing
        )
        coEvery {
            service.joinClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.joinClub(4277314L, "")
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.memberPosition).isEqualTo(MemberPosition.Reviewing)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }


    @Test
    fun joinClub_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, JoinClubResponseBodyWithError::class.java)

        coEvery {
            service.joinClub(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.joinClub(0L, "")
        checkServerException(result)
    }

    @Test
    fun `getMemberClubs_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetClub/GetMemberClubs"
        val urlSlot = slot<String>()
        val responseBody = GetMemberClubsResponseBodyWithError(
            clubs = listOf()
        )

        coEvery {
            service.getMemberClubs(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Club>()
        clubNeedInfo.add(ChannelInfoOption.Club.MemberClubInfo)
        clubNeedInfo.add(ChannelInfoOption.Club.ClubInfo)

        web.getMemberClubs(0L, clubNeedInfo, Relation.AllClub)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberClubs_success() = testScope.runTest {
        val responseBody = GetMemberClubsResponseBodyWithError(
            clubs = listOf()
        )

        coEvery {
            service.getMemberClubs(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Club>()
        clubNeedInfo.add(ChannelInfoOption.Club.MemberClubInfo)
        clubNeedInfo.add(ChannelInfoOption.Club.ClubInfo)

        val result = web.getMemberClubs(0L, clubNeedInfo, Relation.AllClub)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.clubs?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getMemberClubs_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetMemberClubsResponseBodyWithError::class.java)

        coEvery {
            service.getMemberClubs(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Club>()
        clubNeedInfo.add(ChannelInfoOption.Club.MemberClubInfo)
        clubNeedInfo.add(ChannelInfoOption.Club.ClubInfo)

        val result = web.getMemberClubs(0L, clubNeedInfo, Relation.AllClub)
        checkServerException(result)
    }

    @Test
    fun `getRecommendClubs_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetClub/GetRecommendClubs"
        val urlSlot = slot<String>()
        val responseBody = GetRecommendClubsResponseBodyWithError(
            clubs = listOf(),
            updatedInSeconds = 1000
        )
        coEvery {
            service.getRecommendClubs(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = RecommendClubsNeedInfo()
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.ClubInfo)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.Image)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.Description)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.MemberClubInfo)

        web.getRecommendClubs(10, 0, clubNeedInfo)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRecommendClubs_success() = testScope.runTest {
        val responseBody = GetRecommendClubsResponseBodyWithError(
            clubs = listOf(),
            updatedInSeconds = 1000
        )
        coEvery {
            service.getRecommendClubs(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = RecommendClubsNeedInfo()
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.ClubInfo)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.Image)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.Description)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.MemberClubInfo)

        val result = web.getRecommendClubs(10, 0, clubNeedInfo)
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.clubs?.size).isEqualTo(0)
        Truth.assertThat(data.updatedInSeconds).isEqualTo(1000)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getRecommendClubs_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetRecommendClubsResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.getRecommendClubs(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = RecommendClubsNeedInfo()
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.ClubInfo)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.Image)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.Description)
        clubNeedInfo.add(RecommendClubsNeedInfo.NeedInfo.MemberClubInfo)

        //確認api是否成功
        val result = web.getRecommendClubs(10, 0, clubNeedInfo)
        checkServerException(result)
    }

    @Test
    fun `changeMemberStatus_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/ChangeMemberStatus"
        val urlSlot = slot<String>()
        val responseBody = ChangeMemberStatusResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.changeMemberStatus(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.changeMemberStatus(4277314, listOf(7869455), Operation.MoveBlackList)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun changeMemberStatus_success() = testScope.runTest {
        val responseBody = ChangeMemberStatusResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.changeMemberStatus(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.changeMemberStatus(4277314, listOf(7869455), Operation.MoveBlackList)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun changeMemberStatus_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 701101,
            "Message": "沒有權限"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, ChangeMemberStatusResponseBodyWithError::class.java)

        coEvery {
            service.changeMemberStatus(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.changeMemberStatus(4277314, listOf(7869455), Operation.MoveBlackList)
        checkServerException(result)
    }

    @Test
    fun `updateClubDescription_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/SetClub/UpdateClubDescription"
        val urlSlot = slot<String>()
        val responseBody = UpdateClubDescriptionResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.updateClubDescription(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.updateClubDescription(4277314, "社團的功能有些單調123456")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateClubDescription_success() = testScope.runTest {
        val responseBody = UpdateClubDescriptionResponseBodyWithError(
            isSuccess = true
        )
        coEvery {
            service.updateClubDescription(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.updateClubDescription(4277314, "社團的功能有些單調123456")
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun updateClubDescription_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 1001009,
            "Message": "權限不夠，不可變更。(0)"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, UpdateClubDescriptionResponseBodyWithError::class.java)

        coEvery {
            service.updateClubDescription(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.updateClubDescription(4277314, "社團的功能有些單調123456")
        checkServerException(result)
    }

    @Test
    fun `getMemberStatusList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/GetClub/GetMemberStatusList"
        val urlSlot = slot<String>()
        val responseBody = GetMemberStatusListResponseBodyWithError(
            channels = listOf()
        )
        coEvery {
            service.getMemberStatusList(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        web.getMemberStatusList(
            4277314,
            MemberPosition.BlackList,
            50,
            0,
            clubNeedInfo
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberStatusList_success() = testScope.runTest {
        val responseBody = GetMemberStatusListResponseBodyWithError(
            channels = listOf()
        )
        coEvery {
            service.getMemberStatusList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        val result = web.getMemberStatusList(
            4277314,
            MemberPosition.BlackList,
            50,
            0,
            clubNeedInfo
        )
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.channels?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getMemberStatusList_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetMemberStatusListResponseBodyWithError::class.java)

        coEvery {
            service.getMemberStatusList(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        val result = web.getMemberStatusList(
            4277314,
            MemberPosition.BlackList,
            50,
            0,
            clubNeedInfo
        )
        checkServerException(result)
    }

    @Test
    fun `getRelevantComments_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetRelevantComments"
        val urlSlot = slot<String>()
        val responseBody = GetRelevantCommentsResponseWithError(listOf())
        coEvery {
            service.getRelevantComments(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getRelevantComments(
            listOf(4277314),
            0L
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRelevantComment_success() = testScope.runTest {
        val responseBody = GetRelevantCommentsResponseWithError(listOf())
        coEvery {
            service.getRelevantComments(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getRelevantComments(
            listOf(4277314),
            0L
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getRelevantComment_failure() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetRelevantCommentsResponseWithError::class.java)
        coEvery {
            service.getRelevantComments(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        checkServerException(web.getRelevantComments(listOf(4277314), 0L))
    }

    @Test
    fun `getTopicArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetTopicArticles"
        val urlSlot = slot<String>()
        val responseBody = GetTopicArticlesResponseBodyWithError(
            articles = listOf()
        )
        coEvery {
            service.getTopicArticles(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getTopicArticles(
            topic = "研究報告",
            baseArticleId = 9223372036854775807,
            fetchCount = 2,
            articleNeedInfo = ArticleNeedInfo().apply {
                addAll(ArticleNeedInfo.NeedInfo.values().toList())
            })
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getTopicArticles_success() = testScope.runTest {
        val responseBody = GetTopicArticlesResponseBodyWithError(
            articles = listOf()
        )
        coEvery {
            service.getTopicArticles(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getTopicArticles(
            topic = "研究報告",
            baseArticleId = 9223372036854775807,
            fetchCount = 2,
            articleNeedInfo = ArticleNeedInfo().apply {
                addAll(ArticleNeedInfo.NeedInfo.values().toList())
            })
        Truth.assertThat(result.isSuccess).isTrue()

        val data = result.getOrThrow()
        Truth.assertThat(data.articles?.isEmpty()).isTrue()
    }

    @Test
    fun getTopicArticles_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        val responseBody =
            gson.fromJson(json, GetTopicArticlesResponseBodyWithError::class.java)

        coEvery {
            service.getTopicArticles(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(200, responseBody)

        val result = web.getTopicArticles(
            topic = "研究報告",
            baseArticleId = 9223372036854775807,
            fetchCount = 2,
            articleNeedInfo = ArticleNeedInfo().apply {
                addAll(ArticleNeedInfo.NeedInfo.values().toList())
            })
        checkServerException(result)
    }

    @Test
    fun `getStockAndTopicArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/Article/GetStockAndTopicArticles"
        val urlSlot = slot<String>()
        val responseBody = GetStockAndTopicArticlesResponseBodyWithError(
            articles = listOf()
        )
        coEvery {
            service.getStockAndTopicArticles(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        web.getStockAndTopicArticles(
            stockId = "2330",
            topic = "研究報告",
            baseArticleId = 9223372036854775807,
            fetchCount = 2,
            articleNeedInfo = ArticleNeedInfo().apply {
                addAll(ArticleNeedInfo.NeedInfo.values().toList())
            })
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockAndTopicArticles_success() = testScope.runTest {
        val responseBody = GetStockAndTopicArticlesResponseBodyWithError(
            articles = listOf()
        )
        coEvery {
            service.getStockAndTopicArticles(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = web.getStockAndTopicArticles(
            stockId = "2330",
            topic = "研究報告",
            baseArticleId = 9223372036854775807,
            fetchCount = 2,
            articleNeedInfo = ArticleNeedInfo().apply {
                addAll(ArticleNeedInfo.NeedInfo.values().toList())
            })
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.articles?.isEmpty()).isTrue()
    }

    @Test
    fun getStockAndTopicArticles_failure_ServerException() =
        testScope.runTest {
            val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

            val responseBody =
                gson.fromJson(json, GetStockAndTopicArticlesResponseBodyWithError::class.java)

            //設定api成功時的回傳
            coEvery {
                service.getStockAndTopicArticles(
                    url = any(),
                    authorization = any(),
                    body = any()
                )
            } returns Response.success(200, responseBody)

            val result = web.getStockAndTopicArticles(
                stockId = "2330",
                topic = "研究報告",
                baseArticleId = 9223372036854775807,
                fetchCount = 2,
                articleNeedInfo = ArticleNeedInfo().apply {
                    addAll(ArticleNeedInfo.NeedInfo.values().toList())
                })
            checkServerException(result)
        }

    @Test
    fun `createOrUpdateAnnouncement_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/club/5083102/createorupdateannouncement"
        val urlSlot = slot<String>()
        coEvery {
            service.createOrUpdateAnnouncement(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(IsCreateOrUpdateSuccessResponseWithError(isSuccess = true))

        web.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createOrUpdateAnnouncement_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        val responseBody =
            gson.fromJson(json, IsCreateOrUpdateSuccessResponseWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            service.createOrUpdateAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        checkServerException(result)
    }

    @Test
    fun `createOrUpdateAnnouncement_isSuccess is true`() = testScope.runTest {
        val json = """{"IsSuccess":true}""".trimIndent()
        val responseBody =
            gson.fromJson(json, IsCreateOrUpdateSuccessResponseWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            service.createOrUpdateAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = web.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        Truth.assertThat(result.getOrNull()?.isSuccess).isEqualTo(true)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `createOrUpdateAnnouncement_isSuccess is false`() = testScope.runTest {
        val json = """{"IsSuccess":false}""".trimIndent()
        val responseBody =
            gson.fromJson(json, IsCreateOrUpdateSuccessResponseWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            service.createOrUpdateAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = web.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        Truth.assertThat(result.getOrNull()?.isSuccess).isEqualTo(false)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getAllAnnouncement_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/club/5083102/readannouncements"
        val urlSlot = slot<String>()
        coEvery {
            service.readAnnouncement(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(AnnouncementListResponseWithError(emptyList()))

        web.getAllAnnouncements(
            clubChannelId = 5083102
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAllAnnouncement_success() = testScope.runTest {
        val json = """{
          "Announcements":[
            {
            "Article":{
                "ArticleId":109002785,
                "ArticleType":1,
                "Content":"App修復後反應變很快～好用",
                "CreateTime":1612329140,
                "Title":"",
                "ContentImage":"",
                "ContentVideoUrl":"",
                "SourceUrl":"",
                "ArticleFrom":"",
                "StockTags":[],
                "LikeCount":2,
                "DislikeCount":0,
                "ReplyCount":0,
                "RetweetCount":0,
                "ClickCount":0,
                "CollectCount":0,
                "IsLiked":false,
                "IsDisliked":false,
                "IsCollected":false,
                "IsFollowedAuthor":false,
                "IsAnonymous":false,
                "AuthorInfo":{
                    "ChannelId":2296212,
                    "ChannelType":101,
                    "ChannelName":"汪汪",
                    "Image":"https://fsv.cmoney.tw/cmstatic/head-icons/a93.gif",
                    "DiamondInfo":{
                        "IntervalOfDiamondLevel":10,
                        "Level":0,
                        "Quantity":0
                    }
                },
                "LocationChannelInfo":{
                    "ChannelId":5083102,
                    "ChannelType":402,
                    "ChannelName":"陳重銘VIP社團"
                },
                "MultipleImages":[]
            },
            "IsPinned":true,
            "PinnedTime":1612346101,
            "CreateTime":1612346101
        }
    ]
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, AnnouncementListResponseWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            service.readAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = web.getAllAnnouncements(
            clubChannelId = 5083102
        )
        Truth.assertThat(result.getOrNull()?.list?.size).isEqualTo(1)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getAllAnnouncement_failure_ServerException() = testScope.runTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        val responseBody = gson.fromJson(json, AnnouncementListResponseWithError::class.java)

        coEvery {
            service.readAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.getAllAnnouncements(
            clubChannelId = 5083102
        )
        checkServerException(result)
    }

    @Test
    fun `removeAnnouncements_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}OceanService/api/club/5083102/removeannouncement"
        val json = """{
          "IsSuccess":true
        }""".trimIndent()
        val urlSlot = slot<String>()
        val responseBody =
            gson.fromJson(json, IsRemoveAnnouncementSuccessWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            service.removeAnnouncement(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        web.removeAnnouncements(
            clubChannelId = 5083102,
            articleId = 108696750,
            isPinned = false
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun removeAnnouncements_success() = testScope.runTest {
        val json = """{
          "IsSuccess":true
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, IsRemoveAnnouncementSuccessWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            service.removeAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = web.removeAnnouncements(
            clubChannelId = 5083102,
            articleId = 108696750,
            isPinned = false
        )
        Truth.assertThat(result.getOrNull()?.isSuccess).isEqualTo(true)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun removeAnnouncements_failure() = testScope.runTest {
        val json = """{
          "Error":{"Code":103,"Message":"該公告不存在"}
        }""".trimIndent()

        val responseBody =
            gson.fromJson(json, IsRemoveAnnouncementSuccessWithError::class.java)

        coEvery {
            service.removeAnnouncement(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = web.removeAnnouncements(
            clubChannelId = 5083102,
            articleId = 108696750,
            isPinned = false
        )
        checkServerException(result)
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}