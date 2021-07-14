package com.cmoney.backend2.mobileocean.service

import android.text.TextUtils
import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponseWithError
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponseWithError
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponseWithError
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponseWithError
import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.cmoney.backend2.mobileocean.service.api.common.article.FilterType
import com.cmoney.backend2.mobileocean.service.api.common.channel.NeedInfo
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleAppendParam
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleStockTag
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.StockTag
import com.cmoney.backend2.mobileocean.service.api.createarticle.responsebody.CreateArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody.SubmitAdviceParam
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.responsebody.CreateArticleToOceanResponse
import com.cmoney.backend2.mobileocean.service.api.dislikearticle.DisLikeArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.followchannel.FollowChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getarticletips.GetArticleTipsResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getattentionchannel.AttentionChannel
import com.cmoney.backend2.mobileocean.service.api.getattentionchannel.GetAttentionChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getchannelpopulararticles.GetChannelPopularArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getfanschannel.FansChannel
import com.cmoney.backend2.mobileocean.service.api.getfanschannel.GetFansChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles.ChannelCategory
import com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles.GetFollowedChannelArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getforumlatestarticles.GetForumLatestArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getforumpopulararticles.GetForumPopularArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getlatestquestionarticles.GetLatestQuestionArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getmembermasterranking.GetMemberMasterRankingResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getpopularquestionarticles.GetPopularQuestionArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.requestbody.GetPopularStocksParam
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody.PopularStock
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody.PopularStockArticleInfo
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody.PopularStockCollectionWithError
import com.cmoney.backend2.mobileocean.service.api.getrepliedarticleIds.RepliedArticleIds
import com.cmoney.backend2.mobileocean.service.api.getrepliedarticleIds.RepliedArticleIdsWithError
import com.cmoney.backend2.mobileocean.service.api.getreplyarticlelist.GetReplyArticleListResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockarticlelist.GetStockArticleListResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstocklatestarticles.GetStockLatestArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.GetStockPictureResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.PictureType
import com.cmoney.backend2.mobileocean.service.api.getstockpopulararticles.GetStockPopularArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.givearticletip.GiveArticleTipResponseWithError
import com.cmoney.backend2.mobileocean.service.api.istodayaskedstocktendency.IsTodayAskedStockTendencyResponseWithError
import com.cmoney.backend2.mobileocean.service.api.leavechannel.LeaveChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.likearticle.LikeArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.replyarticle.ReplyArticleResponse
import com.cmoney.backend2.mobileocean.service.api.updatechanneldescription.UpdateChannelIdDescriptionResponseWithError
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MobileOceanWebImplTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var service: MobileOceanService

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var webImpl: MobileOceanWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = MobileOceanWebImpl(service, TestSetting(), TestDispatcher())
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `getPopularStocks_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = PopularStockCollectionWithError(
            stocks = listOf(
                PopularStock(
                    channelId = 111111,
                    key = "4142",
                    name = "Test",
                    articlePopularity = 55556666,
                    ranking = 1,
                    hasLastRanking = true,
                    lastRanking = 4,
                    articleInfo = PopularStockArticleInfo(
                        articleId = 11112222,
                        articleType = ArticleType.GeneralArticle,
                        title = "",
                        content = "test",
                        authorName = "1789",
                        authorChannelType = 101,
                        tag = 1
                    )
                )
            ),
            updatedInSeconds = 20
        )
        coEvery {
            service.getPopularStocks(
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                needMoreInfo = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getPopularStocks(
            GetPopularStocksParam(
                fetchCount = 1,
                needMoreInfo = 1
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.stocks).isNotNull()
    }

    @Test
    fun `getPopularStocks_code is 101_授權失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, PopularStockCollectionWithError::class.java)
        coEvery {
            service.getPopularStocks(
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                needMoreInfo = any()
            )
        } returns Response.success(responseBody)
        val result = webImpl.getPopularStocks(
            GetPopularStocksParam(
                fetchCount = 0,
                needMoreInfo = 0
            )
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `createArticleToOcean__response code is 1_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleToOceanResponse(
            articleId = 0.toLong(),
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.createArticleToOcean(
                appId = any(),
                guid = any(),
                authorization = any(),
                device = any(),
                content = any(),
                osVersion = any(),
                appVersion = any(),
                deviceName = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.createArticleToOcean(
            SubmitAdviceParam(
                "test",
                "123456",
                "123456",
                "123456"
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val actual = result.getOrThrow()
        Truth.assertThat(actual.responseCode).isEqualTo(1)
        Truth.assertThat(actual.isResponseSuccess()).isTrue()
    }

    @Test
    fun `createArticleToOcean__response code is 2_沒有輸入發文內容`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleToOceanResponse(
            articleId = null,
            responseCode = 2,
            responseMsg = "沒有輸入發文內容"
        )
        coEvery {
            service.createArticleToOcean(
                appId = any(),
                guid = any(),
                authorization = any(),
                device = any(),
                content = any(),
                osVersion = any(),
                appVersion = any(),
                deviceName = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.createArticleToOcean(
            SubmitAdviceParam(
                "",
                "123456",
                "123456",
                "123456"
            )
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(2)
    }

    @Test
    fun `createArticleToOcean__response code is 3_系統版本、名稱為空，確認後重發`() =
        mainCoroutineRule.runBlockingTest {
            val responseBody = CreateArticleToOceanResponse(
                articleId = null,
                responseCode = 3,
                responseMsg = "系統版本、名稱為空，確認後重發"
            )
            coEvery {
                service.createArticleToOcean(
                    appId = any(),
                    guid = any(),
                    authorization = any(),
                    device = any(),
                    content = any(),
                    osVersion = any(),
                    appVersion = any(),
                    deviceName = any()
                )
            } returns Response.success(responseBody)

            val result = webImpl.createArticleToOcean(
                SubmitAdviceParam(
                    "test",
                    "",
                    "",
                    ""
                )
            )
            Truth.assertThat(result.isSuccess).isFalse()
            val exception = result.exceptionOrNull() as ServerException
            Truth.assertThat(exception).isNotNull()
            Truth.assertThat(exception.code).isEqualTo(3)
        }

    @Test
    fun `createArticle_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleResponseWithError(
            isSuccess = true,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.createArticle(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.createArticle(
            articleContent = "123456",
            appendParam = ArticleAppendParam(
                mentionTag = listOf(
                    ArticleStockTag("1320", StockTag.Bear),
                    ArticleStockTag("6655", StockTag.Bull)
                )
            )
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.responseCode).isEqualTo(0)
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `createArticle_isSuccess = false失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateArticleResponseWithError(
            isSuccess = false,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.createArticle(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.createArticle(
            articleContent = "",
            appendParam = ArticleAppendParam(
                mentionTag = listOf()
            )
        )
        Truth.assertThat(result.isSuccess).isFalse()
    }

    @Test
    fun `replyArticle_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = ReplyArticleResponse(
            isSuccess = true,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.replyArticle(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.replyArticle(
            stockId = "2330",
            articleId = 0,
            content = "TEST",
            isUseClubToReply = false
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.responseCode).isEqualTo(0)
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `replyArticle_isSuccess = false失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBody = ReplyArticleResponse(
            isSuccess = false,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.replyArticle(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.replyArticle(
            stockId = "",
            articleId = 0,
            content = "",
            isUseClubToReply = false
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val isSuccess = result.getOrThrow().isSuccess
        Truth.assertThat(isSuccess).isFalse()
    }

    @Test
    fun `getRepliedArticleIds_成功`() = mainCoroutineRule.runBlockingTest {
        mockkStatic(TextUtils::class)
        every { TextUtils.join(any(), any<Iterable<Long>>()) } returns "0"
        val expect = RepliedArticleIds(
            repliedArticle = listOf(0L),
            responseCode = 1,
            responseMsg = ""
        )
        val responseBody = RepliedArticleIdsWithError(
            repliedArticle = listOf(0L),
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getRepliedArticleIds(
                authorization = any(),
                appId = any(),
                guid = any(),
                articleIds = any()
            )
        } returns Response.success(responseBody)
        val result = webImpl.getRepliedArticleIds(
            articleIds = listOf(0L)
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isEqualTo(expect)
    }

    @Test(expected = ServerException::class)
    fun `getRepliedArticleIds_失敗`() = mainCoroutineRule.runBlockingTest {
        mockkStatic(TextUtils::class)
        every { TextUtils.join(any(), any<Iterable<Long>>()) } returns "0"
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, RepliedArticleIdsWithError::class.java)
        coEvery {
            service.getRepliedArticleIds(
                authorization = any(),
                appId = any(),
                guid = any(),
                articleIds = any()
            )
        } returns Response.success(responseBody)
        val result = webImpl.getRepliedArticleIds(
            articleIds = listOf(0L)
        )
        Truth.assertThat(result.isSuccess).isFalse()
        result.getOrThrow()
    }

    @Test
    fun `isTodayAskedStockTendency_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = IsTodayAskedStockTendencyResponseWithError(
            isAsked = true
        )
        coEvery {
            service.isTodayAskedStockTendency(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.isTodayAskedStockTendency(
            stockId = "2330"
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isAsked).isTrue()
    }

    @Test
    fun `isTodayAskedStockTendency_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"不合理的股票代號"},"error":{"Code":101,"Message":"不合理的股票代號"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, IsTodayAskedStockTendencyResponseWithError::class.java)
        coEvery {
            service.isTodayAskedStockTendency(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.isTodayAskedStockTendency(
            stockId = ""
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `addAskStockTendencyLog_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = AddAskStockTendencyLogResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.addAskStockTendencyLog(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.addAskStockTendencyLog(
            stockId = "2330"
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `addAskStockTendencyLog_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"不合理的股票代號"},"error":{"Code":101,"Message":"不合理的股票代號"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, AddAskStockTendencyLogResponseWithError::class.java)
        coEvery {
            service.addAskStockTendencyLog(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.addAskStockTendencyLog(
            stockId = ""
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getAskStockTendencyAmount_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = AskStockTendencyAmountResponseWithError(
            amount = 1
        )
        coEvery {
            service.getAskStockTredencyAmount(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getAskStockTendencyAmount(
            stockId = "2330"
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.amount).isNotEqualTo(null)
    }

    @Test
    fun `getAskStockTendencyAmount_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"不合理的股票代號"},"error":{"Code":101,"Message":"不合理的股票代號"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, AskStockTendencyAmountResponseWithError::class.java)
        coEvery {
            service.getAskStockTredencyAmount(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getAskStockTendencyAmount(
            stockId = ""
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `likeArticle_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = LikeArticleResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.likeArticle(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.likeArticle(
            articleId = 12345678
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `likeArticle_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, LikeArticleResponseWithError::class.java)
        coEvery {
            service.likeArticle(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.likeArticle(
            articleId = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getStockArticleList_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetStockArticleListResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getStockArticleList(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                stockId = any(),
                fetchSize = any(),
                replyFetchSize = any(),
                isIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockArticleList(
            articleId = 12345678,
            stockId = "2330",
            fetchSize = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getStockArticleList_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetStockArticleListResponseWithError::class.java)
        coEvery {
            service.getStockArticleList(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                stockId = any(),
                fetchSize = any(),
                replyFetchSize = any(),
                isIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockArticleList(
            articleId = 0,
            stockId = "",
            fetchSize = 1
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getReplyArticleList_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetReplyArticleListResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getReplyArticleList(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getReplyArticleList(
            articleId = 12345678
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getReplyArticleList_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetReplyArticleListResponseWithError::class.java)
        coEvery {
            service.getReplyArticleList(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getReplyArticleList(
            articleId = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getForumLatestArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetForumLatestArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumLatestArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                baseArticleId = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getForumLatestArticles(
            baseArticleId = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getForumLatestArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetForumLatestArticlesResponseWithError::class.java)
        coEvery {
            service.getForumLatestArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                baseArticleId = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getForumLatestArticles(
            baseArticleId = 0,
            fetchCount = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getForumPopularArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetForumPopularArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumPopularArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                skipCount = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getForumPopularArticles(
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getForumPopularArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetForumPopularArticlesResponseWithError::class.java)
        coEvery {
            service.getForumPopularArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                skipCount = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getForumPopularArticles(
            skipCount = 0,
            fetchCount = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getStockLatestArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetStockLatestArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getStockLatestArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockIdList = any(),
                filterType = any(),
                baseArticleId = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockLatestArticles(
            stockIdList = listOf("2330", "1101"),
            filterType = FilterType.ALL,
            baseArticleId = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getStockLatestArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetStockLatestArticlesResponseWithError::class.java)
        coEvery {
            service.getStockLatestArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockIdList = any(),
                filterType = any(),
                baseArticleId = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockLatestArticles(
            stockIdList = listOf(),
            filterType = FilterType.DISCUSS,
            baseArticleId = 0,
            fetchCount = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getStockPopularArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetStockPopularArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getStockPopularArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockIdList = any(),
                filterType = any(),
                skipCount = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockPopularArticles(
            stockIdList = listOf("2330", "1101"),
            filterType = FilterType.ALL,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getStockPopularArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetStockPopularArticlesResponseWithError::class.java)
        coEvery {
            service.getStockPopularArticles(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockIdList = any(),
                filterType = any(),
                skipCount = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockPopularArticles(
            stockIdList = listOf(),
            filterType = FilterType.DISCUSS,
            skipCount = 0,
            fetchCount = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getFollowedChannelArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetFollowedChannelArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getFollowedChannelArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                channelCategory = any(),
                baseArticleId = any(),
                fetchCount = any(),
                isIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getFollowedChannelArticles(
            channelCategory = ChannelCategory.PEOPLE,
            baseArticleId = Long.MAX_VALUE,
            fetchCount = 20,
            isIncludeLimitedAskArticle = true
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getFollowedChannelArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetFollowedChannelArticlesResponseWithError::class.java)
        coEvery {
            service.getFollowedChannelArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                channelCategory = any(),
                baseArticleId = any(),
                fetchCount = any(),
                isIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getFollowedChannelArticles(
            channelCategory = ChannelCategory.PEOPLE,
            baseArticleId = Long.MAX_VALUE,
            fetchCount = 20,
            isIncludeLimitedAskArticle = true
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getPopularQuestionArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetPopularQuestionArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getPopularQuestionArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                stockIdList = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getPopularQuestionArticles(
            fetchCount = 20,
            skipCount = 0,
            stockIdList = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getPopularQuestionArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetPopularQuestionArticlesResponseWithError::class.java)
        coEvery {
            service.getPopularQuestionArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                stockIdList = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getPopularQuestionArticles(
            fetchCount = 20,
            skipCount = 0,
            stockIdList = listOf()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getLatestQuestionArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetLatestQuestionArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getLatestQuestionArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                stockIdList = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getLatestQuestionArticles(
            fetchCount = 20,
            skipCount = 0,
            stockIdList = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.articles).isNotNull()
    }

    @Test
    fun `getLatestQuestionArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetLatestQuestionArticlesResponseWithError::class.java)
        coEvery {
            service.getLatestQuestionArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                stockIdList = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getLatestQuestionArticles(
            fetchCount = 20,
            skipCount = 0,
            stockIdList = listOf()
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `followChannel_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = FollowChannelResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.followChannel(
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                channelId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.followChannel(
            channelId = 1443597
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `followChannel_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, FollowChannelResponseWithError::class.java)
        coEvery {
            service.followChannel(
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                channelId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.followChannel(
            channelId = 1443597
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `leaveChannel_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = LeaveChannelResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.leaveChannel(
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                channelId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.leaveChannel(
            channelId = 1443597
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `leaveChannel_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, LeaveChannelResponseWithError::class.java)
        coEvery {
            service.leaveChannel(
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                channelId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.leaveChannel(
            channelId = 1443597
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `updateChannelDescription_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = UpdateChannelIdDescriptionResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.updateChannelDescription(
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                description = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.updateChannelDescription(
            description = ""
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `updateChannelDescription_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, UpdateChannelIdDescriptionResponseWithError::class.java)
        coEvery {
            service.updateChannelDescription(
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                description = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.updateChannelDescription(
            description = ""
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `giveArticleTip_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GiveArticleTipResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.giveArticleTip(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                tip = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.giveArticleTip(
            articleId = 12345678,
            tip = 10
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `giveArticleTip_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GiveArticleTipResponseWithError::class.java)
        coEvery {
            service.giveArticleTip(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                tip = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.giveArticleTip(
            articleId = 0,
            tip = 10
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `addInterestedInArticleInfo_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = AddInterestedInArticleInfoResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.addInterestedInArticleInfo(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                points = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.addInterestedInArticleInfo(
            articleId = 12345678,
            points = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `addInterestedInArticleInfo_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, AddInterestedInArticleInfoResponseWithError::class.java)
        coEvery {
            service.addInterestedInArticleInfo(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                points = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.addInterestedInArticleInfo(
            articleId = 0,
            points = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `dislikeArticle_成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = DisLikeArticleResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.dislikeArticle(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.dislikeArticle(
            articleId = 12345678
        )
        Truth.assertThat(result.isSuccess).isTrue()
        val date = result.getOrThrow()
        Truth.assertThat(date.isSuccess).isTrue()
    }

    @Test
    fun `dislikeArticle_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":700374,"Message":"已經使用此功能！"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, DisLikeArticleResponseWithError::class.java)
        coEvery {
            service.dislikeArticle(
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.dislikeArticle(
            articleId = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `getFansChannel_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetFansChannelResponseWithError(
            channels = listOf(
                FansChannel(
                    answersCount = null,
                    articleCount = null,
                    attentionCount = null,
                    channelId = null,
                    channelName = null,
                    description = null,
                    diamondInfo = null,
                    fansCount = null,
                    image = null,
                    isFollowed = null,
                    levelInfo = null,
                    likeCount = null
                )
            )
        )
        coEvery {
            service.getFansChannel(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                channelId = any(),
                needInfo = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(response)

        val result = webImpl.getFansChannel(
            channelId = 0,
            needInfo = NeedInfo.All,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().channels?.size).isEqualTo(1)
    }

    @Test
    fun `getFansChannel_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetFansChannelResponseWithError::class.java)
        coEvery {
            service.getFansChannel(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                channelId = any(),
                needInfo = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getFansChannel(
            channelId = 0,
            needInfo = NeedInfo.All,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(9001)
    }

    @Test
    fun `getAttentionChannel_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetAttentionChannelResponseWithError(
            channels = listOf(
                AttentionChannel(
                    answersCount = null,
                    articleCount = null,
                    attentionCount = null,
                    channelId = null,
                    channelName = null,
                    description = null,
                    diamondInfo = null,
                    fansCount = null,
                    image = null,
                    isFollowed = null,
                    levelInfo = null,
                    likeCount = null
                )
            )
        )
        coEvery {
            service.getAttentionChannel(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                channelId = any(),
                needInfo = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(response)

        val result = webImpl.getAttentionChannel(
            channelId = 0,
            needInfo = NeedInfo.All,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().channels?.size).isEqualTo(1)
    }

    @Test
    fun `getAttentionChannel_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetAttentionChannelResponseWithError::class.java)
        coEvery {
            service.getAttentionChannel(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                channelId = any(),
                needInfo = any(),
                skipCount = any(),
                fetchCount = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getAttentionChannel(
            channelId = 0,
            needInfo = NeedInfo.All,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(9001)
    }

    @Test
    fun `getStockPicture_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetStockPictureResponseWithError(
            image = "http://fsv.cmoney.tw/cmstatic/t/images/article/1443597/52d9772f-51dd-4f29-a2d3-a3029eda1ee3.png"
        )
        coEvery {
            service.getStockPicture(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                stockId = "2330",
                pictureType = any()
            )
        } returns Response.success(response)

        val result = webImpl.getStockPicture(
            stockId = "2330",
            type = PictureType.RealTimeTrend
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().image.isNullOrEmpty()).isFalse()
    }

    @Test
    fun `getStockPicture_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetStockPictureResponseWithError::class.java)
        coEvery {
            service.getStockPicture(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                stockId = any(),
                pictureType = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getStockPicture(
            stockId = "",
            type = PictureType.WeekKLine
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(9001)
    }

    @Test
    fun `activeFollow_成功`() = mainCoroutineRule.runBlockingTest {
        val response = ActiveFollowResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.activeFollow(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)

        val result = webImpl.activeFollow()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().isSuccess).isTrue()
    }

    @Test
    fun `activeFollow_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, ActiveFollowResponseWithError::class.java)
        coEvery {
            service.activeFollow(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.activeFollow()
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun `getMemberMasterRanking_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetMemberMasterRankingResponseWithError(
            hasRanking = true,
            ranking = 2,
            hasLastRanking = true,
            lastRanking = 10,
            popularity = 6.6
        )
        coEvery {
            service.getMemberMasterRanking(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)

        val result = webImpl.getMemberMasterRanking()
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().ranking).isNotNull()
    }

    @Test
    fun `getMemberMasterRanking_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetMemberMasterRankingResponseWithError::class.java)
        coEvery {
            service.getMemberMasterRanking(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getMemberMasterRanking()
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun `getArticleTips_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetArticleTipsResponseWithError(
            tips = listOf()
        )
        coEvery {
            service.getArticleTips(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                articleId = any(),
                fetchCount = any(),
                skipCount = any()
            )
        } returns Response.success(response)

        val result = webImpl.getArticleTips(
            articleId = 0,
            fetchCount = 0,
            skipCount = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().tips).isEmpty()
    }

    @Test
    fun `getArticleTips_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetArticleTipsResponseWithError::class.java)
        coEvery {
            service.getArticleTips(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                articleId = any(),
                fetchCount = any(),
                skipCount = any()
            )
        } returns Response.success(responseBody)

        val result = webImpl.getArticleTips(
            articleId = 0,
            fetchCount = 0,
            skipCount = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(101)
    }

    @Test
    fun `getChannelPopularArticles_成功`() = mainCoroutineRule.runBlockingTest {
        val response = GetChannelPopularArticlesResponseWithError(
            articles = listOf()
        )
        coEvery {
            service.getChannelPopularArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                channelIdList = any(),
                isIncludeLimitedAskArticle = false
            )
        } returns Response.success(response)

        val result = webImpl.getChannelPopularArticles(
            channelIdList = listOf(),
            skipCount = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrThrow().articles).isEmpty()
    }

    @Test
    fun `getChannelPopularArticles_失敗`() = mainCoroutineRule.runBlockingTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetChannelPopularArticlesResponseWithError::class.java)
        coEvery {
            service.getChannelPopularArticles(
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                channelIdList = any(),
                isIncludeLimitedAskArticle = false
            )
        } returns Response.success(responseBody)

        val result = webImpl.getChannelPopularArticles(
            channelIdList = listOf(),
            skipCount = 0,
            fetchCount = 0,
            isIncludeLimitedAskArticle = false
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(9001)
    }
}