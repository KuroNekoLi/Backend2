package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.chatroom.GetUncheckChatRoomCountResponse
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.hide.HideCommentRequestBody
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.group.v2.Admins
import com.cmoney.backend2.forumocean.service.api.group.v2.AvailableBoardIds
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardManipulation
import com.cmoney.backend2.forumocean.service.api.group.v2.BoardSingle
import com.cmoney.backend2.forumocean.service.api.group.v2.Group
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupManipulation
import com.cmoney.backend2.forumocean.service.api.group.v2.GroupPushSetting
import com.cmoney.backend2.forumocean.service.api.group.v2.InsertedId
import com.cmoney.backend2.forumocean.service.api.group.v2.JoinGroupRequest
import com.cmoney.backend2.forumocean.service.api.group.v2.MemberRoles
import com.cmoney.backend2.forumocean.service.api.group.v2.PendingRequests
import com.cmoney.backend2.forumocean.service.api.group.v2.PushType
import com.cmoney.backend2.forumocean.service.api.notify.getcount.GetNotifyCountResponseBody
import com.cmoney.backend2.forumocean.service.api.notifysetting.NotifyPushSetting
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getcommodityrank.GetCommodityRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getexpertmemberrank.GetExpertMemberRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getfansmemberrank.FansMemberRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getsolutionexpertrank.SolutionExpertRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rating.MemberRatingCounter
import com.cmoney.backend2.forumocean.service.api.rating.RatingComment
import com.cmoney.backend2.forumocean.service.api.rating.ReviewRequest
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.relationship.getrelationshipwithme.RelationshipWithMe
import com.cmoney.backend2.forumocean.service.api.report.ReportRequestBody
import com.cmoney.backend2.forumocean.service.api.role.GetMembersByRoleResponse
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.response.GroupPositionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.chat.GetGroupBoardArticlesResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.promoted.GetPromotedArticlesResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.recommendations.GetRecommendationResponse
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.spacepin.GetSpaceBoardPinArticlesResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentContent
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.GetCommentsResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.MemberEmojis
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfoV2
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ForumOceanWebImplTest {
    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var forumOceanService: ForumOceanService
    private val jsonParser =
        GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var web: ForumOceanWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        web = ForumOceanWebImpl(
            manager = manager,
            service = forumOceanService,
            jsonParser = jsonParser,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getForumOceanSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
        coEvery {
            manager.getForumOceanSettingAdapter().getPathName()
        } returns EXCEPT_PATH_NAME
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getBanState_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/GetBanState"
        val urlSlot = slot<String>()
        val responseBody = GetBanStateResponseBody(
            isBan = true
        )
        coEvery {
            forumOceanService.getBanState(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(responseBody)

        web.getBanState()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBanState_success() = testScope.runTest {
        val responseBody = GetBanStateResponseBody(
            isBan = true
        )
        coEvery {
            forumOceanService.getBanState(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = web.getBanState()
        Truth.assertThat(result.isSuccess).isTrue()
        val response = result.getOrThrow()
        Truth.assertThat(response.isBan).isTrue()
    }

    @Test
    fun getBanState_failure() = testScope.runTest {
        val errorBody = CMoneyError(
            detail = CMoneyError.Detail(
                code = 101,
                message = "無授權"
            )
        )
        val jsonBody = jsonParser.toJson(errorBody).toResponseBody()
        coEvery {
            forumOceanService.getBanState(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, jsonBody)

        val result = web.getBanState()
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(101)
        Truth.assertThat(exception.message).isEqualTo("無授權")
    }

    @Test
    fun `createPersonalArticle_note_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/note"
        val urlSlot = slot<String>()
        val responseBody = CreatePersonalArticleResponseBody(articleId = 1L)
        val createContent = Content.PersonalArticle.Note(
            text = "發筆記",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createPersonalArticle(body = createContent)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createPersonalArticle_note_success() = testScope.runTest {
        val responseBody = CreatePersonalArticleResponseBody(articleId = 1L)
        val createContent = Content.PersonalArticle.Note(
            text = "發筆記",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createPersonalArticle(body = createContent)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1L)
    }

    @Test(expected = HttpException::class)
    fun createPersonalArticle_note_failure() = testScope.runTest {
        val createContent = Content.PersonalArticle.Note(
            text = "發筆記",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createPersonalArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createPersonalArticle_columnist_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/columnist"
        val urlSlot = slot<String>()
        val responseBody = CreatePersonalArticleResponseBody(articleId = 1)
        val createContent = Content.PersonalArticle.Columnist(
            text = "發專欄文章",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createPersonalArticle(body = createContent)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createPersonalArticle_columnist_success() = testScope.runTest {
        val responseBody = CreatePersonalArticleResponseBody(articleId = 1)
        val createContent = Content.PersonalArticle.Columnist(
            text = "發專欄文章",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createPersonalArticle(body = createContent)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun createPersonalArticle_columnist_failure() = testScope.runTest {
        val createContent = Content.PersonalArticle.Columnist(
            text = "發專欄文章",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createPersonalArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createArticle_general_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/Create"
        val urlSlot = slot<String>()
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.General(
            title = "文章標題",
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createArticle(
            body = createContent
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticle_general_success() = testScope.runTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.General(
            title = "文章標題",
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun createArticle_general_failure() = testScope.runTest {
        val createContent = Content.Article.General(
            title = "文章標題",
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createArticle_group_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/Create"
        val urlSlot = slot<String>()
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Group(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            groupId = 164656464,
            position = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createArticle(
            body = createContent
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticle_group_success() = testScope.runTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Group(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            groupId = 164656464,
            position = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun createArticle_group_failure() = testScope.runTest {
        val createContent = Content.Article.Group(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            groupId = 164656464,
            position = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createArticle_shared_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/Create"
        val urlSlot = slot<String>()
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Shared(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            sharedPostsArticleId = 13243543,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createArticle(
            body = createContent
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticle_shared_success() = testScope.runTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Shared(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            sharedPostsArticleId = 13243543,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun createArticle_shared_failure() = testScope.runTest {
        val createContent = Content.Article.Shared(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            sharedPostsArticleId = 13243543,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createArticle_column_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/article/columnist"
        val urlSlot = slot<String>()
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Column(
            title = "文章標題",
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createArticle(
            body = createContent
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticle_column_success() = testScope.runTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Column(
            title = "文章標題",
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun createArticle_column_failure() = testScope.runTest {
        val createContent = Content.Article.Column(
            title = "文章標題",
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createArticle(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createQuestion_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/CreateQuestion"
        val urlSlot = slot<String>()
        val responseBody = CreateQuestionResponseBody(articleId = 1)
        val createContent = Content.Question(
            text = "發表問答",
            multiMedia = null,
            commodityTags = null,
            anonymous = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createQuestion(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        web.createQuestion(
            body = createContent
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createQuestion_success() = testScope.runTest {
        val responseBody = CreateQuestionResponseBody(articleId = 1)
        val createContent = Content.Question(
            text = "發表問答",
            multiMedia = null,
            commodityTags = null,
            anonymous = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createQuestion(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createQuestion(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun createQuestion_failure() = testScope.runTest {
        val createContent = Content.Question(
            text = "發表問答",
            multiMedia = null,
            commodityTags = null,
            anonymous = null,
            topics = null,
            openGraph = null
        )
        coEvery {
            forumOceanService.createQuestion(
                url = any(),
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createQuestion(
            body = createContent
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `updateArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/Update/100"
        val urlSlot = slot<String>()
        val helper = UpdateArticleHelper()
        helper.deleteMultiMedia()
        coEvery {
            forumOceanService.updateArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = helper.create()
            )
        } returns Response.success<Void>(204, null)
        web.updateArticle(100, helper)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateArticle_success() = testScope.runTest {
        val helper = UpdateArticleHelper()
        helper.deleteMultiMedia()
        coEvery {
            forumOceanService.updateArticle(
                url = any(),
                authorization = any(),
                body = helper.create()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateArticle(100, helper)

        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun updateArticle_failure_403() = testScope.runTest {
        val helper = UpdateArticleHelper()
        helper.deleteMultiMedia()
        coEvery {
            forumOceanService.updateArticle(
                url = any(),
                authorization = any(),
                body = helper.create()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.updateArticle(100, helper)

        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun updateArticle_failure_404() = testScope.runTest {
        coEvery {
            forumOceanService.updateArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.updateArticle(132434, UpdateArticleHelper())

        Truth.assertThat(result.isSuccess).isFalse()
        Truth.assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
        Truth.assertThat((result.exceptionOrNull() as? HttpException)?.code()).isEqualTo(404)
    }

    @Test
    fun `deleteArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/Delete/100"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteArticle(100)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }


    @Test
    fun deleteArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteArticle(100)

        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun deleteArticle_failure_403() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteArticle(100)

        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test(expected = HttpException::class)
    fun deleteArticle_failure_404() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.deleteArticle(132434)

        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `deleteArticleV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/100"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteArticleV2(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteArticleV2("100")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteArticleV2_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticleV2(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteArticleV2("100")

        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun deleteArticleV2_failure_403() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticleV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteArticleV2("100")

        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun deleteArticleV2_failure_404() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticleV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.deleteArticleV2("132434")

        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getMemberStatistics_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Member/Info"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMemberStatistics(
                url = capture(urlSlot),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(
            listOf(
                GetMemberStatisticsResponseBody(
                    totalCountArticle = 6,
                    totalCountReaction = 3,
                    memberId = 100,
                    totalCountFollowing = 16,
                    totalCountFollower = 10,
                    isBan = false
                )
            )
        )
        web.getMemberStatistics(listOf(100))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberStatistics_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberStatistics(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(
            listOf(
                GetMemberStatisticsResponseBody(
                    totalCountArticle = 6,
                    totalCountReaction = 3,
                    memberId = 100,
                    totalCountFollowing = 16,
                    totalCountFollower = 10,
                    isBan = false
                )
            )
        )
        val result = web.getMemberStatistics(listOf(100))

        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().size).isEqualTo(1)
        Truth.assertThat(result.getOrThrow().first().memberId).isEqualTo(100)
        Truth.assertThat(result.getOrThrow().first().totalCountArticle).isEqualTo(6)
        Truth.assertThat(result.getOrThrow().first().totalCountReaction).isEqualTo(3)
        Truth.assertThat(result.getOrThrow().first().totalCountFollowing).isEqualTo(16)
        Truth.assertThat(result.getOrThrow().first().totalCountFollower).isEqualTo(10)
    }

    @Test(expected = HttpException::class)
    fun getMemberStatistics_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberStatistics(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getMemberStatistics(listOf(100))

        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getChannelsArticleByWeight_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Channel/GetChannelsArticleByWeight"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getChannelsArticleByWeight(
                url = capture(urlSlot),
                authorization = any(),
                channelNameList = any(),
                startScore = any(),
                count = any()
            )
        } returns Response.success(
            listOf(
                ArticleResponseBody.UnknownArticleResponseBody(
                    articleContent = null,
                    createTime = null,
                    id = null,
                    modifyTime = null,
                    reactionState = null,
                    reaction = mapOf(),
                    reactionCount = null,
                    collected = null,
                    collectCount = null,
                    myCommentIndex = listOf(),
                    commentCount = null,
                    shareCount = null,
                    interested = null,
                    interestCount = null,
                    rewardPoints = null,
                    donateCount = null,
                    voteCount = null,
                    voteStatus = null,
                    weight = null,
                    totalReportCount = null,
                    report = null,
                    commentDeletedCount = null,
                    donate = null,
                    isPromotedArticle = null,
                    isPinnedPromotedArticle = null
                )
            )
        )
        web.getChannelsArticleByWeight(
            channelNameBuilderList = listOf(),
            weight = 0,
            count = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getChannelsArticleByWeight_success() = testScope.runTest {
        coEvery {
            forumOceanService.getChannelsArticleByWeight(
                url = any(),
                authorization = any(),
                channelNameList = any(),
                startScore = any(),
                count = any()
            )
        } returns Response.success(
            listOf(
                ArticleResponseBody.UnknownArticleResponseBody(
                    articleContent = null,
                    createTime = null,
                    id = null,
                    modifyTime = null,
                    reactionState = null,
                    reaction = mapOf(),
                    reactionCount = null,
                    collected = null,
                    collectCount = null,
                    myCommentIndex = listOf(),
                    commentCount = null,
                    shareCount = null,
                    interested = null,
                    interestCount = null,
                    rewardPoints = null,
                    donateCount = null,
                    voteCount = null,
                    voteStatus = null,
                    weight = null,
                    totalReportCount = null,
                    report = null,
                    commentDeletedCount = null,
                    donate = null,
                    isPromotedArticle = null,
                    isPinnedPromotedArticle = null
                )
            )
        )
        val result = web.getChannelsArticleByWeight(
            listOf(),
            0,
            0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().size).isEqualTo(1)
    }

    @Test
    fun getChannelsArticleByWeight_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getChannelsArticleByWeight(
                url = any(),
                authorization = any(),
                channelNameList = any(),
                startScore = any(),
                count = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getChannelsArticleByWeight(
            listOf(),
            0,
            0
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `createCollection_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Collection/Create/1000"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createCollection(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.createCollection(1000)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createCollection_success() = testScope.runTest {
        coEvery {
            forumOceanService.createCollection(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.createCollection(1000)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun createCollection_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createCollection(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createCollection(1000)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `deleteCollection_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Collection/Delete/1000"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteCollection(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteCollection(1000)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteCollection_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteCollection(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteCollection(1000)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun deleteCollection_failure() = testScope.runTest {
        coEvery {
            forumOceanService.deleteCollection(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteCollection(1000)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getGroupManagerComments_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Comment/GetGroupManagerComments/10101"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupManagerComments(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            listOf(
                CommentResponseBody(
                    id = 2, content = CommentContent(
                        creatorId = null,
                        text = null,
                        multiMedia = listOf(),
                        position = null,
                        commentState = null,
                        reactionState = null,
                        reaction = mapOf(),
                        createTime = null,
                        modifyTime = null,
                        report = null,
                        hasAuthToReadReply = null
                    )
                ),
                CommentResponseBody(
                    id = 3, content = CommentContent(
                        creatorId = null,
                        text = null,
                        multiMedia = listOf(),
                        position = null,
                        commentState = null,
                        reactionState = null,
                        reaction = mapOf(),
                        createTime = null,
                        modifyTime = null,
                        report = null,
                        hasAuthToReadReply = null
                    )
                )
            )
        )
        web.getGroupManagerComments(10101)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupManagerComments_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupManagerComments(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            listOf(
                CommentResponseBody(
                    id = 2, content = CommentContent(
                        creatorId = null,
                        text = null,
                        multiMedia = listOf(),
                        position = null,
                        commentState = null,
                        reactionState = null,
                        reaction = mapOf(),
                        createTime = null,
                        modifyTime = null,
                        report = null,
                        hasAuthToReadReply = null
                    )
                ),
                CommentResponseBody(
                    id = 3, content = CommentContent(
                        creatorId = null,
                        text = null,
                        multiMedia = listOf(),
                        position = null,
                        commentState = null,
                        reactionState = null,
                        reaction = mapOf(),
                        createTime = null,
                        modifyTime = null,
                        report = null,
                        hasAuthToReadReply = null
                    )
                )
            )
        )
        val result = web.getGroupManagerComments(10101)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(2)
    }

    @Test(expected = HttpException::class)
    fun getGroupManagerComments_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupManagerComments(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getGroupManagerComments(10101)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `updateComment_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Comment/Update/1000/2000"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.updateComment(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateComment(
            articleId = 1000,
            commentId = 2000,
            helper = UpdateCommentHelper()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateComment_success() = testScope.runTest {
        coEvery {
            forumOceanService.updateComment(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateComment(
            articleId = 1000,
            commentId = 2000,
            helper = UpdateCommentHelper()
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun updateComment_failure() = testScope.runTest {
        coEvery {
            forumOceanService.updateComment(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.updateComment(
            articleId = 1000,
            commentId = 2000,
            helper = UpdateCommentHelper()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `deleteCommentV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123-1"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteCommentV2(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteCommentV2("123-1")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }


    @Test
    fun deleteCommentV2_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteCommentV2(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteCommentV2("123-1")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun deleteCommentV2_failure() = testScope.runTest {
        coEvery {
            forumOceanService.deleteCommentV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteCommentV2("123-1")
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createReaction_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/1010-1/Emoji/1"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createReaction(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.createReaction(id = "1010-1", type = ReactionType.LIKE)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createReaction_success() = testScope.runTest {
        coEvery {
            forumOceanService.createReaction(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.createReaction(id = "1010-1", type = ReactionType.LIKE)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun createReaction_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createReaction(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createReaction("1010-1", ReactionType.LIKE)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getReactionDetailV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123-1/Emoji/Detail"
        val urlSlot = slot<String>()
        val reactionTypeList = listOf(ReactionType.LIKE, ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getReactionDetailV2(
                url = capture(urlSlot),
                authorization = any(),
                emojiTypes = reactionTypeList.joinToString { it.value.toString() },
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            MemberEmojis(
                listOf(
                    ReactionInfoV2(
                        memberId = 67, emoji = ""
                    ),
                    ReactionInfoV2(
                        memberId = 68, emoji = ""
                    )
                )
            )
        )
        web.getReactionDetailV2("123-1", reactionTypeList, 0, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getReactionDetailV2_success() = testScope.runTest {
        val reactionTypeList = listOf(ReactionType.LIKE, ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getReactionDetailV2(
                url = any(),
                authorization = any(),
                emojiTypes = reactionTypeList.joinToString { it.value.toString() },
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            MemberEmojis(
                listOf(
                    ReactionInfoV2(
                        memberId = 67, emoji = ""
                    ),
                    ReactionInfoV2(
                        memberId = 68, emoji = ""
                    )
                )
            )
        )
        val result = web.getReactionDetailV2("123-1", reactionTypeList, 0, 20)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().memberEmojis).hasSize(2)
    }


    @Test(expected = HttpException::class)
    fun getReactionDetailV2_failure() = testScope.runTest {
        val reactionTypeList = listOf(ReactionType.LIKE, ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getReactionDetailV2(
                url = any(),
                authorization = any(),
                emojiTypes = reactionTypeList.joinToString { it.value.toString() },
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getReactionDetailV2("123-1", reactionTypeList, 0, 20)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `deleteReaction_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123-1/Emoji"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteReaction(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteReaction(id = "123-1")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteReaction_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteReaction(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteReaction(id = "123-1")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun deleteReaction_failure_ServerException() = testScope.runTest {
        val error = CMoneyError(
            detail = CMoneyError.Detail(
                message = "不存在的文章"
            )
        )
        val errorBody = jsonParser.toJson(error).toResponseBody()
        coEvery {
            forumOceanService.deleteReaction(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, errorBody)
        val result = web.deleteReaction(id = "123-1")
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createArticleInterest_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Interactive/Interest/10101"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createArticleInterest(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.createArticleInterest(10101)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticleInterest_success() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleInterest(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.createArticleInterest(10101)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun createArticleInterest_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleInterest(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createArticleInterest(10101)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createArticleDonate_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Interactive/Donate/10101"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createArticleDonate(
                url = capture(urlSlot),
                authorization = any(),
                donateValue = any()
            )
        } returns Response.success<Void>(204, null)
        web.createArticleDonate(10101, 1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticleDonate_success() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleDonate(
                url = any(),
                authorization = any(),
                donateValue = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.createArticleDonate(10101, 1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun createArticleDonate_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleDonate(
                url = any(),
                authorization = any(),
                donateValue = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createArticleDonate(10101, 1)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getArticleDonate_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Interactive/GetDonate/10101"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getArticleDonate(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                DonateInfo(memberId = 1000, donateValue = 10),
                DonateInfo(memberId = 1001, donateValue = 1),
                DonateInfo(memberId = 1002, donateValue = 100)
            )
        )
        web.getArticleDonate(10101, 0, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getArticleDonate_success() = testScope.runTest {
        coEvery {
            forumOceanService.getArticleDonate(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                DonateInfo(memberId = 1000, donateValue = 10),
                DonateInfo(memberId = 1001, donateValue = 1),
                DonateInfo(memberId = 1002, donateValue = 100)
            )
        )
        val result = web.getArticleDonate(10101, 0, 20)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(3)
        Truth.assertThat(result.getOrThrow()[2].donateValue).isEqualTo(100)
    }

    @Test(expected = HttpException::class)
    fun getArticleDonate_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.getArticleDonate(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getArticleDonate(10101, 0, 20)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/GetGroup/1161616"
        val urlSlot = slot<String>()
        val groupId = 1161616L
        coEvery {
            forumOceanService.getGroup(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            GroupResponseBody(
                description = "社團敘述",
                id = groupId,
                imageUrl = null,
                isPublic = null,
                joinType = null,
                name = "社團名稱",
                ownerId = null,
                searchable = null,
                memberCount = null,
                groupPosition = null,
                articleCount = null,
                unreadCount = null,
                lastViewTime = null
            )
        )
        web.getGroup(groupId)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroup_success() = testScope.runTest {
        val groupId = 1161616L
        coEvery {
            forumOceanService.getGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            GroupResponseBody(
                description = "社團敘述",
                id = groupId,
                imageUrl = null,
                isPublic = null,
                joinType = null,
                name = "社團名稱",
                ownerId = null,
                searchable = null,
                memberCount = null,
                groupPosition = null,
                articleCount = null,
                unreadCount = null,
                lastViewTime = null
            )
        )
        val result = web.getGroup(groupId)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().id).isEqualTo(groupId)
    }

    @Test(expected = HttpException::class)
    fun getGroup_failure_HttpException() = testScope.runTest {
        val groupId = 1161616L
        coEvery {
            forumOceanService.getGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroup(groupId)
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getUserOwnGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/GetGroupsWithPosition"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any()
            )
        } returns Response.success(
            listOf(
                GroupResponseBody(
                    description = null,
                    id = 1,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                ),
                GroupResponseBody(
                    description = null,
                    id = 2,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                )
            )
        )
        web.getGroupsByPosition(
            ownId = 1321321,
            offset = 0,
            fetch = 20,
            positions = listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUserOwnGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any()
            )
        } returns Response.success(
            listOf(
                GroupResponseBody(
                    description = null,
                    id = 1,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                ),
                GroupResponseBody(
                    description = null,
                    id = 2,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                )
            )
        )
        val result = web.getGroupsByPosition(
            ownId = 1321321,
            offset = 0,
            fetch = 20,
            positions = listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT)
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(2)
    }

    @Test(expected = HttpException::class)
    fun getUserOwnGroup_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getGroupsByPosition(
            ownId = 1321321,
            offset = 0,
            fetch = 20,
            positions = listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT)
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `readMemberManagedGroups_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/GetGroupsWithPosition"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any()
            )
        } returns Response.success(
            listOf(
                GroupResponseBody(
                    description = null,
                    id = 1,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                )
            )
        )
        web.getMemberManagedGroups(memberId = 1, offset = 0, fetch = 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun readMemberManagedGroups_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any()
            )
        } returns Response.success(
            listOf(
                GroupResponseBody(
                    description = null,
                    id = 1,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                )
            )
        )
        val result = web.getMemberManagedGroups(
            memberId = 1,
            offset = 0,
            fetch = 20
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().first().id).isEqualTo(1)
    }

    @Test(expected = HttpException::class)
    fun readMemberManagedGroups_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberManagedGroups(
            memberId = 1,
            offset = 0,
            fetch = 20
        )
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `getMemberBelongGroups_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/GetGroupsWithPosition"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = listOf(
                    GroupPosition.NORMAL,
                    GroupPosition.MANAGEMENT,
                    GroupPosition.PRESIDENT
                ).map { it.position }.sum(),
                includeAppGroup = any()
            )
        } returns Response.success(
            listOf(
                GroupResponseBody(
                    description = null,
                    id = 1,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                )
            )
        )
        web.getMemberBelongGroups(memberId = 1231321, offset = 0, fetch = 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberBelongGroups_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = listOf(
                    GroupPosition.NORMAL,
                    GroupPosition.MANAGEMENT,
                    GroupPosition.PRESIDENT
                ).map { it.position }.sum(),
                includeAppGroup = any()
            )
        } returns Response.success(
            listOf(
                GroupResponseBody(
                    description = null,
                    id = 1,
                    imageUrl = null,
                    isPublic = null,
                    joinType = null,
                    name = null,
                    ownerId = null,
                    searchable = null,
                    memberCount = null,
                    groupPosition = null,
                    articleCount = null,
                    unreadCount = null,
                    lastViewTime = null
                )
            )
        )
        val result = web.getMemberBelongGroups(
            memberId = 1231321,
            offset = 0,
            fetch = 20
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(1)
    }

    @Test(expected = HttpException::class)
    fun getMemberBelongGroups_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = listOf(
                    GroupPosition.NORMAL,
                    GroupPosition.MANAGEMENT,
                    GroupPosition.PRESIDENT
                ).map { it.position }.sum(),
                includeAppGroup = any()
            )
        } returns Response.error(402, "".toResponseBody())
        val result = web.getMemberBelongGroups(
            memberId = 1231321,
            offset = 0,
            fetch = 20
        )
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `getMemberJoinAnyGroups_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/GetMemberJoinAnyGroups"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMemberJoinAnyGroups(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success(
            GetMemberJoinAnyGroupsResponseBody(
                isJoin = true
            )
        )
        web.getMemberJoinAnyGroups(23454734)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberJoinAnyGroups_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberJoinAnyGroups(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success(
            GetMemberJoinAnyGroupsResponseBody(
                isJoin = true
            )
        )
        val result = web.getMemberJoinAnyGroups(23454734)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().isJoin).isTrue()
    }

    @Test(expected = HttpException::class)
    fun getMemberJoinAnyGroups_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberJoinAnyGroups(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberJoinAnyGroups(23454734)
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `createGroupV1_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Create"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createGroup(
                url = capture(urlSlot),
                authorization = any(),
                groupName = any()
            )
        } returns Response.success(
            CreateGroupResponseBody(100321)
        )
        val result = web.createGroup("社團名稱")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().groupId).isEqualTo(100321)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createGroupV1_success() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                url = any(),
                authorization = any(),
                groupName = any()
            )
        } returns Response.success(
            CreateGroupResponseBody(100321)
        )
        val result = web.createGroup("社團名稱")
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().groupId).isEqualTo(100321)
    }

    @Test(expected = HttpException::class)
    fun createGroupV1_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                url = any(),
                authorization = any(),
                groupName = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroup("社團名稱")
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `updateGroupV1_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Update/10220"
        val urlSlot = slot<String>()
        val updateRequestBody = UpdateGroupRequestBody(
            name = null,
            description = null,
            imageUrl = null,
            isPublic = false,
            searchable = null,
            joinType = null
        )
        coEvery {
            forumOceanService.updateGroupV1(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateGroup(
            groupId = 10220,
            body = updateRequestBody
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateGroupV1_success() = testScope.runTest {
        val updateRequestBody = UpdateGroupRequestBody(
            name = null,
            description = null,
            imageUrl = null,
            isPublic = false,
            searchable = null,
            joinType = null
        )
        coEvery {
            forumOceanService.updateGroupV1(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroup(
            groupId = 10220,
            body = updateRequestBody
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateGroupV1_failure_HttpException() = testScope.runTest {
        val updateRequestBody = UpdateGroupRequestBody(
            name = null,
            description = null,
            imageUrl = null,
            isPublic = false,
            searchable = null,
            joinType = null
        )
        coEvery {
            forumOceanService.updateGroupV1(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroup(
            groupId = 10220,
            body = updateRequestBody
        )
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `transferGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/TransferOwner/2032032"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.transferGroup(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.transferGroup(groupId = 2032032, memberId = 20320)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun transferGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.transferGroup(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.transferGroup(groupId = 2032032, memberId = 20320)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun transferGroup_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.transferGroup(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.transferGroup(groupId = 2032032, memberId = 20320)
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `deleteGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Delete/2020"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteGroup(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteGroup(2020)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteGroup(2020)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = ServerException::class)
    fun deleteGroup_failure_ServerException() = testScope.runTest {
        val error = CMoneyError(
            detail = CMoneyError.Detail(
                message = "沒有權限進行刪除"
            )
        )
        val errorBody = jsonParser.toJson(error)
        coEvery {
            forumOceanService.deleteGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, errorBody.toResponseBody())
        val result = web.deleteGroup(2020)
        Truth.assertThat(result.isFailure).isTrue()
        result.getOrThrow()
    }

    @Test
    fun `join_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/Join/1202020"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.join(
                url = capture(urlSlot),
                authorization = any(),
                reason = any()
            )
        } returns Response.success<Void>(204, null)
        web.join(1202020, "入社理由")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun join_success() = testScope.runTest {
        coEvery {
            forumOceanService.join(
                url = any(),
                authorization = any(),
                reason = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.join(1202020, "入社理由")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = IllegalArgumentException::class)
    fun join_failure_IllegalArgumentException() = testScope.runTest {
        val result = web.join(1202020, "")
        result.getOrThrow()
    }

    @Test
    fun join_failure_HttpException() = testScope.runTest {
        coEvery {
            forumOceanService.join(
                url = any(),
                authorization = any(),
                reason = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.join(1202020, "入社理由")
        Truth.assertThat(result.isFailure).isTrue()
    }


    @Test
    fun `getMembers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/GetMembers/132132"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMembers(
                url = capture(urlSlot),
                authorization = any(),
                position = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getMembers(132132, 0, 20, GroupPosition.values().toList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMembers_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMembers(
                url = any(),
                authorization = any(),
                position = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                GroupMember(memberId = 1, position = GroupPositionInfo.NORMAL),
                GroupMember(memberId = 2, position = GroupPositionInfo.NORMAL),
                GroupMember(memberId = 3, position = GroupPositionInfo.PRESIDENT)
            )
        )
        val result = web.getMembers(132132, 0, 20, GroupPosition.values().toList())
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().find { it.position == GroupPositionInfo.PRESIDENT }).isNotNull()
    }

    @Test
    fun getMembers_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getMembers(
                url = any(),
                authorization = any(),
                position = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMembers(132132, 0, 20, GroupPosition.values().toList())
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getApprovals_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/GetApprovals/1321684"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getApprovals(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getApprovals(1321684, 0, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getApprovals_success() = testScope.runTest {
        coEvery {
            forumOceanService.getApprovals(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                GroupPendingApproval(
                    memberId = 1, createTimestamp = 1615530481, reason = "加入社團理由"
                ),
                GroupPendingApproval(
                    memberId = 2, createTimestamp = 1615630481, reason = "加入社團理由2"
                )
            )
        )
        val result = web.getApprovals(1321684, 0, 20)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(2)
    }

    @Test
    fun getApprovals_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getApprovals(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getApprovals(1321684, 0, 20)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `approval_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/Approval/132112"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.approval(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                isAgree = any()
            )
        } returns Response.success<Void>(204, null)
        web.approval(132112, 213213, true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun approval_success() = testScope.runTest {
        coEvery {
            forumOceanService.approval(
                url = any(),
                authorization = any(),
                memberId = any(),
                isAgree = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.approval(132112, 213213, true)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun approval_failure() = testScope.runTest {
        coEvery {
            forumOceanService.approval(
                url = any(),
                authorization = any(),
                memberId = any(),
                isAgree = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.approval(132112, 213213, true)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `changeGroupMemberPosition_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/ChangeGroupMemberPosition/1321321"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.changeGroupMemberPosition(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                position = any()
            )
        } returns Response.success<Void>(204, null)
        web.changeGroupMemberPosition(1321321, 1231, GroupPosition.PRESIDENT)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun changeGroupMemberPosition_success() = testScope.runTest {
        coEvery {
            forumOceanService.changeGroupMemberPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                position = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.changeGroupMemberPosition(1321321, 1231, GroupPosition.PRESIDENT)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun changeGroupMemberPosition_failure() = testScope.runTest {
        coEvery {
            forumOceanService.changeGroupMemberPosition(
                url = any(),
                authorization = any(),
                memberId = any(),
                position = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.changeGroupMemberPosition(1321321, 1231, GroupPosition.PRESIDENT)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `kick_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/Kick/13213"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.kick(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        web.kick(13213, 1321)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun kick_success() = testScope.runTest {
        coEvery {
            forumOceanService.kick(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.kick(13213, 1321)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun kick_failure() = testScope.runTest {
        coEvery {
            forumOceanService.kick(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.kick(13213, 1321)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `leave_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/Leave/5050"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.leave(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.leave(5050)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun leave_success() = testScope.runTest {
        coEvery {
            forumOceanService.leave(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.leave(5050)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun leave_failure() = testScope.runTest {
        coEvery {
            forumOceanService.leave(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.leave(5050)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `pinArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/PinArticle/1321342"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.pinArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.pinArticle(1321342)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun pinArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.pinArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.pinArticle(1321342)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun pinArticle_failure() = testScope.runTest {
        coEvery {
            forumOceanService.pinArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.pinArticle(1321342)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `unpinArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/UnpinArticle/1321342"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unpinArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.unpinArticle(1321342)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun unpinArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.unpinArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unpinArticle(1321342)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun unpinArticle_failure() = testScope.runTest {
        coEvery {
            forumOceanService.unpinArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unpinArticle(1321342)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getPushDefaultSetting_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/NotifySetting/GetPushDefaultSetting"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getPushDefaultSetting(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(200, emptyList())
        web.getPushDefaultSetting()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getPushDefaultSetting_success() = testScope.runTest {
        val responseBody = listOf(
            NotifyPushSetting(
                enable = false,
                notifySettingKey = null
            )
        )
        coEvery {
            forumOceanService.getPushDefaultSetting(
                url = any(),
                authorization = any()
            )
        } returns Response.success(200, responseBody)
        val result = web.getPushDefaultSetting()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPushDefaultSetting_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getPushDefaultSetting(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getPushDefaultSetting()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getUserNotifySetting_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/NotifySetting/Get"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getUserNotifySetting(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(200, emptyList())
        web.getUserNotifySetting()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUserNotifySetting_success() = testScope.runTest {
        val responseBody = listOf(
            NotifyPushSetting(
                enable = false,
                notifySettingKey = null
            )
        )
        coEvery {
            forumOceanService.getUserNotifySetting(
                url = any(),
                authorization = any()
            )
        } returns Response.success(200, responseBody)
        val result = web.getUserNotifySetting()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getUserNotifySetting_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getUserNotifySetting(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getUserNotifySetting()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `setNotifySetting_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/NotifySetting/Set"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.setNotifySetting(
                url = capture(urlSlot),
                authorization = any(),
                notifyType = any(),
                subType = any(),
                enable = any()
            )
        } returns Response.success<Void>(204, null)
        web.setNotifySetting(notifyType = "", subType = "", enable = false)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setNotifySetting_success() = testScope.runTest {
        coEvery {
            forumOceanService.setNotifySetting(
                url = any(),
                authorization = any(),
                notifyType = any(),
                subType = any(),
                enable = any()
            )
        } returns  Response.success<Void>(204, null)
        val result = web.setNotifySetting(notifyType = "", subType = "", enable = false)
            .getOrThrow()
        Truth.assertThat(result).isEqualTo(Unit)
    }

    @Test
    fun setNotifySetting_failure() = testScope.runTest {
        coEvery {
            forumOceanService.setNotifySetting(
                url = any(),
                authorization = any(),
                notifyType = any(),
                subType = any(),
                enable = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.setNotifySetting(notifyType = "", subType = "", enable = false)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getOfficials_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Official/GetOfficials"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getOfficials(
                url = capture(urlSlot),
                authorization = any(),
                offset = 0,
                fetch = 20
            )
        } returns Response.success(emptyList())
        web.getOfficials(0, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getOfficials_success() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficials(
                url = any(),
                authorization = any(),
                offset = 0,
                fetch = 20
            )
        } returns Response.success(
            listOf(
                OfficialChannelInfo(
                    id = 213213,
                    name = "",
                    description = "",
                    imageUrl = "",
                    typeName = "",
                    subscribeCount = 0
                ),
                OfficialChannelInfo(
                    id = 1321321,
                    name = "",
                    description = "",
                    imageUrl = "",
                    typeName = "",
                    subscribeCount = 0
                )
            )
        )
        val result = web.getOfficials(0, 20)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(2)
    }

    @Test
    fun getOfficials_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficials(
                url = any(),
                authorization = any(),
                offset = 0,
                fetch = 20
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getOfficials(0, 20)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getOfficialsByIds_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Official/GetOfficialsByIds"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getOfficialsByIds(
                url = capture(urlSlot),
                authorization = any(),
                officialIds = any()
            )
        } returns Response.success(emptyList())
        web.getOfficialsByIds(emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun `getOfficialsByIds_input official id is 1 2 3 4_official id string is 1,2,3,4`() = testScope.runTest {
        val officialIdsSlot = slot<String>()
        coEvery {
            forumOceanService.getOfficialsByIds(
                url = any(),
                authorization = any(),
                officialIds = capture(officialIdsSlot)
            )
        } returns Response.success(emptyList())
        web.getOfficialsByIds(listOf(1,2,3,4))
        Truth.assertThat(officialIdsSlot.captured).isEqualTo("1,2,3,4")
    }

    @Test
    fun getOfficialsByIds_success() = testScope.runTest {
        val responseBody = listOf(
            OfficialChannelInfo(
                id = 123,
                name = null,
                description = null,
                imageUrl = null,
                typeName = null,
                subscribeCount = null
            )
        )
        coEvery {
            forumOceanService.getOfficialsByIds(
                url = any(),
                authorization = any(),
                officialIds = any()
            )
        } returns Response.success(responseBody)
        val result = web.getOfficialsByIds(listOf(1,2,3,4))
            .getOrThrow()
        Truth.assertThat(result[0].id).isEqualTo(123)
    }

    @Test
    fun getOfficialsByIds_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficialsByIds(
                url = any(),
                authorization = any(),
                officialIds = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getOfficialsByIds(emptyList())
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getOfficialsByKeyword_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Official/GetOfficialsByKeyword"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getOfficialsByKeyword(
                url = capture(urlSlot),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getOfficialsByKeyWord(keyword = "", offset = 0, fetch = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getOfficialsByKeyword_success() = testScope.runTest {
        val responseBody = listOf(
            OfficialChannelInfo(
                id = 123,
                name = null,
                description = null,
                imageUrl = null,
                typeName = null,
                subscribeCount = null
            )
        )
        coEvery {
            forumOceanService.getOfficialsByKeyword(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(responseBody)
        val result = web.getOfficialsByKeyWord(keyword = "", offset = 0, fetch = 0)
            .getOrThrow()
        Truth.assertThat(result[0].id).isEqualTo(123)
    }

    @Test
    fun getOfficialsByKeyword_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficialsByKeyword(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getOfficialsByKeyWord(keyword = "", offset = 0, fetch = 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupsByKeyword_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/GetGroupsByKeyword"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupsByKeyword(
                url = capture(urlSlot),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getGroupsByKeyword(keyword = "", offset = 0, fetch = 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupsByKeyword_success() = testScope.runTest {
        val responseBody = listOf(
            GroupResponseBody(
                description = null,
                id = 123,
                imageUrl = null,
                isPublic = null,
                joinType = null,
                name = null,
                ownerId = null,
                searchable = null,
                memberCount = null,
                groupPosition = null,
                articleCount = null,
                unreadCount = null,
                lastViewTime = null
            )
        )
        coEvery {
            forumOceanService.getGroupsByKeyword(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(responseBody)
        val result = web.getGroupsByKeyword(keyword = "", offset = 0, fetch = 0)
            .getOrThrow()
        Truth.assertThat(result[0].id).isEqualTo(123)
    }

    @Test
    fun getGroupsByKeyword_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsByKeyword(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupsByKeyword(keyword = "", offset = 0, fetch = 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getOfficialSubscribedCount_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/OfficialSubscriber/GetOfficialSubscribedCount/21321"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getOfficialSubscribedCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            GetOfficialSubscribedCountResponseBody(132132)
        )
        web.getOfficialSubscribedCount(21321)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getOfficialSubscribedCount_success() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficialSubscribedCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            GetOfficialSubscribedCountResponseBody(132132)
        )
        val result = web.getOfficialSubscribedCount(21321)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().count).isEqualTo(132132)
    }

    @Test
    fun getOfficialSubscribedCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficialSubscribedCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getOfficialSubscribedCount(21321)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getSubscribedCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/OfficialSubscriber/GetSubscribedCount"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSubscribedCount(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success(GetSubscribedCountResponseBody(2134979))
        web.getSubscribedCount(1465)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSubscribedCount_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribedCount(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success(GetSubscribedCountResponseBody(2134979))
        val result = web.getSubscribedCount(1465)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().count).isEqualTo(2134979)
    }

    @Test
    fun getSubscribedCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribedCount(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSubscribedCount(1465)
        Truth.assertThat(result.isFailure).isTrue()
    }


    @Test
    fun `getSubscribed_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/OfficialSubscriber/GetSubscribeds"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSubscribed(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(1, 2, 3, 4, 5, 6)
        )
        web.getSubscribed(21321, 0, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSubscribed_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribed(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(1, 2, 3, 4, 5, 6)
        )
        val result = web.getSubscribed(21321, 0, 20)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(6)
    }

    @Test
    fun getSubscribed_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribed(
                url = any(),
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSubscribed(21321, 0, 20)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `subscribe_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/OfficialSubscriber/Subscribe/21321"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.subscribe(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.subscribe(21321)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun subscribe_success() = testScope.runTest {
        coEvery {
            forumOceanService.subscribe(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.subscribe(21321)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun subscribe_failure() = testScope.runTest {
        coEvery {
            forumOceanService.subscribe(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.subscribe(21321)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `unsubscribe_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/OfficialSubscriber/Unsubscribe/3213489"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unsubscribe(
                url = capture(urlSlot),
                authorization = any(),
                officialId = any()
            )
        } returns Response.success<Void>(204, null)
        web.unsubscribe(3213489)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    fun unsubscribe_success() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribe(
                url = any(),
                authorization = any(),
                officialId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unsubscribe(3213489)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun unsubscribe_failure() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribe(
                url = any(),
                authorization = any(),
                officialId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unsubscribe(3213489)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `unsubscribeAll_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/OfficialSubscriber/UnsubscribeAll"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unsubscribeAll(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.unsubscribeAll()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun unsubscribeAll_success() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribeAll(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unsubscribeAll()
        Truth.assertThat(result.isSuccess).isTrue()
    }


    @Test
    fun unsubscribeAll_failure() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribeAll(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unsubscribeAll()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getFollowingList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/GetFollowingList/4564"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getFollowingList(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any(),
            )
        } returns Response.success(
            listOf(1, 2, 3, 4)
        )
        web.getFollowingList(4564, 0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getFollowingList_success() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowingList(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any(),
            )
        } returns Response.success(
            listOf(1, 2, 3, 4)
        )
        val result = web.getFollowingList(4564, 0, 10)
        Truth.assertThat(result.getOrThrow()).hasSize(4)
    }

    @Test
    fun getFollowingList_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowingList(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getFollowingList(4564, 0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getFollowers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/GetFollowers/43241321"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getFollowers(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf(1, 2, 3, 4))
        web.getFollowers(43241321, 0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getFollowers_success() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowers(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf(1, 2, 3, 4))
        val result = web.getFollowers(43241321, 0, 10)
        Truth.assertThat(result.getOrThrow()).hasSize(4)
    }

    @Test
    fun getFollowers_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowers(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getFollowers(43241321, 0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `follow_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/Follow"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.follow(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        web.follow(1324324)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun follow_success() = testScope.runTest {
        coEvery {
            forumOceanService.follow(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.follow(1324324)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun follow_failure() = testScope.runTest {
        coEvery {
            forumOceanService.follow(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.follow(1324324)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `unfollow_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/Unfollow"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unfollow(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        web.unfollow(21324324)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun unfollow_success() = testScope.runTest {
        coEvery {
            forumOceanService.unfollow(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unfollow(21324324)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun unfollow_failure() = testScope.runTest {
        coEvery {
            forumOceanService.unfollow(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unfollow(21324324)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `block_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/Block"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.block(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        web.block(2344654)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun block_success() = testScope.runTest {
        coEvery {
            forumOceanService.block(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.block(2344654)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun block_failure() = testScope.runTest {
        coEvery {
            forumOceanService.block(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.block(2344654)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `unblock_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/Unblock"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unblock(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        web.unblock(342321)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    fun unblock_success() = testScope.runTest {
        coEvery {
            forumOceanService.unblock(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unblock(342321)
        Truth.assertThat(result.isSuccess)
    }


    @Test
    fun unblock_failure() = testScope.runTest {
        coEvery {
            forumOceanService.unblock(
                url = any(),
                authorization = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unblock(342321)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getBlockingList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/GetBlockingList"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getBlockingList(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf(1, 2, 3, 4, 5))
        web.getBlockingList(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBlockingList_success() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockingList(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf(1, 2, 3, 4, 5))
        val result = web.getBlockingList(0, 10)
        Truth.assertThat(result.getOrThrow()).hasSize(5)
    }

    @Test
    fun getBlockingList_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockingList(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getBlockingList(0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getBlockers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/GetBlockers"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getBlockers(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf(1, 3, 5, 7, 9))
        web.getBlockers(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBlockers_success() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockers(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf(1, 3, 5, 7, 9))
        val result = web.getBlockers(0, 10)
        Truth.assertThat(result.getOrThrow()).hasSize(5)
    }


    @Test
    fun getBlockers_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockers(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getBlockers(0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getRelationshipWithMe_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Relationship/GetRelationshipWithMe"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getRelationshipWithMe(
                url = capture(urlSlot),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(200, emptyList())
        web.getRelationshipWithMe(emptyList())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRelationshipWithMe_success() = testScope.runTest {
        val responseBody = listOf(
            RelationshipWithMe(
                memberId = 231321,
                relationshipType = null
            )
        )
        coEvery {
            forumOceanService.getRelationshipWithMe(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(200, responseBody)
        val result = web.getRelationshipWithMe(listOf(123))
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getRelationshipWithMe_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getRelationshipWithMe(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRelationshipWithMe(listOf(123))
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `createReport_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Report/Create/231321"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createReport(
                url = capture(urlSlot),
                authorization = any(),
                reasonType = any(),
                commentId = any(),
            )
        } returns Response.success<Void>(204, null)
        web.createReport(231321, 1, null)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createReport_success() = testScope.runTest {
        coEvery {
            forumOceanService.createReport(
                url = any(),
                authorization = any(),
                reasonType = any(),
                commentId = any(),
            )
        } returns Response.success<Void>(204, null)
        val result = web.createReport(231321, 1, null)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun createReport_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createReport(
                url = any(),
                authorization = any(),
                reasonType = any(),
                commentId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createReport(231321, 1, null)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `createReportV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123-1/Report"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createReportV2(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.createReportV2("123-1", 1)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createReportV2_success() = testScope.runTest {
        coEvery {
            forumOceanService.createReportV2(
                url = any(),
                authorization = any(),
                body = ReportRequestBody(0)
            )
        } returns Response.success<Void>(204, null)
        val result = web.createReportV2("123-1", 1)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun createReportV2_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createReportV2(
                url = any(),
                authorization = any(),
                body = ReportRequestBody(0)
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createReportV2("123-1", 1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `deleteReport_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Report/Delete/2136541"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteReport(
                url = capture(urlSlot),
                authorization = any(),
                commentId = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteReport(2136541, commentId = null)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteReport_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteReport(
                url = any(),
                authorization = any(),
                commentId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteReport(2136541, commentId = null)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun deleteReport_failure() = testScope.runTest {
        coEvery {
            forumOceanService.deleteReport(
                url = any(),
                authorization = any(),
                commentId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteReport(2136541, commentId = null)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getMemberIds_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Support/GetMemberIds"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMemberIds(
                url = capture(urlSlot),
                authorization = any(),
                channelIds = any()
            )
        } returns Response.success(emptyList())
        web.getMemberIds(listOf(67, 68))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberIds_success() = testScope.runTest {
        val memberIds: List<Long> = listOf(67, 68)
        coEvery {
            forumOceanService.getMemberIds(
                url = any(),
                authorization = any(),
                channelIds = memberIds.joinToString(",")
            )
        } returns Response.success(
            listOf(
                ChannelIdAndMemberId(
                    channelId = 1979787,
                    memberId = 67
                ),
                ChannelIdAndMemberId(
                    channelId = 2266693,
                    memberId = 68
                )
            )
        )
        val result = web.getMemberIds(memberIds)
        val mappingList = result.getOrThrow().associateBy { it.memberId }
        Truth.assertThat(mappingList[67]?.channelId).isEqualTo(1979787)
        Truth.assertThat(mappingList[68]?.channelId).isEqualTo(2266693)
    }

    @Test
    fun getMemberIds_failure() = testScope.runTest {
        val memberIds: List<Long> = listOf(67, 68)
        coEvery {
            forumOceanService.getMemberIds(
                url = any(),
                authorization = any(),
                channelIds = memberIds.joinToString(",")
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberIds(memberIds)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getChannelIds_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Support/GetChannelIds"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getChannelIds(
                url = capture(urlSlot),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(emptyList())
        web.getChannelIds(listOf(1979787, 2266693))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    fun getChannelIds_success() = testScope.runTest {
        val channelIds: List<Long> = listOf(1979787, 2266693)
        coEvery {
            forumOceanService.getChannelIds(
                url = any(),
                authorization = any(),
                memberIds = channelIds.joinToString(",")
            )
        } returns Response.success(
            listOf(
                ChannelIdAndMemberId(
                    channelId = 1979787,
                    memberId = 67
                ),
                ChannelIdAndMemberId(
                    channelId = 2266693,
                    memberId = 68
                )
            )
        )
        val result = web.getChannelIds(channelIds)
        val mappingList = result.getOrThrow().associateBy { it.channelId }
        Truth.assertThat(mappingList[1979787]?.memberId).isEqualTo(67)
        Truth.assertThat(mappingList[2266693]?.memberId).isEqualTo(68)
    }

    @Test
    fun getChannelIds_failure() = testScope.runTest {
        val channelIds: List<Long> = listOf(1979787, 2266693)
        coEvery {
            forumOceanService.getChannelIds(
                url = any(),
                authorization = any(),
                memberIds = channelIds.joinToString(",")
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getChannelIds(channelIds)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `searchMembers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Support/SearchMember"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.searchMembers(
                url = capture(urlSlot),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.searchMembers("123", 0, 20)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    fun searchMembers_success() = testScope.runTest {
        coEvery {
            forumOceanService.searchMembers(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                SearchMembersResponseBody(
                    memberId = 7777,
                    nickName = "",
                    image = "",
                    fans = 20
                )
            )
        )
        val result = web.searchMembers("123", 0, 20)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun searchMembers_failure() = testScope.runTest {
        coEvery {
            forumOceanService.searchMembers(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = 0,
                fetch = 20
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.searchMembers("123", 0, 20)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `createVote_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Vote/Create/132434"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createVote(
                url = capture(urlSlot),
                authorization = any(),
                optionIndex = any()
            )
        } returns Response.success<Void>(204, null)
        web.createVote(132434, 4)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createVote_success() = testScope.runTest {
        coEvery {
            forumOceanService.createVote(
                url = any(),
                authorization = any(),
                optionIndex = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.createVote(132434, 4)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun createVote_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createVote(
                url = any(),
                authorization = any(),
                optionIndex = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createVote(132434, 4)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getCurrentVote_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Vote/GetCurrentVotes/1324324"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getCurrentVote(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(emptyList())
        web.getCurrentVote(1324324)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCurrentVote_success() = testScope.runTest {
        coEvery {
            forumOceanService.getCurrentVote(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            listOf(
                VoteInfo(option = 1, count = 133),
                VoteInfo(option = 2, count = 13),
                VoteInfo(option = 3, count = 33),
                VoteInfo(option = 4, count = 16)
            )
        )
        val result = web.getCurrentVote(1324324)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getCurrentVote_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getCurrentVote(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getCurrentVote(1324324)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getCommodityRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/Commodity"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getCommodityRank(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getCommodityRank(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCommodityRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getCommodityRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                GetCommodityRankResponseBody(
                    commodityKey = "7777",
                    score = 777,
                    ranking = 1,
                    lastRanking = 10
                ),
                GetCommodityRankResponseBody(
                    commodityKey = "8888",
                    score = 888,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getCommodityRank(0, 10)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getCommodityRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getCommodityRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getCommodityRank(0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }


    @Test
    fun `getUSCommodityRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/USCommodity"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getUSCommodityRank(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getUSCommodityRank(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUSCommodityRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getUSCommodityRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                GetCommodityRankResponseBody(
                    commodityKey = "7777",
                    score = 777,
                    ranking = 1,
                    lastRanking = 10
                ),
                GetCommodityRankResponseBody(
                    commodityKey = "8888",
                    score = 888,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getUSCommodityRank(0, 10)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getUSCommodityRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getUSCommodityRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getUSCommodityRank(0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getExpertMemberRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/ExpertMember"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getExpertMemberRank(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getExpertMemberRank(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getExpertMemberRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getExpertMemberRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                GetExpertMemberRankResponseBody(
                    creatorId = 7777,
                    score = 777.7,
                    ranking = 1,
                    lastRanking = 10
                ),
                GetExpertMemberRankResponseBody(
                    creatorId = 8888,
                    score = 888.8,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getExpertMemberRank(0, 10)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getExpertMemberRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getExpertMemberRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getExpertMemberRank(0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getSpecificExpertMemberRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/SpecificExpertMemberRanks"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSpecificExpertMemberRank(
                url = capture(urlSlot),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(emptyList())
        web.getSpecificExpertMemberRank("7777,8888")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSpecificExpertMemberRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificExpertMemberRank(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(
            listOf(
                GetExpertMemberRankResponseBody(
                    creatorId = 7777,
                    score = 77.7,
                    ranking = 1,
                    lastRanking = 10
                ),
                GetExpertMemberRankResponseBody(
                    creatorId = 8888,
                    score = 88.8,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getSpecificExpertMemberRank("7777,8888")
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getSpecificExpertMemberRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificExpertMemberRank(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpecificExpertMemberRank("7777,8888")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getMemberFansRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/MemberFansRank"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMemberFansRank(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getMemberFansRank(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberFansRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberFansRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                FansMemberRankResponseBody(
                    followMemberId = 7777,
                    fansIncreaseCount = 777,
                    ranking = 1,
                    lastRanking = 10
                ),
                FansMemberRankResponseBody(
                    followMemberId = 8888,
                    fansIncreaseCount = 888,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getMemberFansRank(0, 10)
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getMemberFansRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberFansRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberFansRank(0, 10)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getSpecificMemberFansRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/SpecificMemberFansRank"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSpecificMemberFansRank(
                url = capture(urlSlot),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(emptyList())
        web.getSpecificMemberFansRank("7777,8888")

        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSpecificMemberFansRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificMemberFansRank(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(
            listOf(
                FansMemberRankResponseBody(
                    followMemberId = 7777,
                    fansIncreaseCount = 777,
                    ranking = 1,
                    lastRanking = 10
                ),
                FansMemberRankResponseBody(
                    followMemberId = 8888,
                    fansIncreaseCount = 888,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getSpecificMemberFansRank("7777,8888")
        Truth.assertThat(result.isSuccess)
    }

    @Test
    fun getSpecificMemberFansRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificMemberFansRank(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpecificMemberFansRank("7777,8888")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getSolutionExpertRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/SolutionExpert"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSolutionExpertRank(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(emptyList())
        web.getSolutionExpertRank(0, 10)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSolutionExpertRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSolutionExpertRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                SolutionExpertRankResponseBody(
                    creatorId = 7777,
                    bestSolutionScore = 777,
                    ranking = 1,
                    lastRanking = 10
                ),
                SolutionExpertRankResponseBody(
                    creatorId = 8888,
                    bestSolutionScore = 888,
                    ranking = 2,
                    lastRanking = 20
                )
            )
        )
        val result = web.getSolutionExpertRank(0, 10)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getSolutionExpertRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSolutionExpertRank(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSolutionExpertRank(0, 10)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getSpecificSolutionExpertRank_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rank/SpecificSolutionExpert"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSpecificSolutionExpertRank(
                url = capture(urlSlot),
                authorization = any(),
                memberIds = any(),
            )
        } returns Response.success(emptyList())
        web.getSpecificSolutionExpertRank("7777,8888")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSpecificSolutionExpertRank_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificSolutionExpertRank(
                url = any(),
                authorization = any(),
                memberIds = any(),
            )
        } returns Response.success(
            listOf(
                SolutionExpertRankResponseBody(
                    creatorId = null,
                    bestSolutionScore = null,
                    ranking = null,
                    lastRanking = null
                )
            )
        )
        val result = web.getSpecificSolutionExpertRank("7777,8888")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getSpecificSolutionExpertRank_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificSolutionExpertRank(
                url = any(),
                authorization = any(),
                memberIds = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpecificSolutionExpertRank("7777,8888")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getNotify_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Notify/Get"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getNotify(
                url = capture(urlSlot),
                authorization = any(),
                updateTime = any(),
                offsetCount = any()
            )
        } returns Response.success(null)
        web.getNotify(updateTime = 0, offsetCount = 5)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNotify_success() = testScope.runTest {
        coEvery {
            forumOceanService.getNotify(
                url = any(),
                authorization = any(),
                updateTime = any(),
                offsetCount = any()
            )
        } returns Response.success(emptyList())
        val result = web.getNotify(updateTime = 0, offsetCount = 5)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getNotify_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getNotify(
                url = any(),
                authorization = any(),
                updateTime = any(),
                offsetCount = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getNotify(updateTime = 0, offsetCount = 5)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getNotifyCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Notify/GetCount"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(null)
        web.getNotifyCount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getNotifyCount_success() = testScope.runTest {
        val responseBody = GetNotifyCountResponseBody(count = 1)
        coEvery {
            forumOceanService.getCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)
        val result = web.getNotifyCount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getNotifyCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getNotifyCount()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `resetNotifyCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Notify/ResetCount"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.resetCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(null)
        web.resetNotifyCount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun resetNotifyCount_success() = testScope.runTest {
        coEvery {
            forumOceanService.resetCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.resetNotifyCount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetNotifyCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.resetCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.resetNotifyCount()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `setNotifyRead_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Notify/SetRead"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.setRead(
                url = capture(urlSlot),
                authorization = any(),
                notifyType = any(),
                mergeKey = any(),
                isNew = any()
            )
        } returns Response.success(null)
        web.setNotifyRead(notifyType = "", mergeKey = "", isNew = false)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setNotifyRead_success() = testScope.runTest {
        coEvery {
            forumOceanService.setRead(
                url = any(),
                authorization = any(),
                notifyType = any(),
                mergeKey = any(),
                isNew = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.setNotifyRead(notifyType = "", mergeKey = "", isNew = false)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun setNotifyRead_failure() = testScope.runTest {
        coEvery {
            forumOceanService.setRead(
                url = any(),
                authorization = any(),
                notifyType = any(),
                mergeKey = any(),
                isNew = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.setNotifyRead(notifyType = "", mergeKey = "", isNew = false)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `exchangeColumnArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/BonusPointExchange/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.exchangeColumnArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(null)
        web.exchangeColumnArticle(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun exchangeColumnArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.exchangeColumnArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success(null)
        val result = web.exchangeColumnArticle(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun exchangeColumnArticle_failure() = testScope.runTest {
        coEvery {
            forumOceanService.exchangeColumnArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.exchangeColumnArticle(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getRole_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Role"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getRole(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())
        web.getRole()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRole_success() = testScope.runTest {
        coEvery {
            forumOceanService.getRole(
                url = any(),
                authorization = any()
            )
        } returns Response.success(listOf())
        val result = web.getRole()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getRole_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getRole(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRole()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getMembersByRoleId_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Role/Id/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMembersByRoleId(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(GetMembersByRoleResponse(listOf()))
        web.getMembersByRole(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMembersByRoleId_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMembersByRoleId(
                url = any(),
                authorization = any()
            )
        } returns Response.success(GetMembersByRoleResponse(listOf()))
        val result = web.getMembersByRole(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMembersByRoleId_failed() = testScope.runTest {
        coEvery {
            forumOceanService.getMembersByRoleId(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMembersByRole(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getRoleByMemberId_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Role/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getRoleByMemberId(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())
        web.getRoleByMemberId(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRoleByMemberId_success() = testScope.runTest {
        coEvery {
            forumOceanService.getRoleByMemberId(
                url = any(),
                authorization = any()
            )
        } returns Response.success(listOf())
        val result = web.getRoleByMemberId(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getRoleByMemberId_failed() = testScope.runTest {
        coEvery {
            forumOceanService.getRoleByMemberId(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRoleByMemberId(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getExchangeCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/ExchangeCount/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getExchangeCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(ExchangeCount(1, 1))
        web.getExchangeCount(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getExchangeCount_success() = testScope.runTest {
        coEvery {
            forumOceanService.getExchangeCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success(ExchangeCount(1, 1))
        val result = web.getExchangeCount(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getExchangeCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getExchangeCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.getExchangeCount(1)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `isMemberSubscribe_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/IsMemberSubscribe/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.isMemberSubscribe(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success("{}".toResponseBody())
        web.isMemberSubscribe(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun isMemberSubscribe_success_true() = testScope.runTest {
        coEvery {
            forumOceanService.isMemberSubscribe(
                url = any(),
                authorization = any()
            )
        } returns Response.success("true".toResponseBody())
        val result = web.isMemberSubscribe(1)
            .getOrThrow()
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun isMemberSubscribe_success_false() = testScope.runTest {
        coEvery {
            forumOceanService.isMemberSubscribe(
                url = any(),
                authorization = any()
            )
        } returns Response.success("".toResponseBody())
        val result = web.isMemberSubscribe(1)
            .getOrThrow()
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun isMemberSubscribe_failure() = testScope.runTest {
        coEvery {
            forumOceanService.isMemberSubscribe(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.isMemberSubscribe(-1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getColumnistVipGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/ColumnistVipGroup/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getColumnistVipGroup(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(GetColumnistVipGroupResponse(1))
        web.getColumnistVipGroup(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getColumnistVipGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistVipGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.success(GetColumnistVipGroupResponse(1))
        val result = web.getColumnistVipGroup(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getColumnistVipGroup_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistVipGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.getColumnistVipGroup(1)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getStockReportId_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/StockReport"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getStockReportId(
                url = capture(urlSlot),
                authorization = any(),
                date = any(),
                brokerId = any(),
                stockId = any()
            )
        } returns Response.success(145516088)
        web.getStockReportId("20220505", "C0090", "8046")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockReportId_success() = testScope.runTest {
        coEvery {
            forumOceanService.getStockReportId(
                url = any(),
                authorization = any(),
                date = any(),
                brokerId = any(),
                stockId = any()
            )
        } returns Response.success(145516088)
        val result = web.getStockReportId("20220505", "C0090", "8046")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getStockReportId_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getStockReportId(
                url = any(),
                authorization = any(),
                date = any(),
                brokerId = any(),
                stockId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getStockReportId("20220505", "C0090", "8046")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupV2(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            Group(
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
        web.getGroupV2(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupV2_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupV2(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            Group(
                1,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
        val result = web.getGroupV2(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupV2_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupV2(-1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupsByRole_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/All"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupsByRole(
                url = capture(urlSlot),
                authorization = any(),
                memberId = any(),
                roles = any()
            )
        } returns Response.success(listOf())
        web.getGroupByRoles(1, listOf())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupsByRole_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsByRole(
                url = any(),
                authorization = any(),
                memberId = any(),
                roles = any()
            )
        } returns Response.success(listOf())
        val result = web.getGroupByRoles(1, listOf())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupsByRole_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsByRole(
                url = any(),
                authorization = any(),
                memberId = any(),
                roles = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupByRoles(-1L, listOf())
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `createGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createGroup(
                url = capture(urlSlot),
                authorization = any(),
                body = GroupManipulation(null, null, null, null)
            )
        } returns Response.success(200, InsertedId(0))
        web.createGroup(GroupManipulation(null, null, null, null))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                url = any(),
                authorization = any(),
                body = GroupManipulation(null, null, null, null)
            )
        } returns Response.success(200, InsertedId(0))
        val result = web.createGroup(GroupManipulation(null, null, null, null))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun createGroup_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                url = any(),
                authorization = any(),
                body = GroupManipulation(null, null, null, null)
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroup("name")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `updateGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.updateGroup(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateGroup(
            123,
            GroupManipulation("", "", "", false)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroup(
                url = any(),
                authorization = any(),
                body = GroupManipulation("", "", "", false)
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroup(
            1,
            GroupManipulation("", "", "", false)
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateGroup_failed() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroup(
                url = any(),
                authorization = any(),
                body = GroupManipulation(null, null, null, null)
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroup(1, GroupManipulation(null, null, null, null))
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `createGroupBoard_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/123/Board"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createGroupBoard(
                url = capture(urlSlot),
                authorization = any(),
                body = any(),
                isChatRoom = false
            )
        } returns Response.success(InsertedId(-1))
        web.createGroupBoard(123, false, BoardManipulation(null, null))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createGroupBoard_success() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupBoard(
                url = any(),
                authorization = any(),
                body = any(),
                isChatRoom = false
            )
        } returns Response.success(InsertedId(-1))
        val result = web.createGroupBoard(1, false, BoardManipulation(null, null))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun createGroupBoard_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupBoard(
                url = any(),
                authorization = any(),
                body = any(),
                isChatRoom = false
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroupBoard(1, false, BoardManipulation(null, null))
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `deleteGroupV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteGroupV2(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.dismissGroup(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteGroupV2_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupV2(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.dismissGroup(123)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun deleteGroupV2_failure() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.dismissGroup(123)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `updateGroupBoard_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Board/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.updateGroupBoard(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateGroupBoard(123, BoardManipulation(null, null))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateGroupBoard_success() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupBoard(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroupBoard(1, BoardManipulation(null, null))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateGroupBoard_failed() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupBoard(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroupBoard(1, BoardManipulation(null, null))
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupBoards_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/123/Board/All"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupBoards(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())
        web.getGroupBoards(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupBoards_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoards(
                url = any(),
                authorization = any()
            )
        } returns Response.success(listOf())
        val result = web.getGroupBoards(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupBoards_failed() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoards(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupBoards(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupBoard_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Board/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupBoard(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(BoardSingle(null, null, null, null, null, null, null, null))
        web.getGroupBoard(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupBoard_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoard(
                url = any(),
                authorization = any()
            )
        } returns Response.success(BoardSingle(null, null, null, null, null, null, null, null))
        val result = web.getGroupBoard(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupBoard_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoard(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupBoard(123)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `deleteGroupBoard_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Board/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.deleteGroupBoard(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.deleteGroupBoard(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun deleteGroupBoard_success() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupBoard(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteGroupBoard(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun deleteGroupBoard_failure() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupBoard(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteGroupBoard(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `hasNewGroupPending_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/123/HasNewPending"
        val urlSlot = slot<String>()
        val jsonMediaType = "application/json".toMediaType()
        coEvery {
            forumOceanService.hasNewGroupPending(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(
            200,
            //language=JSON
            """{ "hasNewPending": true }""".toResponseBody(jsonMediaType)
        )
        web.hasNewGroupPending(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun hasNewGroupPending_success() = testScope.runTest {
        val jsonMediaType = "application/json".toMediaType()
        coEvery {
            forumOceanService.hasNewGroupPending(
                url = any(),
                authorization = any()
            )
        } returns Response.success(
            200,
            //language=JSON
            """{ "hasNewPending": true }""".toResponseBody(jsonMediaType)
        )
        val result = web.hasNewGroupPending(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun hasNewGroupPending_failure() = testScope.runTest {
        coEvery {
            forumOceanService.hasNewGroupPending(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.hasNewGroupPending(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupMemberRoles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/456/Role"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupMemberRoles(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(MemberRoles(listOf()))
        web.getGroupMemberRoles(123, 456)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupMemberRoles_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMemberRoles(
                url = any(),
                authorization = any()
            )
        } returns Response.success(MemberRoles(listOf()))
        val result = web.getGroupMemberRoles(1, 1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupMemberRoles_failed() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMemberRoles(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupMemberRoles(1, 1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `updateGroupMemberRoles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/456/Role"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.updateGroupMemberRoles(
                url = capture(urlSlot),
                authorization = any(),
                roles = any()
            )
        } returns Response.success<Void>(204, null)
        web.updateGroupMemberRoles(123, 456, listOf())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateGroupMemberRoles_success() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupMemberRoles(
                url = any(),
                authorization = any(),
                roles = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroupMemberRoles(1, 1, listOf())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun updateGroupMemberRoles_failure() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupMemberRoles(
                url = any(),
                authorization = any(),
                roles = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroupMemberRoles(1, 1, listOf())
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupMembers_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupMembers(
                url = capture(urlSlot),
                authorization = any(),
                roles = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        web.getGroupMembers(123, listOf(), 0, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupMembers_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMembers(
                url = any(),
                authorization = any(),
                roles = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf())
        val result = web.getGroupMembers(1, listOf(), 0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupMembers_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMembers(
                url = any(),
                authorization = any(),
                roles = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupMembers(1, listOf(), 0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `leaveGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.leaveGroup(
                url = capture(urlSlot),
                authorization = any(),
            )
        } returns Response.success<Void>(204, null)
        web.leaveGroup(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun leaveGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.leaveGroup(
                url = any(),
                authorization = any(),
            )
        } returns Response.success<Void>(204, null)
        val result = web.leaveGroup(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun leaveGroup_failure() = testScope.runTest {
        coEvery {
            forumOceanService.leaveGroup(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.leaveGroup(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupAdmins_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/Admins"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupAdmins(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(Admins(listOf(), 0L))
        web.getGroupAdmins(123)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupAdmins_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupAdmins(
                authorization = any(),
                url = any(),
            )
        } returns Response.success(Admins(listOf(), 0L))
        val result = web.getGroupAdmins(1)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupAdmins_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupAdmins(
                authorization = any(),
                url = any(),
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupAdmins(1)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `searchGroupMember_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/Search"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.searchGroupMember(
                url = capture(urlSlot),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf())
        web.searchGroupMember(123, "", 0, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchGroupMember_success() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupMember(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf())
        val result = web.searchGroupMember(1, "", 0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun searchGroupMember_failure() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupMember(
                url = any(),
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.searchGroupMember(1, "", 0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `joinGroup_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/Join"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.joinGroup(
                url = capture(urlSlot),
                authorization = any(),
                body = JoinGroupRequest("reason")
            )
        } returns Response.success<Void>(204, null)
        web.joinGroup(123, JoinGroupRequest("reason"))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun joinGroup_success() = testScope.runTest {
        coEvery {
            forumOceanService.joinGroup(
                url = any(),
                authorization = any(),
                body = JoinGroupRequest("reason")
            )
        } returns Response.success<Void>(204, null)
        val result = web.joinGroup(1, JoinGroupRequest("reason"))
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun joinGroup_failed() = testScope.runTest {
        coEvery {
            forumOceanService.joinGroup(
                url = any(),
                authorization = any(),
                body = JoinGroupRequest("reason")
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.joinGroup(1, JoinGroupRequest("reason"))
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupPendingRequests_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/Pending"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupPendingRequests(
                url = capture(urlSlot),
                authorization = any(),
                timestamp = any()
            )
        } returns Response.success(PendingRequests(0L, listOf()))
        web.getGroupPendingRequests(123, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupPendingRequests_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupPendingRequests(
                url = any(),
                authorization = any(),
                timestamp = any()
            )
        } returns Response.success(PendingRequests(0L, listOf()))
        val result = web.getGroupPendingRequests(1, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupPendingRequests_failed() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupPendingRequests(
                url = any(),
                authorization = any(),
                timestamp = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupPendingRequests(1, -1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `searchGroupPendingRequests_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/SearchPending"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.searchGroupPendingRequests(
                url = capture(urlSlot),
                authorization = any(),
                timestamp = any(),
                keyword = any()
            )
        } returns Response.success(PendingRequests(0L, listOf()))
        web.searchGroupPendingRequests(123L, "keyword", 0L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun searchGroupPendingRequests_success() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupPendingRequests(
                url = any(),
                authorization = any(),
                timestamp = any(),
                keyword = any()
            )
        } returns Response.success(PendingRequests(0L, listOf()))
        val result = web.searchGroupPendingRequests(1L, "keyword", 0L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun searchGroupPendingRequests_failed() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupPendingRequests(
                url = any(),
                authorization = any(),
                timestamp = any(),
                keyword = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.searchGroupPendingRequests(1L, "keyword", 0L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `approvalGroupRequest_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/Approve"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.approvalGroupRequest(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.approvalGroupRequest(123L, listOf())
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun approvalGroupRequest_success() = testScope.runTest {
        coEvery {
            forumOceanService.approvalGroupRequest(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.approvalGroupRequest(1L, listOf())
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun approvalGroupRequest_failure() = testScope.runTest {
        coEvery {
            forumOceanService.approvalGroupRequest(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.approvalGroupRequest(1L, listOf())
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `kickGroupMember_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupMember/123/456"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.kickGroupMember(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.kickGroupMember(123L, 456L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun kickGroupMember_success() = testScope.runTest {
        coEvery {
            forumOceanService.kickGroupMember(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.kickGroupMember(1L, 1L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun kickGroupMember_failure() = testScope.runTest {
        coEvery {
            forumOceanService.kickGroupMember(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.kickGroupMember(1L, 1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `createGroupArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/Board/123/normal"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.createGroupArticle(
                url = capture(urlSlot),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(CreateArticleResponseBody(0L))
        web.createGroupArticle(
            123L,
            Content.Article.General(null, null, null, null, null, null, null, null)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createGroupArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(CreateArticleResponseBody(0L))
        val result = web.createGroupArticle(
            1L,
            Content.Article.General(null, null, null, null, null, null, null, null)
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun createGroupArticle_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupArticle(
                url = any(),
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroupArticle(
            1L,
            Content.Article.General(null, null, null, null, null, null, null, null)
        )
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getBoardArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/Board/123"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getBoardArticles(
                url = capture(urlSlot),
                authorization = any(),
                startWeight = any(),
                fetch = any()
            )
        } returns Response.success(GetGroupBoardArticlesResponse(listOf(), true, 0))
        web.getBoardArticles(123, 0, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getBoardArticles_success() = testScope.runTest {
        coEvery {
            forumOceanService.getBoardArticles(
                url = any(),
                authorization = any(),
                startWeight = any(),
                fetch = any()
            )
        } returns Response.success(GetGroupBoardArticlesResponse(listOf(), true, 0))
        val result = web.getBoardArticles(0, 0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getBoardArticles_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getBoardArticles(
                url = any(),
                authorization = any(),
                startWeight = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getBoardArticles(0, 0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `unsendArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/0/Unsend"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unsendArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.unsendArticle(0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun unsendArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.unsendArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unsendArticle(0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun unsendArticle_failure() = testScope.runTest {
        coEvery {
            forumOceanService.unsendArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unsendArticle(0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getAvailableBoardIds_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Board/All"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getAvailableBoardIds(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(AvailableBoardIds(listOf()))
        web.getAvailableBoardIds()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAvailableBoardIds_success() = testScope.runTest {
        coEvery {
            forumOceanService.getAvailableBoardIds(
                url = any(),
                authorization = any()
            )
        } returns Response.success(AvailableBoardIds(listOf()))
        val result = web.getAvailableBoardIds()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getAvailableBoardIds_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getAvailableBoardIds(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getAvailableBoardIds()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getGroupBoardPushSetting_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupNotification/Board/1/PushSetting"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getGroupBoardPushSetting(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(GroupPushSetting(""))
        web.getGroupBoardPushSetting(1L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getGroupBoardPushSetting_success() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoardPushSetting(
                url = any(),
                authorization = any()
            )
        } returns Response.success(GroupPushSetting(""))
        val result = web.getGroupBoardPushSetting(1L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getGroupBoardPushSetting_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoardPushSetting(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupBoardPushSetting(1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `setGroupBoardPushSetting_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupNotification/Board/1/PushSetting"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.setGroupBoardPushSetting(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.setGroupBoardPushSetting(
            1L,
            PushType.NONE
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun setGroupBoardPushSetting_success() = testScope.runTest {
        coEvery {
            forumOceanService.setGroupBoardPushSetting(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.setGroupBoardPushSetting(
            1L,
            PushType.NONE
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun setGroupBoardPushSetting_failure() = testScope.runTest {
        coEvery {
            forumOceanService.setGroupBoardPushSetting(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.setGroupBoardPushSetting(
            -1,
            PushType.NONE
        )
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getMemberRatingCounter_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rating/1"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMemberRatingCounter(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(MemberRatingCounter(rating = 0.0, reviewCount = 0))
        web.getMemberRatingCounter(1L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberRatingCounter_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingCounter(
                url = any(),
                authorization = any()
            )
        } returns Response.success(MemberRatingCounter(rating = 0.0, reviewCount = 0))
        val result = web.getMemberRatingCounter(1L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMemberRatingCounter_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingCounter(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberRatingCounter(1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getRatingComment_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rating/Review/1/1"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getRatingComment(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(RatingComment("comment", 0, 0))
        web.getRatingComment(1L, 1L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRatingComment_success() = testScope.runTest {
        coEvery {
            forumOceanService.getRatingComment(
                url = any(),
                authorization = any()
            )
        } returns Response.success(RatingComment("comment", 0, 0))
        val result = web.getRatingComment(1L, 1L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getRatingComment_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getRatingComment(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRatingComment(-1, -1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getMemberRatingComments_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rating/Reviews/1"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getMemberRatingComments(
                url = capture(urlSlot),
                authorization = any(),
                sortType = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(listOf())
        web.getMemberRatingComments(1L, 0, 10, SortType.LatestToOldest)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberRatingComments_success() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingComments(
                url = any(),
                authorization = any(),
                sortType = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(listOf())
        val result = web.getMemberRatingComments(1L, 0, 10, SortType.LatestToOldest)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getMemberRatingComments_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingComments(
                url = any(),
                authorization = any(),
                sortType = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberRatingComments(1L, 0, 10, SortType.LatestToOldest)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `reviewUser_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Rating/Review"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.reviewUser(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success("true")
        web.reviewUser(
            ReviewRequest("comment", 1, 0L)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun reviewUser_success() = testScope.runTest {
        coEvery {
            forumOceanService.reviewUser(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success("true")
        val result = web.reviewUser(
            ReviewRequest("comment", 1, 0L)
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun reviewUser_failure() = testScope.runTest {
        coEvery {
            forumOceanService.reviewUser(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.reviewUser(
            ReviewRequest("comment", 0, 0L)
        )
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getColumnistAll_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Columnist/All"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getColumnistAll(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())
        web.getColumnistAll()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getColumnistAll_success() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistAll(
                url = any(),
                authorization = any()
            )
        } returns Response.success(listOf())
        val result = web.getColumnistAll()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getColumnistAll_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistAll(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getColumnistAll()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getArticleV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/1000"
        val urlSlot = slot<String>()
        val articleId = 1000L
        val successResponse = ArticleResponseBodyV2(
            articleContent = null,
            createTime = null,
            id = articleId.toString(),
            creatorId = null,
            modifyTime = null,
            myEmoji = null,
            emojiCount = mapOf(),
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            shareCount = null,
            interested = null,
            interestCount = null,
            rewardPoints = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            totalReportCount = null,
            hasReport = null,
            isHidden = null,
            anonymous = null,
            authType = null,
            isPinnedPromotedArticle = null,
            isPromotedArticle = null
        )
        coEvery {
            forumOceanService.getArticleV2(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(successResponse)
        web.getArticleV2(articleId)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getArticleV2_success() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBodyV2(
            articleContent = null,
            createTime = null,
            id = articleId.toString(),
            creatorId = null,
            modifyTime = null,
            myEmoji = null,
            emojiCount = mapOf(),
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            shareCount = null,
            interested = null,
            interestCount = null,
            rewardPoints = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            totalReportCount = null,
            hasReport = null,
            isHidden = null,
            anonymous = null,
            authType = null,
            isPinnedPromotedArticle = null,
            isPromotedArticle = null
        )
        coEvery {
            forumOceanService.getArticleV2(
                url = any(),
                authorization = any()
            )
        } returns Response.success(successResponse)
        val result = web.getArticleV2(articleId)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().id).isEqualTo(articleId.toString())
    }

    @Test
    fun getArticleV2_failure() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getArticleV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getArticleV2(articleId)
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `getCommentV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/10101/Comments"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getCommentV2(
                url = capture(urlSlot),
                authorization = any(),
                startCommentIndex = any(),
                fetch = any()
            )
        } returns Response.success(
            GetCommentsResponseBody(
                comments = listOf(
                    CommentResponseBodyV2(
                        id = "10101-1",
                        creatorId = null,
                        content = null,
                        createTime = null,
                        modifyTime = null,
                        isHidden = null,
                        myEmoji = null,
                        emojiCount = emptyMap(),
                        myCommentIndex = emptyList(),
                        commentCount = null,
                        hasReport = null
                    )
                ),
                remainCount = 0,
                nextCommentIndex = null
            )
        )
        web.getCommentV2(
            articleId = "10101",
            startCommentIndex = null,
            fetch = null
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCommentV2_success() = testScope.runTest {
        coEvery {
            forumOceanService.getCommentV2(
                url = any(),
                authorization = any(),
                startCommentIndex = any(),
                fetch = any()
            )
        } returns Response.success(
            GetCommentsResponseBody(
                comments = listOf(
                    CommentResponseBodyV2(
                        id = "10101-1",
                        creatorId = null,
                        content = null,
                        createTime = null,
                        modifyTime = null,
                        isHidden = null,
                        myEmoji = null,
                        emojiCount = emptyMap(),
                        myCommentIndex = emptyList(),
                        commentCount = null,
                        hasReport = null
                    )
                ),
                remainCount = 0,
                nextCommentIndex = null
            )
        )
        val result = web.getCommentV2(
            articleId = "10101",
            startCommentIndex = null,
            fetch = null
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().comments).hasSize(1)
    }

    @Test(expected = HttpException::class)
    fun getCommentV2_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getCommentV2(
                url = any(),
                authorization = any(),
                startCommentIndex = any(),
                fetch = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getCommentV2(
            articleId = "10101",
            startCommentIndex = null,
            fetch = null
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `getCommentsByIndex_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/CommentsByIndex"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getCommentsByIndex(
                url = capture(urlSlot),
                authorization = any(),
                articleId = any(),
                commentIndex = any()
            )
        } returns Response.success(
            listOf(
                CommentResponseBodyV2(
                    id = "10101-2",
                    creatorId = null,
                    content = null,
                    createTime = null,
                    modifyTime = null,
                    isHidden = null,
                    myEmoji = null,
                    emojiCount = emptyMap(),
                    myCommentIndex = emptyList(),
                    commentCount = null,
                    hasReport = null
                ),
                CommentResponseBodyV2(
                    id = "10101-3",
                    creatorId = null,
                    content = null,
                    createTime = null,
                    modifyTime = null,
                    isHidden = null,
                    myEmoji = null,
                    emojiCount = emptyMap(),
                    myCommentIndex = emptyList(),
                    commentCount = null,
                    hasReport = null
                )
            )
        )
        web.getCommentsByIndex(id = "10101", commentIndices = listOf(2, 3))
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getCommentsByIndex_success() = testScope.runTest {
        coEvery {
            forumOceanService.getCommentsByIndex(
                url = any(),
                authorization = any(),
                articleId = any(),
                commentIndex = any()
            )
        } returns Response.success(
            listOf(
                CommentResponseBodyV2(
                    id = "10101-2",
                    creatorId = null,
                    content = null,
                    createTime = null,
                    modifyTime = null,
                    isHidden = null,
                    myEmoji = null,
                    emojiCount = emptyMap(),
                    myCommentIndex = emptyList(),
                    commentCount = null,
                    hasReport = null
                ),
                CommentResponseBodyV2(
                    id = "10101-3",
                    creatorId = null,
                    content = null,
                    createTime = null,
                    modifyTime = null,
                    isHidden = null,
                    myEmoji = null,
                    emojiCount = emptyMap(),
                    myCommentIndex = emptyList(),
                    commentCount = null,
                    hasReport = null
                )
            )
        )
        val result = web.getCommentsByIndex("10101", listOf(2, 3))
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow()).hasSize(2)
    }

    @Test(expected = HttpException::class)
    fun getCommentsByIndex_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getCommentsByIndex(
                url = any(),
                authorization = any(),
                articleId = any(),
                commentIndex = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getCommentsByIndex(id = "10101", commentIndices = listOf(2, 3))
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `createCommentV2_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123-1/Comment"
        val urlSlot = slot<String>()
        val commentIndex = 1L
        coEvery {
            forumOceanService.createCommentV2(
                url = capture(urlSlot),
                authorization = any(),
                body = any(),
            )
        } returns Response.success(
            200,
            CreateCommentResponseBodyV2(commentIndex)
        )
        web.createCommentV2(
            id = "123-1",
            text = null,
            multiMedia = listOf()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createCommentV2_success() = testScope.runTest {
        val commentIndex = 1L
        coEvery {
            forumOceanService.createCommentV2(
                url = any(),
                authorization = any(),
                body = any(),
            )
        } returns Response.success(
            200,
            CreateCommentResponseBodyV2(commentIndex)
        )
        val result = web.createCommentV2(
            id = "123-1",
            text = null,
            multiMedia = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().commentIndex == commentIndex)
    }

    @Test
    fun createCommentV2_failure() = testScope.runTest {
        coEvery {
            forumOceanService.createCommentV2(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createCommentV2(
            id = "123-1",
            text = null,
            multiMedia = listOf()
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `changeCommentHideState_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123-1/Hide"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.changeCommentHideState(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        web.changeCommentHideState("123-1", true)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun changeCommentHideState_success() = testScope.runTest {
        coEvery {
            forumOceanService.changeCommentHideState(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.changeCommentHideState("123-1", true)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun changeCommentHideState_failure() = testScope.runTest {
        coEvery {
            forumOceanService.changeCommentHideState(
                url = any(),
                authorization = any(),
                body = HideCommentRequestBody(true)
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.changeCommentHideState("123-1", true)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getSingleComment_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Article/123"
        val urlSlot = slot<String>()
        val response = CommentResponseBodyV2(
            id = "123",
            content = null,
            creatorId = null,
            createTime = null,
            modifyTime = null,
            isHidden = null,
            myEmoji = null,
            emojiCount = null,
            myCommentIndex = null,
            commentCount = null,
            hasReport = null
        )
        coEvery {
            forumOceanService.getSingleComment(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(response)
        web.getSingleComment("123")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSingleComment_success() = testScope.runTest {
        val commentId = "123-1"
        val response = CommentResponseBodyV2(
            id = commentId,
            content = null,
            creatorId = null,
            createTime = null,
            modifyTime = null,
            isHidden = null,
            myEmoji = null,
            emojiCount = null,
            myCommentIndex = null,
            commentCount = null,
            hasReport = null
        )
        coEvery {
            forumOceanService.getSingleComment(
                url = any(),
                authorization = any()
            )
        } returns Response.success(response)
        val result = web.getSingleComment(commentId)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrThrow().id).isEqualTo(commentId)
    }

    @Test
    fun getSingleComment_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSingleComment(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSingleComment("123-1")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getRecommendation_check url`() = testScope.runTest {
        val expect =
            "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Channel/GetRecommendation/Recommendation"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getRecommendation(
                url = capture(urlSlot),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(GetRecommendationResponse(listOf(), true, 0))
        web.getRecommendation(0, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRecommendation_success() = testScope.runTest {
        coEvery {
            forumOceanService.getRecommendation(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(GetRecommendationResponse(listOf(), true, 0))
        val result = web.getRecommendation(0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getRecommendation_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getRecommendation(
                url = any(),
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRecommendation(0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getPinPromotedArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/PromotedArticle/Pin"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getPinPromotedArticles(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())
        web.getPinPromotedArticles()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getPinPromotedArticles_success() = testScope.runTest {
        coEvery {
            forumOceanService.getPinPromotedArticles(
                authorization = any(),
                url = any()
            )
        } returns Response.success(listOf())
        val result = web.getPinPromotedArticles()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPinPromotedArticles_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getPinPromotedArticles(
                authorization = any(),
                url = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getPinPromotedArticles()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getPromotedArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/PromotedArticle"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getPromotedArticles(
                url = capture(urlSlot),
                authorization = any(),
                startWeight = any(),
                fetch = any()
            )
        } returns Response.success(GetPromotedArticlesResponse(listOf(), true, 0))
        web.getPromotedArticles(0, 0)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getPromotedArticles_success() = testScope.runTest {
        coEvery {
            forumOceanService.getPromotedArticles(
                url = any(),
                authorization = any(),
                startWeight = any(),
                fetch = any()
            )
        } returns Response.success(GetPromotedArticlesResponse(listOf(), true, 0))
        val result = web.getPromotedArticles(0, 0)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getPromotedArticles_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getPromotedArticles(
                url = any(),
                authorization = any(),
                startWeight = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getPromotedArticles(0, 0)
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getAllChatRoom_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Chatroom/All"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getAllChatRoom(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(listOf())
        web.getAllChatRoom()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAllChatRoom_success() = testScope.runTest {
        coEvery {
            forumOceanService.getAllChatRoom(
                url = any(),
                authorization = any()
            )
        } returns Response.success(listOf())
        val result = web.getAllChatRoom()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getAllChatRoom_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getAllChatRoom(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getAllChatRoom()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getUncheckChatRoomCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Chatroom/UncheckChatroomCount"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getUncheckChatRoomCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(GetUncheckChatRoomCountResponse(0))
        web.getUncheckChatRoomCount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getUncheckChatRoomCount_success() = testScope.runTest {
        coEvery {
            forumOceanService.getUncheckChatRoomCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success(GetUncheckChatRoomCountResponse(0))
        val result = web.getUncheckChatRoomCount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getUncheckChatRoomCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getUncheckChatRoomCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getUncheckChatRoomCount()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `resetUncheckChatRoomCount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/Group/Chatroom/UncheckChatroomCount"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.resetUncheckChatRoomCount(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.resetUncheckChatRoomCount()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun resetUncheckChatRoomCount_success() = testScope.runTest {
        coEvery {
            forumOceanService.resetUncheckChatRoomCount(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.resetUncheckChatRoomCount()
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun resetUncheckChatRoomCount_failure() = testScope.runTest {
        coEvery {
            forumOceanService.resetUncheckChatRoomCount(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.resetUncheckChatRoomCount()
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `pinSpaceBoardArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/123/Pin"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.pinSpaceBoardArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.pinSpaceBoardArticle("123")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun pinSpaceBoardArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.pinSpaceBoardArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.pinSpaceBoardArticle("123")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun pinSpaceBoardArticle_failure() = testScope.runTest {
        coEvery {
            forumOceanService.pinSpaceBoardArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.pinSpaceBoardArticle("123")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `unpinSpaceBoardArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/123/Unpin"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.unpinSpaceBoardArticle(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        web.unpinSpaceBoardArticle("123")
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun unpinSpaceBoardArticle_success() = testScope.runTest {
        coEvery {
            forumOceanService.unpinSpaceBoardArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.unpinSpaceBoardArticle("123")
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun unpinSpaceBoardArticle_failed() = testScope.runTest {
        coEvery {
            forumOceanService.unpinSpaceBoardArticle(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unpinSpaceBoardArticle("123")
        Truth.assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getSpaceBoardPinArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}${EXCEPT_PATH_NAME}api/GroupArticle/Board/1/Pin"
        val urlSlot = slot<String>()
        coEvery {
            forumOceanService.getSpaceBoardPinArticles(
                url = capture(urlSlot),
                authorization = any()
            )
        } returns Response.success(GetSpaceBoardPinArticlesResponseBody(listOf()))
        web.getSpaceBoardPinArticles(1L)
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSpaceBoardPinArticles_success() = testScope.runTest {
        coEvery {
            forumOceanService.getSpaceBoardPinArticles(
                url = any(),
                authorization = any()
            )
        } returns Response.success(GetSpaceBoardPinArticlesResponseBody(listOf()))
        val result = web.getSpaceBoardPinArticles(1L)
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getSpaceBoardPinArticles_failure() = testScope.runTest {
        coEvery {
            forumOceanService.getSpaceBoardPinArticles(
                url = any(),
                authorization = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpaceBoardPinArticles(1L)
        Truth.assertThat(result.isFailure).isTrue()
    }

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
        private const val EXCEPT_PATH_NAME = "ForumOcean/"
    }
}
