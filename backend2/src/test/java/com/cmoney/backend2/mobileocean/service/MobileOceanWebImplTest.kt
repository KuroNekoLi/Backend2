package com.cmoney.backend2.mobileocean.service

import android.text.TextUtils
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponseWithError
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponseWithError
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponseWithError
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponseWithError
import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
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
import com.cmoney.backend2.mobileocean.service.api.getsinglearticle.GetSingleArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockarticlelist.GetStockArticleListResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.GetStockPictureResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.PictureType
import com.cmoney.backend2.mobileocean.service.api.givearticletip.GiveArticleTipResponseWithError
import com.cmoney.backend2.mobileocean.service.api.istodayaskedstocktendency.IsTodayAskedStockTendencyResponseWithError
import com.cmoney.backend2.mobileocean.service.api.leavechannel.LeaveChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.likearticle.LikeArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.replyarticle.ReplyArticleResponse
import com.cmoney.backend2.mobileocean.service.api.updatechanneldescription.UpdateChannelIdDescriptionResponseWithError
import com.cmoney.core.CoroutineTestRule
import com.cmoney.core.TestDispatcherProvider
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MobileOceanWebImplTest {
    private val testScope = TestScope()

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule(testScope = testScope)

    @MockK
    private lateinit var service: MobileOceanService

    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var webImpl: MobileOceanWeb

    @MockK(relaxed = true)
    private lateinit var manager: GlobalBackend2Manager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webImpl = MobileOceanWebImpl(
            manager = manager,
            service = service,
            dispatcher = TestDispatcherProvider()
        )
        coEvery {
            manager.getMobileOceanSettingAdapter().getDomain()
        } returns EXCEPT_DOMAIN
    }

    @Test
    fun `getPopularStocks_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = PopularStockCollectionWithError(
            stocks = emptyList(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getPopularStocks(
                url = capture(urlSlot),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                needMoreInfo = any()
            )
        } returns Response.success(responseBody)

        webImpl.getPopularStocks(
            GetPopularStocksParam(
                fetchCount = 1,
                needMoreInfo = 1
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getPopularStocks_success() = testScope.runTest {
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
                url = any(),
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
    fun `getPopularStocks_code is 101_授權失敗`() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, PopularStockCollectionWithError::class.java)
        coEvery {
            service.getPopularStocks(
                url = any(),
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
    fun `createArticleToOcean_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = CreateArticleToOceanResponse(
            articleId = 0.toLong(),
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.createArticleToOcean(
                url = capture(urlSlot),
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

        webImpl.createArticleToOcean(
            SubmitAdviceParam(
                "test",
                "123456",
                "123456",
                "123456"
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }


    @Test
    fun `createArticleToOcean_response code is 1_success`() = testScope.runTest {
        val responseBody = CreateArticleToOceanResponse(
            articleId = 0.toLong(),
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.createArticleToOcean(
                url = any(),
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
    fun `createArticleToOcean_response code is 2_沒有輸入發文內容`() = testScope.runTest {
        val responseBody = CreateArticleToOceanResponse(
            articleId = null,
            responseCode = 2,
            responseMsg = "沒有輸入發文內容"
        )
        coEvery {
            service.createArticleToOcean(
                url = any(),
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
    fun `createArticleToOcean_response code is 3_系統版本、名稱為空，確認後重發`() =
        testScope.runTest {
            val responseBody = CreateArticleToOceanResponse(
                articleId = null,
                responseCode = 3,
                responseMsg = "系統版本、名稱為空，確認後重發"
            )
            coEvery {
                service.createArticleToOcean(
                    url = any(),
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
    fun `createArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = CreateArticleResponseWithError(
            isSuccess = true,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.createArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        webImpl.createArticle(
            articleContent = "123456",
            appendParam = ArticleAppendParam(
                mentionTag = listOf(
                    ArticleStockTag("1320", StockTag.Bear),
                    ArticleStockTag("6655", StockTag.Bull)
                )
            )
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun createArticle_success() = testScope.runTest {
        val responseBody = CreateArticleResponseWithError(
            isSuccess = true,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.createArticle(
                url = any(),
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
    fun createArticle_failure() = testScope.runTest {
        val responseBody = CreateArticleResponseWithError(
            isSuccess = false,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.createArticle(
                url = any(),
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
    fun `replyArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = ReplyArticleResponse(
            isSuccess = false,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.replyArticle(
                url = capture(urlSlot),
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        webImpl.replyArticle(
            stockId = "2330",
            articleId = 0,
            content = "TEST",
            isUseClubToReply = false
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun replyArticle_success() = testScope.runTest {
        val responseBody = ReplyArticleResponse(
            isSuccess = true,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.replyArticle(
                url = any(),
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
    fun replyArticle_failure() = testScope.runTest {
        val responseBody = ReplyArticleResponse(
            isSuccess = false,
            responseCode = 0,
            newArticleId = 0
        )
        coEvery {
            service.replyArticle(
                url = any(),
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
    fun `getRepliedArticleIds_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = RepliedArticleIdsWithError(
            repliedArticle = listOf(0L),
            responseCode = 1,
            responseMsg = ""
        )
        coEvery {
            service.getRepliedArticleIds(
                url = capture(urlSlot),
                authorization = any(),
                appId = any(),
                guid = any(),
                articleIds = any()
            )
        } returns Response.success(responseBody)
        webImpl.getRepliedArticleIds(
            articleIds = listOf(0L)
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getRepliedArticleIds_success() = testScope.runTest {
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
                url = any(),
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
    fun getRepliedArticleIds_failure() = testScope.runTest {
        mockkStatic(TextUtils::class)
        every { TextUtils.join(any(), any<Iterable<Long>>()) } returns "0"
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"},"error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, RepliedArticleIdsWithError::class.java)
        coEvery {
            service.getRepliedArticleIds(
                url = any(),
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
    fun `isTodayAskedStockTendency_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = IsTodayAskedStockTendencyResponseWithError(
            isAsked = true
        )
        coEvery {
            service.isTodayAskedStockTendency(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        webImpl.isTodayAskedStockTendency(
            stockId = "2330"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun isTodayAskedStockTendency_success() = testScope.runTest {
        val responseBody = IsTodayAskedStockTendencyResponseWithError(
            isAsked = true
        )
        coEvery {
            service.isTodayAskedStockTendency(
                url = any(),
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
    fun isTodayAskedStockTendency_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"不合理的股票代號"},"error":{"Code":101,"Message":"不合理的股票代號"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, IsTodayAskedStockTendencyResponseWithError::class.java)
        coEvery {
            service.isTodayAskedStockTendency(
                url = any(),
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
    fun `addAskStockTendencyLog_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = AddAskStockTendencyLogResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.addAskStockTendencyLog(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        webImpl.addAskStockTendencyLog(
            stockId = "2330"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun addAskStockTendencyLog_success() = testScope.runTest {
        val responseBody = AddAskStockTendencyLogResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.addAskStockTendencyLog(
                url = any(),
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
    fun addAskStockTendencyLog_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"不合理的股票代號"},"error":{"Code":101,"Message":"不合理的股票代號"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, AddAskStockTendencyLogResponseWithError::class.java)
        coEvery {
            service.addAskStockTendencyLog(
                url = any(),
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
    fun `addAskStockTendencyAmount_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = AskStockTendencyAmountResponseWithError(
            amount = 1
        )
        coEvery {
            service.getAskStockTredencyAmount(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                stockId = any()
            )
        } returns Response.success(responseBody)

        webImpl.getAskStockTendencyAmount(
            stockId = "2330"
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAskStockTendencyAmount_success() = testScope.runTest {
        val responseBody = AskStockTendencyAmountResponseWithError(
            amount = 1
        )
        coEvery {
            service.getAskStockTredencyAmount(
                url = any(),
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
    fun getAskStockTendencyAmount_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"不合理的股票代號"},"error":{"Code":101,"Message":"不合理的股票代號"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, AskStockTendencyAmountResponseWithError::class.java)
        coEvery {
            service.getAskStockTredencyAmount(
                url = any(),
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
    fun `likeArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = LikeArticleResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.likeArticle(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        webImpl.likeArticle(
            articleId = 12345678
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun likeArticle_success() = testScope.runTest {
        val responseBody = LikeArticleResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.likeArticle(
                url = any(),
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
    fun likeArticle_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody = gson.fromJson(responseBodyJson, LikeArticleResponseWithError::class.java)
        coEvery {
            service.likeArticle(
                url = any(),
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
    fun `getStockArticleList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetStockArticleListResponseWithError(
            articles = emptyList()
        )
        coEvery {
            service.getStockArticleList(
                url = capture(urlSlot),
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

        webImpl.getStockArticleList(
            articleId = 12345678,
            stockId = "2330",
            fetchSize = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockArticleList_success() = testScope.runTest {
        val responseBody = GetStockArticleListResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getStockArticleList(
                url = any(),
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
    fun getStockArticleList_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetStockArticleListResponseWithError::class.java)
        coEvery {
            service.getStockArticleList(
                url = any(),
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
    fun `getReplyArticleList_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetReplyArticleListResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getReplyArticleList(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        webImpl.getReplyArticleList(
            articleId = 12345678
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getReplyArticleList_success() = testScope.runTest {
        val responseBody = GetReplyArticleListResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getReplyArticleList(
                url = any(),
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
    fun getReplyArticleList_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetReplyArticleListResponseWithError::class.java)
        coEvery {
            service.getReplyArticleList(
                url = any(),
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
    fun `getForumLatestArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetForumLatestArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumLatestArticles(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                baseArticleId = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        webImpl.getForumLatestArticles(
            baseArticleId = 0,
            fetchCount = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getForumLatestArticles_success() = testScope.runTest {
        val responseBody = GetForumLatestArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumLatestArticles(
                url = any(),
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
    fun getForumLatestArticles_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetForumLatestArticlesResponseWithError::class.java)
        coEvery {
            service.getForumLatestArticles(
                url = any(),
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
    fun `getForumPopularArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetForumPopularArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumPopularArticles(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                skipCount = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        webImpl.getForumPopularArticles(
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }
    @Test
    fun getForumPopularArticles_success() = testScope.runTest {
        val responseBody = GetForumPopularArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumPopularArticles(
                url = any(),
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
    fun getForumPopularArticles_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetForumPopularArticlesResponseWithError::class.java)
        coEvery {
            service.getForumPopularArticles(
                url = any(),
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
    fun `getChannelPopularArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetForumPopularArticlesResponseWithError(
            articles = arrayListOf(),
            updatedInSeconds = 20
        )
        coEvery {
            service.getForumPopularArticles(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                skipCount = any(),
                fetchCount = any(),
                IsIncludeLimitedAskArticle = any()
            )
        } returns Response.success(responseBody)

        webImpl.getForumPopularArticles(
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getChannelPopularArticles_success() = testScope.runTest {
        val response = GetChannelPopularArticlesResponseWithError(
            articles = listOf()
        )
        coEvery {
            service.getChannelPopularArticles(
                url = any(),
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
    fun getChannelPopularArticles_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetChannelPopularArticlesResponseWithError::class.java)
        coEvery {
            service.getChannelPopularArticles(
                url = any(),
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

    @Test
    fun `getSingleArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetSingleArticleResponseWithError(
            articleFrom = null,
            articleId = null,
            articleType = null,
            askInfo = null,
            authorChannelId = null,
            authorImage = null,
            authorName = null,
            content = null,
            contentImage = null,
            contentVideoPath = null,
            createTime = null,
            diamondInfo = null,
            dislikeCount = null,
            hasAuthToReadReply = null,
            isCollected = null,
            isDisliked = null,
            isFollowedAuthor = null,
            isLiked = null,
            levelInfo = null,
            likeCount = null,
            multipleImages = listOf(),
            replyCount = null,
            replyList = listOf(),
            sourceUrl = null,
            tipInfo = null,
            title = null,
            stockTags = listOf(),
            stockInfos = listOf()
        )
        coEvery {
            service.getSingleArticle(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
            )
        } returns Response.success(responseBody)
        webImpl.getSingleArticle(
            articleId = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getSingleArticle_success() = testScope.runTest {
        val responseBody = GetSingleArticleResponseWithError(
            articleFrom = null,
            articleId = null,
            articleType = null,
            askInfo = null,
            authorChannelId = null,
            authorImage = null,
            authorName = null,
            content = null,
            contentImage = null,
            contentVideoPath = null,
            createTime = null,
            diamondInfo = null,
            dislikeCount = null,
            hasAuthToReadReply = null,
            isCollected = null,
            isDisliked = null,
            isFollowedAuthor = null,
            isLiked = null,
            levelInfo = null,
            likeCount = null,
            multipleImages = listOf(),
            replyCount = null,
            replyList = listOf(),
            sourceUrl = null,
            tipInfo = null,
            title = null,
            stockTags = listOf(),
            stockInfos = listOf()
        )
        coEvery {
            service.getSingleArticle(
                url = any(),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
            )
        } returns Response.success(responseBody)
        val result = webImpl.getSingleArticle(
            articleId = 0
        )
        Truth.assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun getSingleArticle_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetSingleArticleResponseWithError::class.java)
        coEvery {
            service.getSingleArticle(
                url = any(),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
            )
        } returns Response.success(responseBody)
        val result = webImpl.getSingleArticle(
            articleId = 0
        )
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull() as ServerException
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception.code).isEqualTo(9001)
    }

    @Test
    fun `getFollowedChannelArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetFollowedChannelArticlesResponseWithError(
            articles = emptyList()
        )
        coEvery {
            service.getFollowedChannelArticles(
                url = capture(urlSlot),
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

        webImpl.getFollowedChannelArticles(
            channelCategory = ChannelCategory.PEOPLE,
            baseArticleId = Long.MAX_VALUE,
            fetchCount = 20,
            isIncludeLimitedAskArticle = true
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getFollowedChannelArticles_success() = testScope.runTest {
        val responseBody = GetFollowedChannelArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getFollowedChannelArticles(
                url = any(),
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
    fun getFollowedChannelArticles_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetFollowedChannelArticlesResponseWithError::class.java)
        coEvery {
            service.getFollowedChannelArticles(
                url = any(),
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
    fun `getPopularQuestionArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetPopularQuestionArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getPopularQuestionArticles(
                url = capture(urlSlot),
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                stockIdList = any()
            )
        } returns Response.success(responseBody)

        webImpl.getPopularQuestionArticles(
            fetchCount = 20,
            skipCount = 0,
            stockIdList = listOf()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getPopularQuestionArticles_success() = testScope.runTest {
        val responseBody = GetPopularQuestionArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getPopularQuestionArticles(
                url = any(),
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
    fun getPopularQuestionArticles_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetPopularQuestionArticlesResponseWithError::class.java)
        coEvery {
            service.getPopularQuestionArticles(
                url = any(),
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
    fun `getLatestQuestionArticles_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GetLatestQuestionArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getLatestQuestionArticles(
                url = capture(urlSlot),
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                fetchCount = any(),
                skipCount = any(),
                stockIdList = any()
            )
        } returns Response.success(responseBody)

        webImpl.getLatestQuestionArticles(
            fetchCount = 20,
            skipCount = 0,
            stockIdList = listOf()
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getLatestQuestionArticles_success() = testScope.runTest {
        val responseBody = GetLatestQuestionArticlesResponseWithError(
            articles = arrayListOf()
        )
        coEvery {
            service.getLatestQuestionArticles(
                url = any(),
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
    fun getLatestQuestionArticles_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetLatestQuestionArticlesResponseWithError::class.java)
        coEvery {
            service.getLatestQuestionArticles(
                url = any(),
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
    fun `followChannel_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = FollowChannelResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.followChannel(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                channelId = any()
            )
        } returns Response.success(responseBody)

        webImpl.followChannel(
            channelId = 1443597
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun followChannel_success() = testScope.runTest {
        val responseBody = FollowChannelResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.followChannel(
                url = any(),
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
    fun followChannel_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, FollowChannelResponseWithError::class.java)
        coEvery {
            service.followChannel(
                url = any(),
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
    fun `leaveChannel_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = LeaveChannelResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.leaveChannel(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                channelId = any()
            )
        } returns Response.success(responseBody)

        webImpl.leaveChannel(
            channelId = 1443597
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun leaveChannel_success() = testScope.runTest {
        val responseBody = LeaveChannelResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.leaveChannel(
                url = any(),
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
    fun leaveChannel_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, LeaveChannelResponseWithError::class.java)
        coEvery {
            service.leaveChannel(
                url = any(),
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
    fun `updateChannelDescription_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = UpdateChannelIdDescriptionResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.updateChannelDescription(
                url = capture(urlSlot),
                authorization = any(),
                action = any(),
                guid = any(),
                appId = any(),
                description = any()
            )
        } returns Response.success(responseBody)

        webImpl.updateChannelDescription(
            description = ""
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun updateChannelDescription_success() = testScope.runTest {
        val responseBody = UpdateChannelIdDescriptionResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.updateChannelDescription(
                url = any(),
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
    fun updateChannelDescription_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, UpdateChannelIdDescriptionResponseWithError::class.java)
        coEvery {
            service.updateChannelDescription(
                url = any(),
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
    fun `giveArticleTip_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = GiveArticleTipResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.giveArticleTip(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                tip = any()
            )
        } returns Response.success(responseBody)

        webImpl.giveArticleTip(
            articleId = 12345678,
            tip = 10
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun giveArticleTip_success() = testScope.runTest {
        val responseBody = GiveArticleTipResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.giveArticleTip(
                url = any(),
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
    fun giveArticleTip_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GiveArticleTipResponseWithError::class.java)
        coEvery {
            service.giveArticleTip(
                url = any(),
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
    fun `addInterestedInArticleInfo_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = AddInterestedInArticleInfoResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.addInterestedInArticleInfo(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any(),
                points = any()
            )
        } returns Response.success(responseBody)

        webImpl.addInterestedInArticleInfo(
            articleId = 12345678,
            points = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun addInterestedInArticleInfo_success() = testScope.runTest {
        val responseBody = AddInterestedInArticleInfoResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.addInterestedInArticleInfo(
                url = any(),
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
    fun addInterestedInArticleInfo_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, AddInterestedInArticleInfoResponseWithError::class.java)
        coEvery {
            service.addInterestedInArticleInfo(
                url = any(),
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
    fun `dislikeArticle_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val responseBody = DisLikeArticleResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.dislikeArticle(
                url = capture(urlSlot),
                action = any(),
                guid = any(),
                authorization = any(),
                appId = any(),
                articleId = any()
            )
        } returns Response.success(responseBody)

        webImpl.dislikeArticle(
            articleId = 12345678
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun dislikeArticle_success() = testScope.runTest {
        val responseBody = DisLikeArticleResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.dislikeArticle(
                url = any(),
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
    fun dislikeArticle_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":700374,"Message":"已經使用此功能！"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, DisLikeArticleResponseWithError::class.java)
        coEvery {
            service.dislikeArticle(
                url = any(),
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
    fun `getFansChannel_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val response = GetFansChannelResponseWithError(
            channels = emptyList()
        )
        coEvery {
            service.getFansChannel(
                url = capture(urlSlot),
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

        webImpl.getFansChannel(
            channelId = 0,
            needInfo = NeedInfo.All,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getFansChannel_success() = testScope.runTest {
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
                url = any(),
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
    fun getFansChannel_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetFansChannelResponseWithError::class.java)
        coEvery {
            service.getFansChannel(
                url = any(),
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
    fun `getAttentionChannel_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val response = GetAttentionChannelResponseWithError(
            channels = emptyList()
        )
        coEvery {
            service.getAttentionChannel(
                url = capture(urlSlot),
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

        webImpl.getAttentionChannel(
            channelId = 0,
            needInfo = NeedInfo.All,
            skipCount = 0,
            fetchCount = 1
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getAttentionChannel_success() = testScope.runTest {
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
                url = any(),
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
    fun getAttentionChannel_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetAttentionChannelResponseWithError::class.java)
        coEvery {
            service.getAttentionChannel(
                url = any(),
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
    fun `getStockPicture_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val response = GetStockPictureResponseWithError(
            image = "http://fsv.cmoney.tw/cmstatic/t/images/article/1443597/52d9772f-51dd-4f29-a2d3-a3029eda1ee3.png"
        )
        coEvery {
            service.getStockPicture(
                url = capture(urlSlot),
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                stockId = "2330",
                pictureType = any()
            )
        } returns Response.success(response)

        webImpl.getStockPicture(
            stockId = "2330",
            type = PictureType.RealTimeTrend
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getStockPicture_success() = testScope.runTest {
        val response = GetStockPictureResponseWithError(
            image = "http://fsv.cmoney.tw/cmstatic/t/images/article/1443597/52d9772f-51dd-4f29-a2d3-a3029eda1ee3.png"
        )
        coEvery {
            service.getStockPicture(
                url = any(),
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
    fun getStockPicture_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":9001,"Message":"參數錯誤"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetStockPictureResponseWithError::class.java)
        coEvery {
            service.getStockPicture(
                url = any(),
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
    fun `activeFollow_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val response = ActiveFollowResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.activeFollow(
                url = capture(urlSlot),
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)

        webImpl.activeFollow()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun activeFollow_success() = testScope.runTest {
        val response = ActiveFollowResponseWithError(
            isSuccess = true
        )
        coEvery {
            service.activeFollow(
                url = any(),
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
    fun activeFollow_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, ActiveFollowResponseWithError::class.java)
        coEvery {
            service.activeFollow(
                url = any(),
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
    fun `getMemberMasterRanking_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val response = GetMemberMasterRankingResponseWithError(
            hasRanking = true,
            ranking = 2,
            hasLastRanking = true,
            lastRanking = 10,
            popularity = 6.6
        )
        coEvery {
            service.getMemberMasterRanking(
                url = capture(urlSlot),
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any()
            )
        } returns Response.success(response)

        webImpl.getMemberMasterRanking()
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getMemberMasterRanking_success() = testScope.runTest {
        val response = GetMemberMasterRankingResponseWithError(
            hasRanking = true,
            ranking = 2,
            hasLastRanking = true,
            lastRanking = 10,
            popularity = 6.6
        )
        coEvery {
            service.getMemberMasterRanking(
                url = any(),
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
    fun getMemberMasterRanking_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetMemberMasterRankingResponseWithError::class.java)
        coEvery {
            service.getMemberMasterRanking(
                url = any(),
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
    fun `getArticleTips_check url`() = testScope.runTest {
        val expect = "${EXCEPT_DOMAIN}MobileService/ashx/MobileCode.ashx"
        val urlSlot = slot<String>()
        val response = GetArticleTipsResponseWithError(
            tips = listOf()
        )
        coEvery {
            service.getArticleTips(
                url = capture(urlSlot),
                action = any(),
                appId = any(),
                guid = any(),
                authorization = any(),
                articleId = any(),
                fetchCount = any(),
                skipCount = any()
            )
        } returns Response.success(response)

        webImpl.getArticleTips(
            articleId = 0,
            fetchCount = 0,
            skipCount = 0
        )
        Truth.assertThat(urlSlot.captured).isEqualTo(expect)
    }

    @Test
    fun getArticleTips_success() = testScope.runTest {
        val response = GetArticleTipsResponseWithError(
            tips = listOf()
        )
        coEvery {
            service.getArticleTips(
                url = any(),
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
    fun getArticleTips_failure() = testScope.runTest {
        val responseBodyJson = """
            {"Error":{"Code":101,"Message":"Auth Failed"}}
        """.trimIndent()
        val responseBody =
            gson.fromJson(responseBodyJson, GetArticleTipsResponseWithError::class.java)
        coEvery {
            service.getArticleTips(
                url = any(),
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

    companion object {
        private const val EXCEPT_DOMAIN = "localhost://8080:80/"
    }
}