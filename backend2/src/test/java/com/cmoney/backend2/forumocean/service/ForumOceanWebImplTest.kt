package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.forumocean.service.api.article.ExchangeCount
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.columnist.GetColumnistVipGroupResponse
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBodyV2
import com.cmoney.backend2.forumocean.service.api.comment.hide.HideCommentResponseBody
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
import com.cmoney.backend2.forumocean.service.api.role.GetMembersByRoleResponse
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.support.SearchMembersResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.PersonalArticleType
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.response.GroupPositionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentContent
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.core.CoroutineTestRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ForumOceanWebImplTest {
    private val testScope = TestScope()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private val forumOceanService = mockk<ForumOceanService>()
    private val jsonParser =
        GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private val web: ForumOceanWeb =
        ForumOceanWebImpl(forumOceanService, TestSetting(), jsonParser, "", TestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createPersonalArticle_發筆記文章成功測試`() = testScope.runTest {
        val responseBody = CreatePersonalArticleResponseBody(articleId = 1L)
        val createContent = Content.PersonalArticle.Note(
            text = "發筆記",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                authorization = any(),
                path = "",
                articleType = PersonalArticleType.NOTE.articleType,
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createPersonalArticle(body = createContent)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1L)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createPersonalArticle_發筆記文章失敗測試`() = testScope.runTest {
        val createContent = Content.PersonalArticle.Note(
            text = "發筆記",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                authorization = any(),
                path = "",
                articleType = PersonalArticleType.NOTE.articleType,
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createPersonalArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createPersonalArticle_發專欄文章成功測試`() = testScope.runTest {
        val responseBody = CreatePersonalArticleResponseBody(articleId = 1)
        val createContent = Content.PersonalArticle.Columnist(
            text = "發專欄文章",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                authorization = any(),
                path = "",
                articleType = PersonalArticleType.COLUMNIST.articleType,
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = web.createPersonalArticle(body = createContent)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createPersonalArticle_發專欄文章失敗測試`() = testScope.runTest {
        val createContent = Content.PersonalArticle.Columnist(
            text = "發專欄文章",
            commodityTags = null,
            multiMedia = null,
            topics = null
        )
        coEvery {
            forumOceanService.createPersonalArticle(
                authorization = any(),
                path = "",
                articleType = PersonalArticleType.COLUMNIST.articleType,
                requestBody = createContent
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createPersonalArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發一般文章成功測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發一般文章失敗測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發社團文章成功測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發社團文章失敗測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發轉推文章成功測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.success(responseBody)
        val result = web.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發轉推文章失敗測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticle_取得一般文章_遇到文章不存在的情況`() = testScope.runTest {
        coEvery {
            forumOceanService.getArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.getArticle(132434)

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
        assertThat((result.exceptionOrNull() as? HttpException)?.code()).isEqualTo(404)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createQuestion_發問答文章成功測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.success(responseBody)
        val result = web.createQuestion(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createQuestion_發問答文章失敗測試`() = testScope.runTest {
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
                authorization = any(),
                requestBody = createContent,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createQuestion(
            body = createContent
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticle_取得一般文章成功`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.GeneralArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticle_取得一般文章失敗`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getQuestionArticle_取得問答文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.QuestionArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            interested = null,
            interestCount = null,
            rewardPoints = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getQuestionArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getQuestionArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getQuestionArticle_取得問答文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getQuestionArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getQuestionArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupArticle_取得社團文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.GroupArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getGroupArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getGroupArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupArticle_取得社團文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getGroupArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getGroupArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSharedArticle_取得轉推文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.SharedArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            shareCount = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getSharedArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getSharedArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSharedArticle_取得轉推文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getSharedArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getSharedArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSignalArticle_取得訊號文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.SignalArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getSignalArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getSignalArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSignalArticle_取得訊號文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getSignalArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getSignalArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getNewsArticle_取得新聞文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.NewsArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getNewsArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getNewsArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getNewsArticle_取得新聞文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getNewsArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getNewsArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPersonalArticle_取得個人文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.PersonalArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            weight = null
        )
        coEvery {
            forumOceanService.getPersonalArticle(
                authorization = any(),
                path = "",
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = web.getPersonalArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPersonalArticle_取得個人文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getPersonalArticle(
                authorization = any(),
                path = "",
                articleId = articleId
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getPersonalArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUnknownArticle_取得未知型態文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.UnknownArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
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
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getUnknownArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getUnknownArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUnknownArticle_取得未知型態文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getUnknownArticle(
                authorization = any(),
                articleId = articleId,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getUnknownArticle(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleV2_取得一般文章成功`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.GeneralArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleV2_取得一般文章失敗`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getArticleV2(
                authorization = any(),
                articleId =  articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getQuestionArticleV2_取得問答文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.QuestionArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            interested = null,
            interestCount = null,
            rewardPoints = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getQuestionArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getQuestionArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getQuestionArticleV2_取得問答文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getQuestionArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getQuestionArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupArticleV2_取得社團文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.GroupArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getGroupArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getGroupArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupArticleV2_取得社團文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getGroupArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getGroupArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSharedArticleV2_取得轉推文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.SharedArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            shareCount = null,
            donateCount = null,
            voteCount = null,
            voteStatus = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getSharedArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getSharedArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSharedArticleV2_取得轉推文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getSharedArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getSharedArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSignalArticleV2_取得訊號文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.SignalArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getSignalArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getSignalArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSignalArticleV2_取得訊號文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getSignalArticleV2(
                authorization = any(),
                articleId =  articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getSignalArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getNewsArticleV2_取得新聞文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.NewsArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            weight = null,
            totalReportCount = null,
            report = null,
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getNewsArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getNewsArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getNewsArticleV2_取得新聞文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getNewsArticleV2(
                authorization = any(),
                articleId =  articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getNewsArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPersonalArticleV2_取得個人文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.PersonalArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            weight = null
        )
        coEvery {
            forumOceanService.getPersonalArticleV2(
                authorization = any(),
                path = "",
                articleId =  articleId.toString()
            )
        } returns Response.success(successResponse)
        val result = web.getPersonalArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getPersonalArticleV2_取得個人文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getPersonalArticleV2(
                authorization = any(),
                path = "",
                articleId =  articleId.toString()
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getPersonalArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUnknownArticleV2_取得未知型態文章成功測試`() = testScope.runTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.UnknownArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            myReaction = null,
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
            commentDeletedCount = null
        )
        coEvery {
            forumOceanService.getUnknownArticleV2(
                authorization = any(),
                articleId =  articleId.toString(),
                path = ""
            )
        } returns Response.success(successResponse)
        val result = web.getUnknownArticleV2(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUnknownArticleV2_取得未知型態文章失敗測試`() = testScope.runTest {
        val articleId = 1000L
        coEvery {
            forumOceanService.getUnknownArticleV2(
                authorization = any(),
                articleId = articleId.toString(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getUnknownArticleV2(articleId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateArticle_修改文章成功測試`() = testScope.runTest {
        val helper = UpdateArticleHelper()
        helper.deleteMultiMedia()
        coEvery {
            forumOceanService.updateArticle(
                authorization = any(),
                articleId = any(),
                body = helper.create(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateArticle(100, helper)

        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateArticle_修改文章失敗測試`() = testScope.runTest {
        val helper = UpdateArticleHelper()
        helper.deleteMultiMedia()
        coEvery {
            forumOceanService.updateArticle(
                authorization = any(),
                articleId = any(),
                body = helper.create(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.updateArticle(100, helper)

        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateArticle_修改文章_遇到文章不存在的情況`() = testScope.runTest {
        coEvery {
            forumOceanService.updateArticle(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.updateArticle(132434, UpdateArticleHelper())

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
        assertThat((result.exceptionOrNull() as? HttpException)?.code()).isEqualTo(404)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticle_刪除文章成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteArticle(100)

        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticle_刪除文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteArticle(100)

        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticle_刪除文章_遇到文章不存在的情況`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.deleteArticle(132434)

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberStatistics_取得指定使用者的統計資訊成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberStatistics(
                authorization = any(),
                memberIds = any(),
                path = ""
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

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().size).isEqualTo(1)
        assertThat(result.getOrThrow().first().memberId).isEqualTo(100)
        assertThat(result.getOrThrow().first().totalCountArticle).isEqualTo(6)
        assertThat(result.getOrThrow().first().totalCountReaction).isEqualTo(3)
        assertThat(result.getOrThrow().first().totalCountFollowing).isEqualTo(16)
        assertThat(result.getOrThrow().first().totalCountFollower).isEqualTo(10)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberStatistics_取得指定使用者的統計資訊失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberStatistics(
                authorization = any(),
                memberIds = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getMemberStatistics(listOf(100))

        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getChannelsArticleByWeight_取得頻道文章清單以權重取成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getChannelsArticleByWeight(
                authorization = any(),
                channelNameList = any(),
                startScore = any(),
                count = any(),
                path = ""
            )
        } returns Response.success(
            listOf(
                ArticleResponseBody.UnknownArticleResponseBody(
                    articleContent = null,
                    createTime = null,
                    id = null,
                    modifyTime = null,
                    reactionState = null,
                    myReaction = null,
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
                    commentDeletedCount = null
                )
            )
        )
        val result = web.getChannelsArticleByWeight(
            listOf(),
            0,
            0
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().size).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getChannelsArticleByWeight_取得頻道文章清單以權重取失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getChannelsArticleByWeight(
                authorization = any(),
                channelNameList = any(),
                startScore = any(),
                count = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getChannelsArticleByWeight(
            listOf(),
            0,
            0
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createCollection_收藏文章成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createCollection(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.createCollection(1000)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createCollection_收藏文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createCollection(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createCollection(1000)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteCollection_取消收藏文章成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteCollection(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteCollection(1000)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteCollection_取消收藏文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteCollection(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteCollection(1000)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createComment_回覆文章成功測試`() = testScope.runTest {
        val commentId = 123L
        coEvery {
            forumOceanService.createComment(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.success<CreateCommentResponseBody>(
            200,
            CreateCommentResponseBody(commentId)
        )
        val result = web.createComment(
            articleId = 0,
            text = null,
            multiMedia = listOf(),
            position = null
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().commentIndex).isEqualTo(commentId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createComment_回覆文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createComment(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createComment(
            articleId = 0,
            text = null,
            multiMedia = listOf(),
            position = null
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createCommentV2_回覆文章成功測試`() = testScope.runTest {
        val commentIndex = 1L
        coEvery {
            forumOceanService.createCommentV2(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.success<CreateCommentResponseBodyV2>(
            200,
            CreateCommentResponseBodyV2(commentIndex)
        )
        val result = web.createCommentV2(
            articleId = "123-1",
            text = null,
            multiMedia = listOf()
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().commentIndex == commentIndex)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createCommentV2_回覆文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createCommentV2(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createCommentV2(
            articleId =  "123-1",
            text = null,
            multiMedia = listOf()
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createGroupArticleComment_回覆文章成功測試`() = testScope.runTest {
        val commentId = 123L
        coEvery {
            forumOceanService.createGroupArticleComment(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.success<CreateCommentResponseBody>(
            200,
            CreateCommentResponseBody(commentId)
        )
        val result = web.createGroupArticleComment(
            articleId = 0,
            text = null,
            multiMedia = listOf(),
            position = null
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().commentIndex).isEqualTo(commentId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createGroupArticleComment_回覆文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupArticleComment(
                authorization = any(),
                articleId = any(),
                body = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.createGroupArticleComment(
            articleId = 0,
            text = null,
            multiMedia = listOf(),
            position = null
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getComment_取得回覆清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getComment(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                offsetCount = any(),
                path = ""
            )
        } returns Response.success(
            listOf(
                CommentResponseBody(
                    id = 0, content = CommentContent(
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
        val result = web.getComment(10101, null, null)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getComment_取得回覆清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getComment(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                offsetCount = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getComment(10101, null, null)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCommentWithIds_取得指定回文清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getCommentWithId(
                authorization = any(),
                articleId = any(),
                commentIds = any(),
                path = ""
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
        val result = web.getCommentWithId(10101, listOf(2, 3))
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCommentWithIds_取得指定回文清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getCommentWithId(
                authorization = any(),
                articleId = any(),
                commentIds = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getCommentWithId(10101, listOf(2, 3))
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupManagerComments_取得指定主文的社團管理員回文清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupManagerComments(
                authorization = any(),
                articleId = any(),
                path = ""
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupManagerComments_取得指定主文的社團管理員回文清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupManagerComments(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getGroupManagerComments(10101)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateComment_更新回覆成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.updateComment(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                body = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateComment(1000, 2000, UpdateCommentHelper())
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateComment_更新回覆失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.updateComment(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                body = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.updateComment(1000, 2000, UpdateCommentHelper())
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteComment_刪除回覆成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteComment(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteComment(1000, 2000)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteComment_刪除回覆失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteComment(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.deleteComment(1000, 2000)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `reactionComment_對回覆做反應成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.reactComment(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                reactionType = ReactionType.LIKE.value,
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.reactionComment(1010, 10, ReactionType.LIKE)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `reactionComment_對回覆做反應失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.reactComment(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                reactionType = ReactionType.LIKE.value,
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.reactionComment(1010, 10, ReactionType.LIKE)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getReactionDetail_取得反映詳細資料成功測試`() = testScope.runTest {
        val reactionTypeList = listOf(ReactionType.LIKE, ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getReactionDetail(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                reactions = reactionTypeList.joinToString { it.value.toString() },
                skipCount = any(),
                takeCount = any(),
                path = ""
            )
        } returns Response.success(
            listOf(
                ReactionInfo(
                    memberId = 67, reactionType = 1, time = 1625563759

                ),
                ReactionInfo(
                    memberId = 68, reactionType = 2, time = 1625563759

                )
            )
        )
        val result = web.getReactionDetail(1010, 2000, reactionTypeList, 0, 20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `getReactionDetail_取得反映詳細資料失敗測試`() = testScope.runTest {
        val reactionTypeList = listOf(ReactionType.LIKE, ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getReactionDetail(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                reactions = reactionTypeList.joinToString { it.value.toString() },
                skipCount = any(),
                takeCount = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getReactionDetail(1010, 2000, reactionTypeList, 0, 20)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `removeReactionComment_移除回文反應成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.removeCommentReaction(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.removeReactionComment(1010, 2020)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `removeReactionComment_移除回文反應失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.removeCommentReaction(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.removeReactionComment(1010, 2020)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleReaction_建立文章反應成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleReaction(
                authorization = any(),
                articleId = any(),
                reactionType = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.createArticleReaction(10101, ReactionType.DISLIKE)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleReaction_建立文章反應失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleReaction(
                authorization = any(),
                articleId = any(),
                reactionType = any(),
                path = ""
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.createArticleReaction(10101, ReactionType.DISLIKE)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleReactionDetail_取得主文反應成功測試`() = testScope.runTest {
        val reactionTypeList = listOf(ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getArticleReactionDetail(
                authorization = any(),
                articleId = any(),
                reactions = any(),
                count = any(),
                skipCount = any(),
                path = ""
            )
        } returns Response.success(
            listOf(
                ReactionInfo(
                    memberId = 67, reactionType = ReactionType.DISLIKE.value, time = 1625563759
                )
            )
        )
        val result = web.getArticleReactionDetail(1010, reactionTypeList, 0, 20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(1)
        assertThat(result.getOrThrow().first()).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleReactionDetail_取得主文反應失敗測試`() = testScope.runTest {
        val reactionTypeList = listOf(ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getArticleReactionDetail(
                authorization = any(),
                articleId = any(),
                reactions = any(),
                count = any(),
                skipCount = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getArticleReactionDetail(1010, reactionTypeList, 0, 20)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticleReaction_刪除文章反應成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticleReaction(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteArticleReaction(10101)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticleReaction_刪除文章反應失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticleReaction(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteArticleReaction(10101)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleInterest_對文章有興趣成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleInterest(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.createArticleInterest(10101)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleInterest_對文章有興趣失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleInterest(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createArticleInterest(10101)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleDonate_對文章做打賞成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleDonate(
                authorization = any(),
                articleId = any(),
                donateValue = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.createArticleDonate(10101, 1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleDonate_對文章做打賞失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createArticleDonate(
                authorization = any(),
                articleId = any(),
                donateValue = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createArticleDonate(10101, 1)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleDonate_取得文章打賞成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getArticleDonate(
                authorization = any(),
                articleId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(
            listOf(
                DonateInfo(memberId = 1000, donateValue = 10),
                DonateInfo(memberId = 1001, donateValue = 1),
                DonateInfo(memberId = 1002, donateValue = 100)
            )
        )
        val result = web.getArticleDonate(10101, 0, 20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(3)
        assertThat(result.getOrThrow()[2].donateValue).isEqualTo(100)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleDonate_取得文章打賞失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getArticleDonate(
                authorization = any(),
                articleId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getArticleDonate(10101, 0, 20)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroup_取得社團資訊成功測試`() = testScope.runTest {
        val groupId = 1161616L
        coEvery {
            forumOceanService.getGroup(
                authorization = any(),
                groupId = groupId,
                path = ""
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(groupId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroup_取得社團資訊失敗測試`() = testScope.runTest {
        val groupId = 1161616L
        coEvery {
            forumOceanService.getGroup(
                authorization = any(),
                groupId = groupId,
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroup(groupId)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUserOwnGroup_取得用戶所擁有社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any(),
                path = ""
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
            1321321,
            0,
            20,
            listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT)
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUserOwnGroup_取得用戶所擁有社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any(),
                path = ""
            )
        } returns Response.error(403, "".toResponseBody())
        val result = web.getGroupsByPosition(
            1321321,
            0,
            20,
            listOf(GroupPosition.NORMAL, GroupPosition.MANAGEMENT, GroupPosition.PRESIDENT)
        )
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `readMemberManagedGroups_取得指定使用者管理的所有社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any(),
                path = ""
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
        val result = web.getMemberManagedGroups(1, 0, 20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().first().id).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `readMemberManagedGroups_取得指定使用者管理的所有社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = any(),
                includeAppGroup = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberManagedGroups(1, 0, 20)
        assertThat(result.isFailure).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberBelongGroups_取得用戶所屬社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = listOf(
                    GroupPosition.NORMAL,
                    GroupPosition.MANAGEMENT,
                    GroupPosition.PRESIDENT
                ).map { it.position }.sum(),
                includeAppGroup = any(),
                path = ""
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
        val result = web.getMemberBelongGroups(1231321, 0, 20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberBelongGroups_取得用戶所屬社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = listOf(
                    GroupPosition.NORMAL,
                    GroupPosition.MANAGEMENT,
                    GroupPosition.PRESIDENT
                ).map { it.position }.sum(),
                includeAppGroup = any(),
                path = ""
            )
        } returns Response.error(402, "".toResponseBody())
        val result = web.getMemberBelongGroups(1231321, 0, 20)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberJoinAnyGroups_取得指定使用者是否加入或擁有任何社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberJoinAnyGroups(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success(
            GetMemberJoinAnyGroupsResponseBody(
                isJoin = true
            )
        )
        val result = web.getMemberJoinAnyGroups(23454734)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().isJoin).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberJoinAnyGroups_取得指定使用者是否加入或擁有任何社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberJoinAnyGroups(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberJoinAnyGroups(23454734)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createGroup_建立社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                authorization = any(),
                groupName = any(),
                path = ""
            )
        } returns Response.success(
            CreateGroupResponseBody(100321)
        )
        val result = web.createGroup("社團名稱")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().groupId).isEqualTo(100321)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createGroup_建立社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                authorization = any(),
                groupName = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroup("社團名稱")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateGroup_更新社團資訊成功測試`() = testScope.runTest {
        val updateRequestBody = UpdateGroupRequestBody(
            name = null,
            description = null,
            imageUrl = null,
            isPublic = false,
            searchable = null,
            joinType = null
        )
        coEvery {
            forumOceanService.updateGroup(
                authorization = any(),
                groupId = any(),
                body = updateRequestBody,
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroup(
            10220,
            updateRequestBody
        )
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateGroup_更新社團資訊失敗測試`() = testScope.runTest {
        val updateRequestBody = UpdateGroupRequestBody(
            name = null,
            description = null,
            imageUrl = null,
            isPublic = false,
            searchable = null,
            joinType = null
        )
        coEvery {
            forumOceanService.updateGroup(
                authorization = any(),
                groupId = any(),
                body = updateRequestBody,
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroup(
            10220,
            updateRequestBody
        )
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `transferGroup_轉讓社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.transferGroup(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.transferGroup(2032032, 20320)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `transferGroup_轉讓社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.transferGroup(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.transferGroup(2032032, 20320)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteGroup_刪除社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroup(
                authorization = any(),
                groupId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteGroup(2020)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteGroup_刪除社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroup(
                authorization = any(),
                groupId = any(),
                path = ""
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.deleteGroup(2020)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `join_加入社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.join(
                authorization = any(),
                groupId = any(),
                reason = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.join(1202020, "入社理由")
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `join_加入社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.join(
                authorization = any(),
                groupId = any(),
                reason = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.join(1202020, "入社理由")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMembers_取得社團用戶成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMembers(
                authorization = any(),
                groupId = any(),
                position = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(
            listOf(
                GroupMember(memberId = 1, position = GroupPositionInfo.NORMAL),
                GroupMember(memberId = 2, position = GroupPositionInfo.NORMAL),
                GroupMember(memberId = 3, position = GroupPositionInfo.PRESIDENT)
            )
        )
        val result = web.getMembers(132132, 0, 20, GroupPosition.values().toList())
        assertThat(result.isSuccess).isTrue()
        assertThat(
            result.getOrThrow().find { it.position == GroupPositionInfo.PRESIDENT }).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMembers_取得社團用戶失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMembers(
                authorization = any(),
                groupId = any(),
                position = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMembers(132132, 0, 20, GroupPosition.values().toList())
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getApprovals_取得社團待審核清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getApprovals(
                authorization = any(),
                groupId = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getApprovals_取得社團待審核清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getApprovals(
                authorization = any(),
                groupId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getApprovals(1321684, 0, 20)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `approval_審核用戶加入社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.approval(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                isAgree = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.approval(132112, 213213, true)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `approval_審核用戶加入社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.approval(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                isAgree = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.approval(132112, 213213, true)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `changeGroupMemberPosition_設定社團成員職位成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.changeGroupMemberPosition(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                position = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.changeGroupMemberPosition(1321321, 1231, GroupPosition.PRESIDENT)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `changeGroupMemberPosition_設定社團成員職位失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.changeGroupMemberPosition(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                position = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.changeGroupMemberPosition(1321321, 1231, GroupPosition.PRESIDENT)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `kick_踢出社員成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.kick(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.kick(13213, 1321)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `kick_踢出社員失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.kick(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.kick(13213, 1321)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `leave_離開社團成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.leave(
                authorization = any(),
                groupId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.leave(5050)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `leave_離開社團失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.leave(
                authorization = any(),
                groupId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.leave(5050)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `pinArticle_置頂社團文章成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.pinArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.pinArticle(1321342)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `pinArticle_置頂社團文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.pinArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.pinArticle(1321342)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unpinArticle_取消置頂社團文章成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unpinArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.unpinArticle(1321342)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unpinArticle_取消置頂社團文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unpinArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unpinArticle(1321342)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getOfficials_取得官方頻道成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficials(
                authorization = any(),
                offset = 0,
                fetch = 20,
                path = ""
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getOfficials_取得官方頻道失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficials(
                authorization = any(),
                offset = 0,
                fetch = 20,
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getOfficials(0, 20)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getOfficialSubscribedCount_取得官方訂閱數成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficialSubscribedCount(
                authorization = any(),
                officialId = any(),
                path = ""
            )
        } returns Response.success(
            GetOfficialSubscribedCountResponseBody(132132)
        )
        val result = web.getOfficialSubscribedCount(21321)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().count).isEqualTo(132132)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getOfficialSubscribedCount_取得官方訂閱數失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getOfficialSubscribedCount(
                authorization = any(),
                officialId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getOfficialSubscribedCount(21321)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSubscribedCount_取得用戶訂閱數成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribedCount(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success(GetSubscribedCountResponseBody(2134979))
        val result = web.getSubscribedCount(1465)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().count).isEqualTo(2134979)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSubscribedCount_取得用戶訂閱數失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribedCount(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSubscribedCount(1465)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSubscribed_取得訂閱用戶清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribed(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(
            listOf(1, 2, 3, 4, 5, 6)
        )
        val result = web.getSubscribed(21321, 0, 20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(6)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSubscribed_取得訂閱用戶清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSubscribed(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSubscribed(21321, 0, 20)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `subscribe_訂閱官方成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.subscribe(
                authorization = any(),
                officialId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.subscribe(21321)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `subscribe_訂閱官方失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.subscribe(
                authorization = any(),
                officialId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.subscribe(21321)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unsubscribe_解除訂閱官方成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribe(
                authorization = any(),
                officialId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.unsubscribe(3213489)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unsubscribe_解除訂閱官方失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribe(
                authorization = any(),
                officialId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unsubscribe(3213489)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unsubscribeAll_解除訂閱所有官方成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribeAll(
                authorization = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.unsubscribeAll()
        assertThat(result.isSuccess).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `unsubscribeAll_解除訂閱所有官方失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unsubscribeAll(
                authorization = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unsubscribeAll()
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFollowingList_取得指定會員追蹤中的清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowingList(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(
            listOf<Long>(1, 2, 3, 4)
        )
        val result = web.getFollowingList(4564, 0, 10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(4)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFollowingList_取得指定會員追蹤中的清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowingList(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getFollowingList(4564, 0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFollowers_取得指定會員被追蹤中清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowers(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(listOf<Long>(1, 2, 3, 4))
        val result = web.getFollowers(43241321, 0, 10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(4)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFollowers_取得指定會員被追蹤中清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getFollowers(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getFollowers(43241321, 0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `follow_追蹤成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.follow(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.follow(1324324)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `follow_追蹤失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.follow(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.follow(1324324)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unfollow_解除追蹤成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unfollow(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.unfollow(21324324)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unfollow_解除追蹤失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unfollow(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unfollow(21324324)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `block_封鎖用戶成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.block(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.block(2344654)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `block_封鎖用戶失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.block(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.block(2344654)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unblock_解除封鎖用戶成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unblock(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.unblock(342321)
        assertThat(result.isSuccess)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `unblock_解除封鎖用戶失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.unblock(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.unblock(342321)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getBlockingList_取得封鎖用戶清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockingList(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(listOf<Long>(1, 2, 3, 4, 5))
        val result = web.getBlockingList(0, 10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(5)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getBlockingList_取得封鎖用戶清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockingList(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getBlockingList(0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getBlockers_取得被用戶封鎖清單成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockers(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.success(listOf<Long>(1, 3, 5, 7, 9))
        val result = web.getBlockers(0, 10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(5)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `getBlockers_取得被用戶封鎖清單失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getBlockers(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getBlockers(0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createReport_檢舉文章成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createReport(
                authorization = any(),
                articleId = any(),
                reasonType = any(),
                commentId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.createReport(231321, 1, null)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createReport_檢舉文章失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createReport(
                authorization = any(),
                articleId = any(),
                reasonType = any(),
                commentId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createReport(231321, 1, null)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteReport_刪除檢舉成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteReport(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteReport(2136541, commentId = null)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteReport_刪除檢舉失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteReport(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteReport(2136541, commentId = null)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberIds_取得會員ID成功測試`() = testScope.runTest {

        val memberIds: List<Long> = listOf(67, 68)
        coEvery {
            forumOceanService.getMemberIds(
                authorization = any(),
                channelIds = memberIds.joinToString(","),
                path = ""
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
        assertThat(result.isSuccess)
        val mappingList = result.getOrThrow().associateBy { it.memberId }
        assertThat(mappingList[67]?.channelId).isEqualTo(1979787)
        assertThat(mappingList[68]?.channelId).isEqualTo(2266693)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberIds_取得會員ID失敗測試`() = testScope.runTest {
        val memberIds: List<Long> = listOf(67, 68)
        coEvery {
            forumOceanService.getMemberIds(
                authorization = any(),
                channelIds = memberIds.joinToString(","),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberIds(memberIds)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getChannelIds_取得頻道ID成功測試`() = testScope.runTest {

        val channelIds: List<Long> = listOf(1979787, 2266693)
        coEvery {
            forumOceanService.getChannelIds(
                authorization = any(),
                memberIds = channelIds.joinToString(","),
                path = ""
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
        assertThat(result.isSuccess)
        val mappingList = result.getOrThrow().associateBy { it.channelId }
        assertThat(mappingList[1979787]?.memberId).isEqualTo(67)
        assertThat(mappingList[2266693]?.memberId).isEqualTo(68)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getChannelIds_取得頻道ID失敗測試`() = testScope.runTest {

        val channelIds: List<Long> = listOf(1979787, 2266693)
        coEvery {
            forumOceanService.getChannelIds(
                authorization = any(),
                memberIds = channelIds.joinToString(","),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getChannelIds(channelIds)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createVote_投票成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createVote(
                authorization = any(),
                articleId = any(),
                optionIndex = any(),
                path = ""
            )
        } returns Response.success<Void>(204, null)
        val result = web.createVote(132434, 4)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createVote_投票失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.createVote(
                authorization = any(),
                articleId = any(),
                optionIndex = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createVote(132434, 4)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCurrentVote_取得目前投票結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getCurrentVote(
                authorization = any(),
                articleId = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCurrentVote_取得目前投票結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getCurrentVote(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getCurrentVote(1324324)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCommodityRank_取得個股排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getCommodityRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCommodityRank_取得個股排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getCommodityRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getCommodityRank(0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUSCommodityRank_取得美股排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getUSCommodityRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUSCommodityRank_取得美股排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getUSCommodityRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getUSCommodityRank(0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getExpertMemberRank_取得達人排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getExpertMemberRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getExpertMemberRank_取得達人排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getExpertMemberRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getExpertMemberRank(0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificExpertMemberRank_取得指定達人排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificExpertMemberRank(
                authorization = any(),
                memberIds = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificExpertMemberRank_取得指定達人排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificExpertMemberRank(
                authorization = any(),
                memberIds = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpecificExpertMemberRank("7777,8888")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberFansRank_取得粉絲成長達人排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberFansRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberFansRank_取得粉絲成長達人排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberFansRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberFansRank(0, 10)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificMemberFansRank_取得指定粉絲成長達人排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificMemberFansRank(
                authorization = any(),
                memberIds = any(),
                path = ""
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
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificMemberFansRank_取得指定粉絲成長達人排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificMemberFansRank(
                authorization = any(),
                memberIds = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpecificMemberFansRank("7777,8888")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSolutionExpertRank_取得解題達人排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSolutionExpertRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSolutionExpertRank_取得解題達人排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSolutionExpertRank(
                authorization = any(),
                offset = any(),
                fetch = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSolutionExpertRank(0, 10)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificMemberFansRank_取得指定解題達人排行結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificSolutionExpertRank(
                authorization = any(),
                memberIds = any(),
                path = ""
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
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificMemberFansRank_取得指定解題達人排行結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.getSpecificSolutionExpertRank(
                authorization = any(),
                memberIds = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getSpecificSolutionExpertRank("7777,8888")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `searchMembers_以關鍵字搜尋用戶結果成功測試`() = testScope.runTest {
        coEvery {
            forumOceanService.searchMembers(
                authorization = any(),
                keyword = any(),
                offset = any(),
                fetch = any(),
                path = ""
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
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `searchMembers_以關鍵字搜尋用戶結果失敗測試`() = testScope.runTest {
        coEvery {
            forumOceanService.searchMembers(
                authorization = any(),
                keyword = any(),
                offset = 0,
                fetch = 20,
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.searchMembers("123", 0, 20)
        assertThat(result.isFailure).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `使用p幣兌換專欄文章_success`() = testScope.runTest {
        coEvery {
            forumOceanService.exchangeColumnArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.success(null)
        val result = web.exchangeColumnArticle(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `使用p幣兌換專欄文章_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.exchangeColumnArticle(
                authorization = any(),
                articleId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.exchangeColumnArticle(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得會員的社群角色_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getRole(
                authorization = any(),
                path = "",
            )
        } returns Response.success(listOf())
        val result = web.getRole()
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得會員的社群角色_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getRole(
                authorization = any(),
                path = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRole()
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `依角色類型取得會員名單_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getMembersByRoleId(
                authorization = any(),
                path = any(),
                roleId = any()
            )
        } returns Response.success(GetMembersByRoleResponse(listOf()))
        val result = web.getMembersByRole(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `依角色類型取得會員名單_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getMembersByRoleId(
                authorization = any(),
                path = any(),
                roleId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMembersByRole(1)
        assertThat(result.isFailure).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `取得其他使用者的角色_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getRole(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success(listOf())
        val result = web.getRole(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得其他使用者的角色_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getRole(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRole(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `使用者已兌換該作者文章數及上限_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getExchangeCount(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.success(ExchangeCount(1, 1))
        val result = web.getExchangeCount(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `使用者已兌換該作者文章數及上限_failure`() = testScope.runTest {
        coEvery {
            forumOceanService.getExchangeCount(
                authorization = any(),
                memberId = any(),
                path = ""
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.getExchangeCount(1)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得專欄作家Vip社團資訊_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistVipGroup(
                authorization = any(),
                columnistMemberId = any(),
                path = ""
            )
        } returns Response.success(GetColumnistVipGroupResponse(1))
        val result = web.getColumnistVipGroup(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得專欄作家Vip社團資訊_failure`() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistVipGroup(
                authorization = any(),
                columnistMemberId = any(),
                path = ""
            )
        } returns Response.error(400, "".toResponseBody())
        val result = web.getColumnistVipGroup(1)
        assertThat(result.isSuccess).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得研究報告Id_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getStockReportId(
                authorization = any(),
                path = "",
                date = any(),
                brokerId = any(),
                stockId = any()
            )
        } returns Response.success(145516088)
        val result = web.getStockReportId("20220505", "C0090", "8046")
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得研究報告Id_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getStockReportId(
                authorization = any(),
                path = "",
                date = any(),
                brokerId = any(),
                stockId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getStockReportId("20220505", "C0090", "8046")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得指定社團資訊_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupV2(
                authorization = any(),
                path = "",
                groupId = any()
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
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得指定社團資訊_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupV2(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupV2(-1L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `依角色取得會員所有社團_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsByRole(
                authorization = any(),
                path = "",
                memberId = any(),
                roles = any()
            )
        } returns Response.success(listOf())
        val result = web.getGroupByRoles(1, listOf())
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `依角色取得會員所有社團_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupsByRole(
                authorization = any(),
                path = "",
                memberId = any(),
                roles = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupByRoles(-1L, listOf())
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `創建社團_success`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                authorization = any(),
                path = "",
                body = GroupManipulation(null, null, null, null)
            )
        } returns Response.success(200, InsertedId(0))
        val result = web.createGroup(GroupManipulation(null, null, null, null))
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `創建社團_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroup(
                authorization = any(),
                path = "",
                groupName = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroup("name")
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `更新社團資訊_success`() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroup(
                authorization = any(),
                path = "",
                body = GroupManipulation("", "", "", false),
                groupId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroup(
            1,
            GroupManipulation("", "", "", false)
        )
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `更新社團資訊_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroup(
                authorization = any(),
                path = "",
                body = GroupManipulation(null, null, null, null),
                groupId = 0
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroup(1, GroupManipulation(null, null, null, null))
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `增加社團的板_success`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupBoard(
                authorization = any(),
                path = "",
                groupId = 1L,
                body = any()
            )
        } returns Response.success(InsertedId(-1))
        val result = web.createGroupBoard(1, BoardManipulation(null, null))
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `增加社團的板_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupBoard(
                authorization = any(),
                path = "",
                groupId = 1L,
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroupBoard(1, BoardManipulation(null, null))
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團所有看板_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoards(
                authorization = any(),
                path = "",
                groupId = 1L
            )
        } returns Response.success(listOf())
        val result = web.getGroupBoards(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團所有看板_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoards(
                authorization = any(),
                path = "",
                groupId = 1L
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupBoards(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `修改看板_success`() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupBoard(
                authorization = any(),
                path = "",
                boardId = 1L,
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroupBoard(1, BoardManipulation(null, null))
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `修改看板_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupBoard(
                authorization = any(),
                path = "",
                boardId = 1L,
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroupBoard(1, BoardManipulation(null, null))
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得特定看板資訊_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoard(
                authorization = any(),
                path = "",
                boardId = 1L
            )
        } returns Response.success(BoardSingle(null, null, null, null, null, null))
        val result = web.getGroupBoard(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得特定看板資訊_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupBoard(
                authorization = any(),
                path = "",
                boardId = 1L
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupBoard(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `刪除社團看板_success`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupBoard(
                authorization = any(),
                path = "",
                boardId = 1L
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteGroupBoard(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `刪除社團看板_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupBoard(
                authorization = any(),
                path = "",
                boardId = 1L
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteGroupBoard(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團是否有未察看的待審用戶_success`() = testScope.runTest {
        coEvery {
            forumOceanService.hasNewGroupPending(
                authorization = any(),
                path = "",
                groupId = 1L
            )
        } returns Response.success(
            200,
            //langauge=JSON
            """{ "hasNewPending": true }""".toResponseBody("application/json".toMediaType())
        )
        val result = web.hasNewGroupPending(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團是否有未察看的待審用戶_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.hasNewGroupPending(
                authorization = any(),
                path = "",
                groupId = 1L
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.hasNewGroupPending(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得該社員在社團的所有角色_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMemberRoles(
                authorization = any(),
                path = "",
                groupId = any(),
                memberId = any()
            )
        } returns Response.success(MemberRoles(listOf()))
        val result = web.getGroupMemberRoles(1, 1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得該社員在社團的所有角色_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMemberRoles(
                authorization = any(),
                path = "",
                groupId = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupMemberRoles(1, 1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `設定社團身份_success`() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupMemberRoles(
                authorization = any(),
                path = any(),
                groupId = any(),
                memberId = any(),
                roles = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.updateGroupMemberRoles(1, 1, listOf())
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `設定社團身份_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.updateGroupMemberRoles(
                authorization = any(),
                path = "",
                groupId = any(),
                memberId = any(),
                roles = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.updateGroupMemberRoles(1, 1, listOf())
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團成員列表_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMembers(
                authorization = any(),
                path = "",
                groupId = any(),
                roles = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf())
        val result = web.getGroupMembers(1, listOf(), 0, 0)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團成員列表_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupMembers(
                authorization = any(),
                path = "",
                groupId = any(),
                roles = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupMembers(1, listOf(), 0, 0)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `離開社團_success`() = testScope.runTest {
        coEvery {
            forumOceanService.leaveGroup(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.leaveGroup(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `離開社團_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.leaveGroup(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.leaveGroup(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社長及幹部清單_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupAdmins(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.success(Admins(listOf(), 0L))
        val result = web.getGroupAdmins(1)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社長及幹部清單_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupAdmins(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupAdmins(1)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `搜尋社員_success`() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupMember(
                authorization = any(),
                path = "",
                groupId = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf())
        val result = web.searchGroupMember(1, "", 0, 0)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `搜尋社員_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupMember(
                authorization = any(),
                path = "",
                groupId = any(),
                keyword = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.searchGroupMember(1, "", 0, 0)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `申請加入社團_success`() = testScope.runTest {
        coEvery {
            forumOceanService.joinGroup(
                authorization = any(),
                path = "",
                groupId = any(),
                body = JoinGroupRequest("reason")
            )
        } returns Response.success<Void>(204, null)
        val result = web.joinGroup(1, JoinGroupRequest("reason"))
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `申請加入社團_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.joinGroup(
                authorization = any(),
                path = "",
                groupId = any(),
                body = JoinGroupRequest("reason")
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.joinGroup(1, JoinGroupRequest("reason"))
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得審核成員列表_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupPendingRequests(
                authorization = any(),
                path = "",
                groupId = any(),
                timestamp = any()
            )
        } returns Response.success(PendingRequests(0L, listOf()))
        val result = web.getGroupPendingRequests(1, 0)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得審核成員列表_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupPendingRequests(
                authorization = any(),
                path = "",
                groupId = any(),
                timestamp = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupPendingRequests(1, -1L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `搜尋審核中的社員_success`() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupPendingRequests(
                authorization = any(),
                path = "",
                groupId = any(),
                timestamp = any(),
                keyword = any()
            )
        } returns Response.success(PendingRequests(0L, listOf()))
        val result = web.searchGroupPendingRequests(1L, "keyword", 0L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `搜尋審核中的社員_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.searchGroupPendingRequests(
                authorization = any(),
                path = "",
                groupId = any(),
                timestamp = any(),
                keyword = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.searchGroupPendingRequests(1L, "keyword", 0L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `審核成員加入_success`() = testScope.runTest {
        coEvery {
            forumOceanService.approvalGroupRequest(
                authorization = any(),
                path = "",
                groupId = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.approvalGroupRequest(1L, listOf())
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `審核成員加入_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.approvalGroupRequest(
                authorization = any(),
                path = "",
                groupId = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.approvalGroupRequest(1L, listOf())
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `踢出成員_success`() = testScope.runTest {
        coEvery {
            forumOceanService.kickGroupMember(
                authorization = any(),
                path = "",
                groupId = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.kickGroupMember(1L, 1L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `踢出成員_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.kickGroupMember(
                authorization = any(),
                path = "",
                groupId = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.kickGroupMember(1L, 1L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `對社團看板發文_success`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupArticle(
                authorization = any(),
                path = "",
                boardId = any(),
                requestBody = any()
            )
        } returns Response.success(CreateArticleResponseBody(0L))
        val result = web.createGroupArticle(
            1L,
            Content.Article.General(null, null, null, null, null, null, null, null)
        )
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `對社團看板發文_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.createGroupArticle(
                authorization = any(),
                path = "",
                boardId = any(),
                requestBody = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.createGroupArticle(
            1L,
            Content.Article.General(null, null, null, null, null, null, null, null)
        )
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `刪除看板文章_success`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupArticle(
                authorization = any(),
                path = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteGroupArticle(1L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `刪除看板文章_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupArticle(
                authorization = any(),
                path = any(),
                articleId = 0L
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteGroupArticle(1L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `刪除看板文章留言_success`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupArticleComment(
                authorization = any(),
                path = any(),
                articleId = any(),
                commentId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.deleteGroupArticleComment(1L, 1L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `刪除看板文章留言_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteGroupArticleComment(
                authorization = any(),
                path = "",
                articleId = 0L,
                commentId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.deleteGroupArticleComment(1L, 1L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得用戶可以進入的所有看板 id_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getAvailableBoardIds(
                authorization = any(),
                path = ""
            )
        } returns Response.success(AvailableBoardIds(listOf()))
        val result = web.getAvailableBoardIds()
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得用戶可以進入的所有看板 id_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getAvailableBoardIds(
                authorization = any(),
                path = ""
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getAvailableBoardIds()
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團推播_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupPushSetting(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.success(GroupPushSetting(""))
        val result = web.getGroupPushSetting(1L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得社團推播_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getGroupPushSetting(
                authorization = any(),
                path = "",
                groupId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getGroupPushSetting(1L)
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `設定社團推播_success`() = testScope.runTest {
        coEvery {
            forumOceanService.setGroupPushSetting(
                authorization = any(),
                path = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = web.setGroupPushSetting(
            1L,
            PushType.NONE
        )
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `設定社團推播_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.setGroupPushSetting(
                authorization = any(),
                path = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.setGroupPushSetting(
            -1,
            PushType.NONE
        )
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得會員的被評價資訊統計_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingCounter(
                authorization = any(),
                path = any(),
                memberId = any()
            )
        } returns Response.success(MemberRatingCounter(rating = 0.0, reviewCount = 0))
        val result = web.getMemberRatingCounter(1L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得會員的被評價資訊統計_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingCounter(
                authorization = any(),
                path = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberRatingCounter(1L)
        assertThat(result.isFailure).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `取得指定評價_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getRatingComment(
                authorization = any(),
                path = any(),
                creatorId = any(),
                memberId = any()
            )
        } returns Response.success(RatingComment("comment", 0, 0))
        val result = web.getRatingComment(1L, 1L)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得指定評價_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getRatingComment(
                authorization = any(),
                path = any(),
                creatorId = any(),
                memberId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getRatingComment(-1, -1L)
        assertThat(result.isFailure).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `取得指定會員的被評價清單_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingComments(
                authorization = any(),
                path = any(),
                memberId = any(),
                sortType = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(listOf())
        val result = web.getMemberRatingComments(1L, 0, 10, SortType.LatestToOldest)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得指定會員的被評價清單_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getMemberRatingComments(
                authorization = any(),
                path = any(),
                memberId = any(),
                sortType = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getMemberRatingComments(1L, 0, 10, SortType.LatestToOldest)
        assertThat(result.isFailure).isTrue()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `評價_success`() = testScope.runTest {
        coEvery {
            forumOceanService.reviewUser(
                authorization = any(),
                path = any(),
                body = any()
            )
        } returns Response.success("true")
        val result = web.reviewUser(
            ReviewRequest("comment", 1, 0L)
        )
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `評價_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.reviewUser(
                authorization = any(),
                path = any(),
                body = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.reviewUser(
            ReviewRequest("comment", 0, 0L)
        )
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得專欄作家清單_success`() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistAll(
                authorization = any(),
                path = any()
            )
        } returns Response.success(listOf())
        val result = web.getColumnistAll()
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `取得專欄作家清單_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.getColumnistAll(
                authorization = any(),
                path = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.getColumnistAll()
        assertThat(result.isFailure).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `隱藏留言_success`() = testScope.runTest {
        coEvery {
            forumOceanService.hideComment(
                authorization = any(),
                path = any(),
                articleId = any()
            )
        } returns Response.success(HideCommentResponseBody(true))
        val result = web.hideComment( "123-1")
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `隱藏留言_failed`() = testScope.runTest {
        coEvery {
            forumOceanService.hideComment(
                authorization = any(),
                path = any(),
                articleId = any()
            )
        } returns Response.error(500, "".toResponseBody())
        val result = web.hideComment( "123-1")
        assertThat(result.isFailure).isTrue()
    }
}
