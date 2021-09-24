package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.create.CreateCommentResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.Positions
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getcommodityrank.GetCommodityRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getexpertmemberrank.GetExpertMemberRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getfansmemberrank.FansMemberRankResponseBody
import com.cmoney.backend2.forumocean.service.api.rank.getsolutionexpertrank.SolutionExpertRankResponseBody
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.report.create.ReasonType
import com.cmoney.backend2.forumocean.service.api.support.ChannelIdAndMemberId
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.response.GroupPositionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentContent
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.vote.get.VoteInfo
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private val forumOceanService = mockk<ForumOceanService>()
    private val jsonParser =
        GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private val service: ForumOceanWeb =
        ForumOceanWebImpl(forumOceanService, TestSetting(), jsonParser, TestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發一般文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.General(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            topics = null
        )
        coEvery {
            forumOceanService.createArticle(
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = service.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發社團文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Group(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            groupId = 164656464,
            position = null
        )
        coEvery {
            forumOceanService.createArticle(
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = service.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticle_發轉推文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleResponseBody(articleId = 1)
        val createContent = Content.Article.Shared(
            text = "發表文章",
            multiMedia = null,
            commodityTags = null,
            voteOptions = null,
            voteMinutes = null,
            sharedPostsArticleId = 13243543,
            topics = null
        )
        coEvery {
            forumOceanService.createArticle(
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = service.createArticle(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticle_取得一般文章_遇到文章不存在的情況`() = mainCoroutineRule.runBlockingTest {

        coEvery {
            forumOceanService.getArticle(
                    authorization = any(),
                    articleId = any()
            )
        } returns Response.error(404,"".toResponseBody())
        val result = service.getArticle(132434)

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
        assertThat((result.exceptionOrNull() as? HttpException)?.code()).isEqualTo(404)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createQuestion_發問答文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateQuestionResponseBody(articleId = 1)
        val createContent = Content.Question(
            text = "發表問答",
            multiMedia = null,
            commodityTags = null,
            anonymous = null,
            topics = null
        )
        coEvery {
            forumOceanService.createQuestion(
                authorization = any(),
                requestBody = createContent
            )
        } returns Response.success(responseBody)
        val result = service.createQuestion(
            body = createContent
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticle_取得一般文章成功`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.GeneralArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
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
            donate = null
        )
        coEvery {
            forumOceanService.getArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getQuestionArticle_取得問答文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.QuestionArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
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
            report = null
        )
        coEvery {
            forumOceanService.getQuestionArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getQuestionArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupArticle_取得社團文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.GroupArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
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
            donate = null
        )
        coEvery {
            forumOceanService.getGroupArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getGroupArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSharedArticle_取得轉推文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.SharedArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
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
            donate = null
        )
        coEvery {
            forumOceanService.getSharedArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getSharedArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSignalArticle_取得訊號文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.SignalArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            weight = null,
            totalReportCount = null,
            report = null
        )
        coEvery {
            forumOceanService.getSignalArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getSignalArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getNewsArticle_取得新聞文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.NewsArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
            modifyTime = null,
            reactionState = null,
            reaction = mapOf(),
            reactionCount = null,
            collected = null,
            collectCount = null,
            myCommentIndex = listOf(),
            commentCount = null,
            weight = null,
            totalReportCount = null,
            report = null
        )
        coEvery {
            forumOceanService.getNewsArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getNewsArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUnknownArticle_取得未知型態文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val articleId = 1000L
        val successResponse = ArticleResponseBody.UnknownArticleResponseBody(
            articleContent = null,
            createTime = null,
            id = articleId,
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
            donate = null
        )
        coEvery {
            forumOceanService.getUnknownArticle(
                authorization = any(),
                articleId = articleId
            )
        } returns Response.success(successResponse)
        val result = service.getUnknownArticle(articleId)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().id).isEqualTo(articleId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateArticle_修改文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val helper = UpdateArticleHelper()
        helper.deleteMultiMedia()
        coEvery {
            forumOceanService.updateArticle(
                authorization = any(),
                articleId = any(),
                body = helper.create()
            )
        } returns Response.success<Void>(204, null)
        val result = service.updateArticle(100, helper)

        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateArticle_修改文章_遇到文章不存在的情況`() = mainCoroutineRule.runBlockingTest {

        coEvery {
            forumOceanService.updateArticle(
                    authorization = any(),
                    articleId = any(),
                    body = any()
            )
        } returns Response.error(404,"".toResponseBody())
        val result = service.updateArticle(132434, UpdateArticleHelper())

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
        assertThat((result.exceptionOrNull() as? HttpException)?.code()).isEqualTo(404)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticle_刪除文章成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteArticle(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.deleteArticle(100)

        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticle_刪除文章_遇到文章不存在的情況`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteArticle(
                    authorization = any(),
                    articleId = any()
            )
        } returns Response.error(404,"".toResponseBody())
        val result = service.deleteArticle(132434)

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberStatistics_取得指定使用者的統計資訊成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getMemberStatistics(
                authorization = any(),
                memberIds = any()
            )
        } returns Response.success(
            listOf(GetMemberStatisticsResponseBody(
                totalCountArticle = 6,
                totalCountReaction = 3,
                memberId = 100,
                totalCountFollowing = 16,
                totalCountFollower = 10
            ))
        )
        val result = service.getMemberStatistics(listOf(100))

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
    fun `getChannelsArticleByWeight_取得頻道文章清單以權重取成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getChannelsArticleByWeight(
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
                    donate = null
                )
            )
        )
        val result = service.getChannelsArticleByWeight(
            listOf(),
            0,
            0
        )
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().size).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createCollection_收藏文章成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createCollection(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.createCollection(1000)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteCollection_取消收藏文章成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteCollection(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.deleteCollection(1000)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createComment_回復文章成功測試`() = mainCoroutineRule.runBlockingTest {
        val commentId = 123L
        coEvery {
            forumOceanService.createComment(
                authorization = any(),
                articleId = any(),
                body = any()
            )
        } returns Response.success<CreateCommentResponseBody>(
            200,
            CreateCommentResponseBody(commentId)
        )
        val result = service.createComment(
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
    fun `getComment_取得回復清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getComment(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                offsetCount = any()
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
                        report = null
                    )
                )
            )
        )
        val result = service.getComment(10101, null, null)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCommentWithIds_取得指定回文清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getCommentWithId(
                authorization = any(),
                articleId = any(),
                commentIds = any()
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
                        report = null
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
                        report = null
                    )
                )
            )
        )
        val result = service.getCommentWithId(10101, listOf(2, 3))
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroupManagerComments_取得指定主文的社團管理員回文清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getGroupManagerComments(
                authorization = any(),
                articleId = any()
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
                        report = null
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
                        report = null
                    )
                )
            )
        )
        val result = service.getGroupManagerComments(10101)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateComment_更新回覆成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.updateComment(
                authorization = any(),
                articleId = any(),
                commentId = any(),
                body = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.updateComment(1000, 2000, UpdateCommentHelper())
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteComment_刪除回復成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteComment(
                authorization = any(),
                articleId = any(),
                commentIndex = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.deleteComment(1000, 2000)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `reactionComment_對回復做反應成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.reactComment(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                reactionType = ReactionType.LIKE.value
            )
        } returns Response.success<Void>(204, null)
        val result = service.reactionComment(1010, 10, ReactionType.LIKE)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getReactionDetail_取得反映詳細資料成功測試`() = mainCoroutineRule.runBlockingTest {
        val reactionTypeList = listOf(ReactionType.LIKE,ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getReactionDetail(
                authorization = any(),
                articleId = any(),
                commentIndex = any(),
                reactions = reactionTypeList.joinToString { it.value.toString() },
                skipCount = any(),
                takeCount = any()
            )
        } returns Response.success(
            listOf(
                ReactionInfo(
                    memberId = 67, reactionType =  1, time = 1625563759

                ),
                ReactionInfo(
                    memberId = 68, reactionType = 2, time = 1625563759

                )
            )
        )
        val result = service.getReactionDetail(1010, 2000,reactionTypeList,0,20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `removeReactionComment_移除回文反應成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.removeCommentReaction(
                authorization = any(),
                articleId = any(),
                commentIndex = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.removeReactionComment(1010, 2020)
        assertThat(result.isSuccess).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleReaction_建立文章反應成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createArticleReaction(
                authorization = any(),
                articleId = any(),
                reactionType = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.createArticleReaction(10101, ReactionType.DISLIKE)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleReactionDetail_取得主文反應成功測試`() = mainCoroutineRule.runBlockingTest {
        val reactionTypeList = listOf(ReactionType.DISLIKE)
        coEvery {
            forumOceanService.getArticleReactionDetail(
                authorization = any(),
                articleId = any(),
                reactions = any(),
                count = any(),
                skipCount = any()
            )
        } returns Response.success(
            listOf(
                ReactionInfo(
                    memberId = 67, reactionType = ReactionType.DISLIKE.value, time = 1625563759
                )
            )
        )
        val result = service.getArticleReactionDetail(1010, reactionTypeList, 0,20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(1)
        assertThat(result.getOrThrow().first()).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteArticleReaction_刪除文章反應成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteArticleReaction(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.deleteArticleReaction(10101)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleInterest_對文章有興趣成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createArticleInterest(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.createArticleInterest(10101)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createArticleDonate_對文章做打賞成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createArticleDonate(
                authorization = any(),
                articleId = any(),
                donateValue = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.createArticleDonate(10101, 1)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getArticleDonate_取得文章打賞成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getArticleDonate(
                authorization = any(),
                articleId = any(),
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
        val result = service.getArticleDonate(10101,0,20)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(3)
        assertThat(result.getOrThrow()[2].donateValue).isEqualTo(100)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getGroup_取得社團資訊成功測試`() = mainCoroutineRule.runBlockingTest {
        val groupId = 1161616L
        coEvery {
            forumOceanService.getGroup(
                authorization = any(),
                groupId = groupId
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
                groupPosition = null
            )
        )
        val result = service.getGroup(groupId)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow().id).isEqualTo(groupId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUserOwnGroup_取得用戶所擁有社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
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
                    groupPosition = null
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
                    groupPosition = null
                )
            )
        )
        val result = service.getGroupsByPosition(
            1321321,
            0,
            20,
            listOf(Positions.NORMAL, Positions.MANAGEMENT, Positions.PRESIDENT)
        )
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `readMemberManagedGroups_取得指定使用者管理的所有社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
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
                                groupPosition = null
                        )
                )
        )
        val result = service.getMemberManagedGroups(1,0,20)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().first().id).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberBelongGroups_取得用戶所屬社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getGroupsWithPosition(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any(),
                position = listOf(
                    Positions.NORMAL,
                    Positions.MANAGEMENT,
                    Positions.PRESIDENT
                ).map { it.position },
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
                    groupPosition = null
                )
            )
        )
        val result = service.getMemberBelongGroups(1231321,0,20)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberJoinAnyGroups_取得指定使用者是否加入或擁有任何社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getMemberJoinAnyGroups(
                    authorization = any(),
                    memberId = any()
            )
        } returns Response.success(
                GetMemberJoinAnyGroupsResponseBody(
                        isJoin = true
                )
        )
        val result = service.getMemberJoinAnyGroups(23454734)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow().isJoin).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createGroup_建立社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createGroup(
                authorization = any(),
                groupName = any()
            )
        } returns Response.success(
            CreateGroupResponseBody(100321)
        )
        val result = service.createGroup("社團名稱")
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow().groupId).isEqualTo(100321)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `updateGroup_更新社團資訊成功測試`() = mainCoroutineRule.runBlockingTest {
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
                body = updateRequestBody
            )
        } returns Response.success<Void>(204, null)
        val result = service.updateGroup(
            10220,
            updateRequestBody
        )
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `transferGroup_轉讓社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.transferGroup(
                authorization = any(),
                groupId = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.transferGroup(2032032, 20320)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteGroup_刪除社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteGroup(
                authorization = any(),
                groupId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.deleteGroup(2020)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `join_加入社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.join(
                authorization = any(),
                groupId = any(),
                reason = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.join(1202020, "入社理由")
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMembers_取得社團用戶成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getMembers(
                authorization = any(),
                groupId = any(),
                includeManagerInfo = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf(
                GroupMember(memberId = 1, position = GroupPositionInfo.General),
                GroupMember(memberId = 2, position = GroupPositionInfo.General),
                GroupMember(memberId = 3, position = GroupPositionInfo.Cadre)
            )
        )
        val result = service.getMembers(132132, 0,20,true)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow().find { it.position == GroupPositionInfo.Cadre }).isNotNull()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getApprovals_取得社團待審核清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getApprovals(
                authorization = any(),
                groupId = any(),
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
        val result = service.getApprovals(1321684,0,20)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `approval_審核用戶加入社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.approval(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                isAgree = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.approval(132112, 213213, true)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `changeGroupMemberPosition_設定社團成員職位成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.changeGroupMemberPosition(
                authorization = any(),
                groupId = any(),
                memberId = any(),
                position = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.changeGroupMemberPosition(1321321, 1231, GroupPosition.Cadre)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `kick_踢出社員成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.kick(
                authorization = any(),
                groupId = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.kick(13213, 1321)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `leave_離開社團成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.leave(
                authorization = any(),
                groupId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.leave(5050)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `pinArticle_置頂社團文章成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.pinArticle(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.pinArticle(1321342)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unpinArticle_取消置頂社團文章成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.unpinArticle(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.unpinArticle(1321342)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getOfficials_取得官方頻道成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getOfficials(
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
        val result = service.getOfficials(0, 20)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(2)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getOfficialSubscribedCount_取得官方訂閱數成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getOfficialSubscribedCount(
                authorization = any(),
                officialId = any()
            )
        } returns Response.success(
            GetOfficialSubscribedCountResponseBody(132132)
        )
        val result = service.getOfficialSubscribedCount(21321)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow().count).isEqualTo(132132)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSubscribedCount_取得用戶訂閱數成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getSubscribedCount(
                authorization = any(),
                memberId = any()
            )
        } returns Response.success(GetSubscribedCountResponseBody(2134979))
        val result = service.getSubscribedCount(1465)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow().count).isEqualTo(2134979)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSubscribed_取得訂閱用戶清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getSubscribed(
                authorization = any(),
                memberId = any(),
                offset = any()
                , fetch = any()
            )
        } returns Response.success(
            listOf(1, 2, 3, 4, 5, 6)
        )
        val result = service.getSubscribed(21321,0,20)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(6)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `subscribe_訂閱官方成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.subscribe(
                authorization = any(),
                officialId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.subscribe(21321)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unsubscribe_解除訂閱官方成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.unsubscribe(
                authorization = any(),
                officialId = any()
            )
        }
        val result = service.unsubscribe(3213489)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unsubscribeAll_解除訂閱所有官方成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.unsubscribeAll(
                authorization = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.unsubscribeAll()
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFollowingList_取得指定會員追蹤中的清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getFollowingList(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(
            listOf<Long>(1, 2, 3, 4)
        )
        val result = service.getFollowingList(4564,0,10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(4)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getFollowers_取得指定會員被追蹤中清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getFollowers(
                authorization = any(),
                memberId = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf<Long>(1, 2, 3, 4))
        val result = service.getFollowers(43241321,0,10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(4)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `follow_追蹤成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.follow(
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.follow(1324324)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unfollow_解除追蹤成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.unfollow(
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.unfollow(21324324)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `block_封鎖用戶成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.block(
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.block(2344654)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `unblock_解除封鎖用戶成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.unblock(
                authorization = any(),
                memberId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.unblock(342321)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getBlockingList_取得封鎖用戶清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getBlockingList(
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf<Long>(1, 2, 3, 4, 5))
        val result = service.getBlockingList(0,10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(5)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getBlockers_取得被用戶封鎖清單成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getBlockers(
                authorization = any(),
                offset = any(),
                fetch = any()
            )
        } returns Response.success(listOf<Long>(1, 3, 5, 7, 9))
        val result = service.getBlockers(0,10)
        assertThat(result.isSuccess)
        assertThat(result.getOrThrow()).hasSize(5)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createReport_檢舉文章成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createReport(
                authorization = any(),
                articleId = any(),
                reasonType = any(),
                commentId = any()

            )
        } returns Response.success<Void>(204,null)
        val result = service.createReport(231321,ReasonType.AD,null)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteReport_刪除檢舉成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.deleteReport(
                authorization = any(),
                articleId = any(),
                commentId = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.deleteReport(2136541, commentId = null)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberIds_取得會員ID成功測試`() = mainCoroutineRule.runBlockingTest {

        val memberIds: List<Long> = listOf(67, 68)
        coEvery {
            forumOceanService.getMemberIds(
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
        val result = service.getMemberIds(memberIds)
        assertThat(result.isSuccess)
        val mappingList = result.getOrThrow().associateBy { it.memberId }
        assertThat(mappingList[67]?.channelId).isEqualTo(1979787)
        assertThat(mappingList[68]?.channelId).isEqualTo(2266693)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getChannelIds_取得頻道ID成功測試`() = mainCoroutineRule.runBlockingTest {

        val channelIds: List<Long> = listOf(1979787, 2266693)
        coEvery {
            forumOceanService.getChannelIds(
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
        val result = service.getChannelIds(channelIds)
        assertThat(result.isSuccess)
        val mappingList = result.getOrThrow().associateBy { it.channelId }
        assertThat(mappingList[1979787]?.memberId).isEqualTo(67)
        assertThat(mappingList[2266693]?.memberId).isEqualTo(68)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `createVote_投票成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.createVote(
                authorization = any(),
                articleId = any(),
                optionIndex = any()
            )
        } returns Response.success<Void>(204, null)
        val result = service.createVote(132434, 4)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCurrentVote_取得目前投票結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getCurrentVote(
                authorization = any(),
                articleId = any()
            )
        } returns Response.success(
            listOf(
                VoteInfo(option = 1, count = 133),
                VoteInfo(option = 2, count = 13),
                VoteInfo(option = 3, count = 33),
                VoteInfo(option = 4, count = 16)
            )
        )
        val result = service.getCurrentVote(1324324)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getCommodityRank_取得個股排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getCommodityRank(
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
        val result = service.getCommodityRank(0,10)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getUSCommodityRank_取得美股排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getUSCommodityRank(
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
        val result = service.getUSCommodityRank(0,10)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getExpertMemberRank_取得達人排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getExpertMemberRank(
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
        val result = service.getExpertMemberRank(0, 10)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificExpertMemberRank_取得指定達人排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getSpecificExpertMemberRank(
                authorization = any(),
                memeberIds = any()
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
        val result = service.getSpecificExpertMemberRank("7777,8888")
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getMemberFansRank_取得粉絲成長達人排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getMemberFansRank(
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
        val result = service.getMemberFansRank(0, 10)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificMemberFansRank_取得指定粉絲成長達人排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getSpecificMemberFansRank(
                authorization = any(),
                memeberIds = any()
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
        val result = service.getSpecificMemberFansRank("7777,8888")
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSolutionExpertRank_取得解題達人排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getSolutionExpertRank(
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
        val result = service.getSolutionExpertRank(0, 10)
        assertThat(result.isSuccess)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getSpecificMemberFansRank_取得指定解題達人排行結果成功測試`() = mainCoroutineRule.runBlockingTest {
        coEvery {
            forumOceanService.getSpecificSolutionExpertRank(
                authorization = any(),
                memeberIds = any()
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
        val result = service.getSpecificSolutionExpertRank("7777,8888")
        assertThat(result.isSuccess)
    }
}