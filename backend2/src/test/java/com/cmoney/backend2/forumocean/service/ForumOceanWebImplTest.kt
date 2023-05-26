package com.cmoney.backend2.forumocean.service

import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.forumocean.service.api.article.create.CreateArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.create.variable.Content
import com.cmoney.backend2.forumocean.service.api.article.createpersonal.CreatePersonalArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.article.createquestion.CreateQuestionResponseBody
import com.cmoney.backend2.forumocean.service.api.article.getbanstate.GetBanStateResponseBody
import com.cmoney.backend2.forumocean.service.api.article.update.UpdateArticleHelper
import com.cmoney.backend2.forumocean.service.api.channel.getmemberstatistics.GetMemberStatisticsResponseBody
import com.cmoney.backend2.forumocean.service.api.comment.update.UpdateCommentHelper
import com.cmoney.backend2.forumocean.service.api.group.create.CreateGroupResponseBody
import com.cmoney.backend2.forumocean.service.api.group.getapprovals.GroupPendingApproval
import com.cmoney.backend2.forumocean.service.api.group.getmember.GroupMember
import com.cmoney.backend2.forumocean.service.api.group.getmemberjoinanygroups.GetMemberJoinAnyGroupsResponseBody
import com.cmoney.backend2.forumocean.service.api.group.update.UpdateGroupRequestBody
import com.cmoney.backend2.forumocean.service.api.official.get.OfficialChannelInfo
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getofficialsubscribedcount.GetOfficialSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.officialsubscriber.getsubscribedcount.GetSubscribedCountResponseBody
import com.cmoney.backend2.forumocean.service.api.relationship.getdonate.DonateInfo
import com.cmoney.backend2.forumocean.service.api.variable.request.GroupPosition
import com.cmoney.backend2.forumocean.service.api.variable.request.ReactionType
import com.cmoney.backend2.forumocean.service.api.variable.response.GroupPositionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.ArticleResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentContent
import com.cmoney.backend2.forumocean.service.api.variable.response.commentresponse.CommentResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.groupresponse.GroupResponseBody
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.MemberEmojis
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.interactive.ReactionInfoV2
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.unmockkAll
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1L)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().articleId).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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

        assertThat(result.isSuccess).isTrue()
    }

    @Test(expected = HttpException::class)
    fun updateArticle_failure() = testScope.runTest {
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

        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `updateArticle_修改文章_遇到文章不存在的情況`() = testScope.runTest {
        coEvery {
            forumOceanService.updateArticle(
                url = any(),
                authorization = any(),
                body = any()
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.updateArticle(132434, UpdateArticleHelper())

        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(HttpException::class.java)
        assertThat((result.exceptionOrNull() as? HttpException)?.code()).isEqualTo(404)
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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

        assertThat(result.isSuccess).isTrue()
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

        assertThat(result.isSuccess).isFalse()
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

        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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

        assertThat(result.isSuccess).isTrue()
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

        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `deleteArticleV2_failure_404`() = testScope.runTest {
        coEvery {
            forumOceanService.deleteArticleV2(
                url = any(),
                authorization = any()
            )
        } returns Response.error(404, "".toResponseBody())
        val result = web.deleteArticleV2("132434")

        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().size).isEqualTo(1)
        assertThat(result.getOrThrow().first().memberId).isEqualTo(100)
        assertThat(result.getOrThrow().first().totalCountArticle).isEqualTo(6)
        assertThat(result.getOrThrow().first().totalCountReaction).isEqualTo(3)
        assertThat(result.getOrThrow().first().totalCountFollowing).isEqualTo(16)
        assertThat(result.getOrThrow().first().totalCountFollower).isEqualTo(10)
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

        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().size).isEqualTo(1)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).hasSize(2)
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(urlSlot.captured).isEqualTo(expect)
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
        assertThat(result.isSuccess).isTrue()
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
        assertThat(result.isSuccess).isFalse()
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
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().memberEmojis).hasSize(2)
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
        assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

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


    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
        private const val EXCEPT_PATH_NAME = "ForumOcean/"
    }
}
