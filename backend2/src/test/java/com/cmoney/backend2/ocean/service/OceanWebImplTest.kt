package com.cmoney.backend2.ocean.service

import com.cmoney.backend2.MainCoroutineRule
import com.cmoney.backend2.TestDispatcher
import com.cmoney.backend2.TestSetting
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.changememberstatus.Operation
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubResponseWithError
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.club.AnnouncementListResponseWithError
import com.cmoney.backend2.ocean.service.api.club.IsCreateOrUpdateSuccessResponseWithError
import com.cmoney.backend2.ocean.service.api.club.IsRemoveAnnouncementSuccessWithError
import com.cmoney.backend2.ocean.service.api.createannouncement.CreateAnnouncementResponseWithError
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getAnnouncements.GetAnnouncementsWithError
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getblacklist.GetBlackListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getblocklist.GetBlockListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubRequestBody
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
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoRequestBody
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoResponseBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountResponseBody
import com.cmoney.backend2.ocean.service.api.invite.InviteResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.isinwhitelist.IsInCreateArticleWhiteListResponseBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.removeannouncement.RemoveAnnouncementResponseWithError
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelResponseBody
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.uploadchannelimage.UploadChannelImageResponseBodyWithError
import com.cmoney.backend2.ocean.service.api.variable.*
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class OceanWebImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private val oceanService = mockk<OceanService>()
    private val gson = GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create()
    private lateinit var service: OceanWeb

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        service = OceanWebImpl(gson, oceanService, TestSetting(), TestDispatcher())
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `putOnBlackList加入黑名單 成功回傳`() = mainCoroutineRule.runBlockingTest {

        //準備api成功時的回傳
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.putOnBlackList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.putOnBlackList(105399L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test
    fun `putOnBlackList加入黑名單 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.putOnBlackList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.putOnBlackList(105399L)
        checkServerException(result)
    }

    @Test
    fun `spinOffBlackList移除黑名單 成功回傳`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.spinOffBlackList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.spinOffBlackList(105399L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
    }

    @Test
    fun `spinOffBlackList移除黑名單 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, SuccessResultWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.spinOffBlackList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.spinOffBlackList(105399L)
        checkServerException(result)
    }

    @Test
    fun `getBlackList取得黑名單清單 listof(22222)`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetBlackListResponseBodyWithError(
            channelIds = listOf(222222)
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getBlackList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getBlackList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelIds).hasSize(1)
    }

    @Test
    fun `getBlackList取得黑名單清單 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetBlackListResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.getBlackList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getBlackList()
        checkServerException(result)
    }

    @Test
    fun `getBlockList取得被黑名單清單 listof(22222)`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetBlockListResponseBodyWithError(
            channelIds = listOf(222222)
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getBlockList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getBlockList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelIds).hasSize(1)
    }

    @Test
    fun `getBlockList取得被黑名單清單 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetBlockListResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.getBlockList(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getBlockList()
        checkServerException(result)
    }

    @Test
    fun `getNotify取得通知訊息`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getNotify(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getNotify(

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
    fun `getUnReadCount取得未讀通知數`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetUnreadCountResponseBody(
            data = 25
        )

        coEvery {
            oceanService.getUnreadCount(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getUnreadCount(

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
    fun `setReaded設定已讀通知`() = mainCoroutineRule.runBlockingTest {
        val responseBody =
            RealResponseBody(contentTypeString = null, contentLength = 0, source = Buffer())

        coEvery {
            oceanService.setReaded(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.setReaded(

            notifyIdAndIsSpecificPair = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.contentLength()).isEqualTo(0)
    }

    @Test
    fun `getSingleArticle 取得單一文章(如果有回覆會有最多20篇回覆)`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getSingleArticle(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getSingleArticle(
            articleId = articleId, articleNeedInfo = ArticleNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.data?.article?.articleId).isEqualTo(articleId)
    }

    @Test
    fun `getComments 取得指定文章的回覆`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getComments(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getComments(
            articleId = articleId, upperBoundArticleId = 0, fetchCount = 20
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.data?.size).isEqualTo(1)
    }

    @Test
    fun `getOtherChannelInfo 取得指定頻道資訊(其他類型頻道)`() = mainCoroutineRule.runBlockingTest {
        val responseBody = ChannelInfo.ChannelBaseInfo(
            channelId = 1000,
            channelName = null,
            description = null,
            image = null
        )

        coEvery {
            oceanService.getOtherChannelInfo(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getOtherChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `getMemberChannelInfo 取得指定頻道資訊(會員頻道資訊)`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getMemberChannelInfo(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getMemberChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `getClubChannelInfo 取得指定頻道資訊(社團頻道資訊)`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getClubChannelInfo(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getClubChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `getRssSignalChannelInfo 取得指定頻道資訊(RSS、訊號頻道資訊)`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getRssSignalChannelInfo(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getRssSignalChannelInfo(
            channelId = 1000, needInfo = ChannelNeedInfo()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
    }

    @Test
    fun `searchChannel 搜尋社團 ( 搜尋頻道 )`() = mainCoroutineRule.runBlockingTest {
        val responseBody = SearchChannelResponseBody(
            clubs = listOf()
        )

        coEvery {
            oceanService.searchChannel(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.searchChannel(

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
    fun `getFanListExcludeJoinedClub 取得排除加入社團的粉絲清單(已邀請或審核中或黑名單的粉絲也會排除)`() =
        mainCoroutineRule.runBlockingTest {
            val responseBody = GetFansListExcludeJoinedClubResponseBody(
                channels = listOf()
            )

            coEvery {
                oceanService.getFanListExcludeJoinedClub(
                    authorization = any(),
                    requestBody = any()
                )
            } returns Response.success(responseBody)

            //確認api是否成功
            val result = service.getFanListExcludeJoinedClub(

                needInfo = ChannelNeedInfo(),
                excludeClubChannelId = 0,
                fetchCount = 0,
                skipCount = 0
            )
            Truth.assertThat(result.isSuccess).isTrue()

            //確認api回傳是否如預期
            val data = result.getOrThrow()
            Truth.assertThat(data.channels?.size).isEqualTo(0)
        }

    @Test
    fun `getSimpleChannelInfo 取得頻道基本資訊(多筆)`() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetSimpleChannelInfoResponseBody(
            channelInfo = listOf()
        )

        coEvery {
            oceanService.getSimpleChannelInfo(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getSimpleChannelInfo(
            channelIds = listOf()
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelInfo?.size).isEqualTo(0)
    }

    @Test
    fun `setEvaluation 設定評價`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = SuccessResultWithError(
            isSuccess = true
        )

        //設定api成功時的回傳
        coEvery {
            oceanService.setEvaluation(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.setEvaluation(105399L, "", 0)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isEqualTo(true)
    }

    @Test
    fun `checkHasEvaluated 是否給過評價`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = CheckHasEvaluatedResponseBodyWithError(
            hasEvaluated = true
        )

        //設定api成功時的回傳
        coEvery {
            oceanService.checkHasEvaluated(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.checkHasEvaluated(105399L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.hasEvaluated).isEqualTo(true)
    }

    @Test
    fun `getEvaluationList 取得指定用戶的評價清單`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetEvaluationListResponseBodyWithError(
            evaluations = listOf()
        )

        //設定api成功時的回傳
        coEvery {
            oceanService.getEvaluationList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getEvaluationList(105399L, 10, 0, SortType.LatestToOldest)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
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
    fun `changeCollectArticleState_收藏文章成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = SuccessResultWithError(true)
        coEvery { oceanService.changeCollectArticleState(any(), any()) }
            .returns(Response.success(responseBody))

        val result = service.changeCollectArticleState(1, true)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `changeCollectArticleState_取消收藏文章成功`() = mainCoroutineRule.runBlockingTest {
        val responseBody = SuccessResultWithError(false)
        coEvery { oceanService.changeCollectArticleState(any(), any()) }
            .returns(Response.success(responseBody))

        val result = service.changeCollectArticleState(1, true)
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `changeCollectArticleState_收藏文章失敗`() = mainCoroutineRule.runBlockingTest {
        val errorBody = """
            {"Error":{"Code":100,"Message":"參數錯誤"}}
        """.trimIndent()
        val errorResponse =
            gson.fromJson<SuccessResultWithError>(errorBody, SuccessResultWithError::class.java)
        coEvery { oceanService.changeCollectArticleState(any(), any()) }
            .returns(Response.success(errorResponse))

        val result = service.changeCollectArticleState(1, true)
        Truth.assertThat(result.isSuccess).isFalse()
        val exception = result.exceptionOrNull()
        Truth.assertThat(exception).isNotNull()
        Truth.assertThat(exception).isInstanceOf(ServerException::class.java)
        require(exception is ServerException)
        Truth.assertThat(exception.code).isEqualTo(100)
    }

    @Test
    fun `getCollectArticleList取得收藏文章清單`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetCollectArticleListResponseBodyWithError(
            articles = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getCollectArticleList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getCollectArticleList(0, 20, ArticleNeedInfo(), true)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.articles?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getCollectArticleList取得收藏文章清單 發生ServerException`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getCollectArticleList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getCollectArticleList(0, 20, ArticleNeedInfo(), true)
        checkServerException(result)
    }

    @Test
    fun `isInCreateArticleWhiteList取得使用者是否在白名單中 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = IsInCreateArticleWhiteListResponseBody(
            isInWhiteList = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.isInCreateArticleWhiteList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.isInCreateArticleWhiteList()
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isInWhiteList).isTrue()
    }

    @Test
    fun `isInCreateArticleWhiteList取得使用者是否在白名單中 失敗`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val json = ""
        //設定api成功時的回傳
        coEvery {
            oceanService.isInCreateArticleWhiteList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.isInCreateArticleWhiteList()
        checkHttpException(result)
    }

    @Test
    fun `getMasters取得大師排行榜 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetMastersResponseBodyWithError(
            masters = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getMasters(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getMasters(MasterType.Popularity, 20)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.masters?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getMasters取得大師排行榜 失敗`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val json = ""
        //設定api成功時的回傳
        coEvery {
            oceanService.getMasters(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.getMasters(MasterType.Popularity, 20)
        checkHttpException(result)
    }

    @Test
    fun `getAskLatestArticle取得最新問答文章 成功回傳`() = mainCoroutineRule.runBlockingTest {

        //準備api成功時的回傳
        val responseBody = GetAskLatestArticleResponseBody(
            articles = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getAskLatestArticle(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getAskLatestArticle(0, 20, listOf(), ArticleNeedInfo())
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.articles?.isEmpty()).isTrue()
    }

    @Test
    fun `getAskLatestArticle取得最新問答文章 發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        //設定api成功時的回傳
        coEvery {
            oceanService.getAskLatestArticle(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(400, json.toResponseBody())

        //確認api是否成功
        val result = service.getAskLatestArticle(-1, 20, listOf(), ArticleNeedInfo())
        checkServerException(result)
    }

    @Test
    fun `getAskLatestArticle取得最新問答文章 發生401HttpException`() = mainCoroutineRule.runBlockingTest {
        //設定api成功時的回傳
        coEvery {
            oceanService.getAskLatestArticle(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, "".toResponseBody())

        //確認api是否成功
        val result = service.getAskLatestArticle(
            0, 20, listOf("字串"),
            ArticleNeedInfo()
        )
        checkHttpException(result)
    }

    @Test
    fun `getStockMasterEvaluationList取得多股大師評價分數 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetStockMasterEvaluationListResponseBodyWithError(
            masterEvaluationScores = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getStockMasterEvaluationList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getStockMasterEvaluationList(listOf("2330"))
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.masterEvaluationScores?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getStockMasterEvaluationList取得多股大師評價分數 失敗`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val json = ""
        //設定api成功時的回傳
        coEvery {
            oceanService.getStockMasterEvaluationList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.getStockMasterEvaluationList(listOf("2330"))
        checkHttpException(result)
    }

    @Test
    fun `getStockMasterEvaluation取得個股大師評價 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetStockMasterEvaluationResponseBodyWithError(
            avgScores = 7.2,
            channelId = 1000,
            closePr = null,
            trends = null
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getStockMasterEvaluation(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getStockMasterEvaluation(
            "2330"
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.avgScores).isEqualTo(7.2)
        Truth.assertThat(data.channelId).isEqualTo(1000)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getStockMasterEvaluation取得個股大師評價 失敗`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val json = ""
        //設定api成功時的回傳
        coEvery {
            oceanService.getStockMasterEvaluation(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.error(401, json.toResponseBody())

        //確認api是否成功
        val result = service.getStockMasterEvaluation(
            "2330"
        )
        checkHttpException(result)
    }

    @Test
    fun `uploadChannelImage上傳社團頭像 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = UploadChannelImageResponseBodyWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.uploadChannelImage(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.uploadChannelImage(105498)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `uploadChannelImage上傳社團頭像 失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.uploadChannelImage(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.uploadChannelImage(105498)
        checkServerException(result)
    }

    @Test
    fun `createClub創建社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = CreateClubResponseBodyWithError(
            channelId = 1000
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.createClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.createClub("我的新社團", "新的呦", JoinMethod.Join)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channelId).isEqualTo(1000)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `createClub創建社團 失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.createClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.createClub("我的新社團", "新的呦", JoinMethod.Join)
        checkServerException(result)
    }

    @Test
    fun `deleteClub關閉社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = DeleteClubResponseBodyWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.deleteClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.deleteClub(0L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `deleteClub關閉社團 失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.deleteClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.deleteClub(0L)
        checkServerException(result)
    }

    @Test
    fun `leaveClub離開社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = LeaveClubResponseBodyWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.leaveClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.leaveClub(0L)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `leaveClub離開社團 失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.leaveClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.leaveClub(0L)
        checkServerException(result)
    }

    @Test
    fun `checkHasJoinedClub是否有參加的社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = HasJoinedClubResponseWithError(
            isJoin = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.isJoinClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.isJoinedClub(2850768, Relation.ClubCreated)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isJoin).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `checkHasJoinedClub是否有參加的社團 失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.isJoinClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.isJoinedClub(2850768, Relation.ClubCreated)
        checkServerException(result)
    }

    @Test
    fun `invite邀請加入社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = InviteResponseBodyWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.invite(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.invite(4277314, listOf(7869455))
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `invite邀請加入社團 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, InviteResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.invite(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.invite(4277314, listOf(7869455))
        checkServerException(result)
    }

    @Test
    fun `joinClub加入社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = JoinClubResponseBodyWithError(
            memberPosition = MemberPosition.Reviewing
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.joinClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.joinClub(4277314L, "")
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.memberPosition).isEqualTo(MemberPosition.Reviewing)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `joinClub加入社團 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, JoinClubResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.joinClub(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.joinClub(0L, "")
        checkServerException(result)
    }

    @Test
    fun `getMemberClubs取得指定會員的社團清單 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetMemberClubsResponseBodyWithError(
            clubs = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getMemberClubs(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Club>()
        clubNeedInfo.add(ChannelInfoOption.Club.MemberClubInfo)
        clubNeedInfo.add(ChannelInfoOption.Club.ClubInfo)

        //確認api是否成功
        val result = service.getMemberClubs(0L, clubNeedInfo, Relation.AllClub)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.clubs?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getMemberClubs取得指定會員的社團清單 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetMemberClubsResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.getMemberClubs(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Club>()
        clubNeedInfo.add(ChannelInfoOption.Club.MemberClubInfo)
        clubNeedInfo.add(ChannelInfoOption.Club.ClubInfo)

        //確認api是否成功
        val result = service.getMemberClubs(0L, clubNeedInfo, Relation.AllClub)
        checkServerException(result)
    }

    @Test
    fun `getRecommendClubs取得推薦社團 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetRecommendClubsResponseBodyWithError(
            clubs = listOf(),
            updatedInSeconds = 1000
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getRecommendClubs(
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
        val result = service.getRecommendClubs(10, 0, clubNeedInfo)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.clubs?.size).isEqualTo(0)
        Truth.assertThat(data.updatedInSeconds).isEqualTo(1000)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getRecommendClubs取得推薦社團 失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.getRecommendClubs(
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
        val result = service.getRecommendClubs(10, 0, clubNeedInfo)
        checkServerException(result)
    }

    @Test
    fun `changeMemberStatus改變會員身分 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = ChangeMemberStatusResponseBodyWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.changeMemberStatus(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.changeMemberStatus(4277314, listOf(7869455), Operation.MoveBlackList)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `changeMemberStatus改變會員身分 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 701101,
            "Message": "沒有權限"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, ChangeMemberStatusResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.changeMemberStatus(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.changeMemberStatus(4277314, listOf(7869455), Operation.MoveBlackList)
        checkServerException(result)
    }

    @Test
    fun `updateClubDescription變更社團描述 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = UpdateClubDescriptionResponseBodyWithError(
            isSuccess = true
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.updateClubDescription(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.updateClubDescription(4277314, "社團的功能有些單調123456")
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `updateClubDescription變更社團描述 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 1001009,
            "Message": "權限不夠，不可變更。(0)"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, UpdateClubDescriptionResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.updateClubDescription(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.updateClubDescription(4277314, "社團的功能有些單調123456")
        checkServerException(result)
    }

    @Test
    fun `getMemberStatusList取得指定身份的社團成員清單(審核清單,黑名單) 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetMemberStatusListResponseBodyWithError(
            channels = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getMemberStatusList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        //確認api是否成功
        val result = service.getMemberStatusList(

            4277314,
            MemberPosition.BlackList,
            50,
            0,
            clubNeedInfo
        )
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.channels?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getMemberStatusList取得指定身份的社團成員清單(審核清單,黑名單) 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetMemberStatusListResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.getMemberStatusList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        //確認api是否成功
        val result = service.getMemberStatusList(

            4277314,
            MemberPosition.BlackList,
            50,
            0,
            clubNeedInfo
        )
        checkServerException(result)
    }

    @Test
    fun `getManagerList取得管理者清單(幹部、社長) 成功`() = mainCoroutineRule.runBlockingTest {
        //準備api成功時的回傳
        val responseBody = GetManagerListResponseWithError(
            creator = null,
            managers = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getManagerList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        //確認api是否成功
        val result = service.getManagerList(4277314, clubNeedInfo)
        Truth.assertThat(result.isSuccess).isTrue()

        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.managers?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getManagerList取得管理者清單(幹部、社長) 失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, GetManagerListResponseWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.getManagerList(
                authorization = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
        clubNeedInfo.add(ChannelInfoOption.Member.Image)

        //確認api是否成功
        val result = service.getManagerList(4277314, clubNeedInfo)
        checkServerException(result)
    }

    @Test
    fun getAnnouncementsSuccess() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetAnnouncementsWithError(listOf())
        coEvery {
            oceanService.getAnnouncements(
                authorization = any(),
                channelId = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getAnnouncements(4277314)
        Truth.assertThat(result.isSuccess).isTrue()
        //確認api回傳是否如預期
        val data = result.getOrThrow()
        Truth.assertThat(data.announcements?.size).isEqualTo(0)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getAnnouncementsFailed() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetAnnouncementsWithError::class.java)
        coEvery {
            oceanService.getAnnouncements(
                authorization = any(),
                channelId = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = service.getAnnouncements(4277314)
        checkServerException(result)
    }


    @Test
    fun removeAnnouncement() = mainCoroutineRule.runBlockingTest {
        val responseBody = RemoveAnnouncementResponseWithError(true)
        coEvery {
            oceanService.removeAnnouncement(
                authorization = any(),
                channelId = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = service.removeAnnouncements(
            4277314,
            true,
            0L
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun removeAnnouncementFailed() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, RemoveAnnouncementResponseWithError::class.java)
        coEvery {
            oceanService.removeAnnouncement(
                authorization = any(),
                channelId = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = service.removeAnnouncements(
            4277314,
            true,
            0L
        )
        checkServerException(result)
    }

    @Test
    fun createAnnouncementSuccess() = mainCoroutineRule.runBlockingTest {
        val responseBody = CreateAnnouncementResponseWithError(true)
        coEvery {
            oceanService.createAnnouncement(
                authorization = any(),
                channelId = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = service.createAnnouncement(
            4277314,
            true,
            0L
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun createAnnouncementFailed() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, CreateAnnouncementResponseWithError::class.java)
        coEvery {
            oceanService.createAnnouncement(
                authorization = any(),
                channelId = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        val result = service.createAnnouncement(
            4277314,
            true,
            0L
        )
        checkServerException(result)
    }

    @Test
    fun getRelevantCommentSuccess() = mainCoroutineRule.runBlockingTest {
        val responseBody = GetRelevantCommentsResponseWithError(listOf())
        coEvery {
            oceanService.getRelevantComments(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        val result = service.getRelevantComments(
            listOf(4277314),
            0L
        )
        Truth.assertThat(result.isSuccess).isTrue()
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun getRelevantCommentSuccessFailure() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()
        val responseBody = gson.fromJson(json, GetRelevantCommentsResponseWithError::class.java)
        coEvery {
            oceanService.getRelevantComments(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)
        checkServerException(service.getRelevantComments(listOf(4277314), 0L))
    }

    @Test
    fun `getTopicArticles取得主題標籤文章 成功回傳`() = mainCoroutineRule.runBlockingTest {

        //準備api成功時的回傳
        val responseBody = GetTopicArticlesResponseBodyWithError(
            articles = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getTopicArticles(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getTopicArticles(
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
    fun `getTopicArticles取得主題標籤文章 失敗200發生ServerException`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        val responseBody =
            gson.fromJson(json, GetTopicArticlesResponseBodyWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.getTopicArticles(
                authorization = any(),
                body = any()
            )
        } returns Response.success(200, responseBody)

        //確認api是否成功
        val result = service.getTopicArticles(
            topic = "研究報告",
            baseArticleId = 9223372036854775807,
            fetchCount = 2,
            articleNeedInfo = ArticleNeedInfo().apply {
                addAll(ArticleNeedInfo.NeedInfo.values().toList())
            })
        checkServerException(result)
    }

    @Test
    fun `getStockAndTopicArticles取得個股下有主題標籤文章 成功回傳`() = mainCoroutineRule.runBlockingTest {

        //準備api成功時的回傳
        val responseBody = GetStockAndTopicArticlesResponseBodyWithError(
            articles = listOf()
        )
        //設定api成功時的回傳
        coEvery {
            oceanService.getStockAndTopicArticles(
                authorization = any(),
                body = any()
            )
        } returns Response.success(responseBody)

        //確認api是否成功
        val result = service.getStockAndTopicArticles(
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
    fun `getStockAndTopicArticles取得個股下有主題標籤文章 失敗200有ServerException`() =
        mainCoroutineRule.runBlockingTest {
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
                oceanService.getStockAndTopicArticles(
                    authorization = any(),
                    body = any()
                )
            } returns Response.success(200, responseBody)

            //確認api是否成功
            val result = service.getStockAndTopicArticles(
                stockId = "2330",
                topic = "研究報告",
                baseArticleId = 9223372036854775807,
                fetchCount = 2,
                articleNeedInfo = ArticleNeedInfo().apply {
                    addAll(ArticleNeedInfo.NeedInfo.values().toList())
                })
            checkServerException(result)
        }

//
//    @Test
//    fun `getManagerList取得管理者清單(幹部、社長) 失敗`() = mainCoroutineRule.runBlockingTest {
//        val json = """{
//          "Error": {
//            "Code": 100,
//            "Message": "參數錯誤"
//          }
//        }""".trimIndent()
//        val responseBody =
//            gson.fromJson(json, GetManagerListResponseWithError::class.java)
//
//        //設定api成功時的回傳
//        coEvery {
//            oceanService.getManagerList(
//                requestBody = any()
//            )
//        } returns Response.success(responseBody)
//
//        val clubNeedInfo = ChannelNeedInfo<ChannelInfoOption.Member>()
//        clubNeedInfo.add(ChannelInfoOption.Member.IsFollowed)
//        clubNeedInfo.add(ChannelInfoOption.Member.LevelInfo)
//        clubNeedInfo.add(ChannelInfoOption.Member.DiamondInfo)
//        clubNeedInfo.add(ChannelInfoOption.Member.Image)
//
//        //確認api是否成功
//        val result = service.getManagerList(4277314, clubNeedInfo)
//        checkServerException(result)
//    }

    @Test
    fun `createOrUpdateAnnouncement 新增公告Api回傳失敗`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.createOrUpdateAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = service.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        checkServerException(result)
    }

    @Test
    fun `createOrUpdateAnnouncement_新增或更新公告_回傳正確(成功)`() = mainCoroutineRule.runBlockingTest {
        val json = """{"IsSuccess":true}""".trimIndent()
        val responseBody =
            gson.fromJson(json, IsCreateOrUpdateSuccessResponseWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            oceanService.createOrUpdateAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = service.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        Truth.assertThat(result.getOrNull()?.isSuccess).isEqualTo(true)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `createOrUpdateAnnouncement_新增或更新公告_回傳正確(失敗)`() = mainCoroutineRule.runBlockingTest {
        val json = """{"IsSuccess":false}""".trimIndent()
        val responseBody =
            gson.fromJson(json, IsCreateOrUpdateSuccessResponseWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            oceanService.createOrUpdateAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = service.createOrUpdateAnnouncement(
            clubChannelId = 5083102,
            articleId = 1234,
            isPinned = true
        )
        Truth.assertThat(result.getOrNull()?.isSuccess).isEqualTo(false)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `getAllAnnouncement_拿到所有公告_錯誤`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error": {
            "Code": 100,
            "Message": "參數錯誤"
          }
        }""".trimIndent()

        val responseBody =
            gson.fromJson(json, AnnouncementListResponseWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.readAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = service.getAllAnnouncements(
            clubChannelId = 5083102
        )
        checkServerException(result)
    }

    @Test
    fun `getAllAnnouncement_拿到所有資料_正確有資料回傳`() = mainCoroutineRule.runBlockingTest {
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
            oceanService.readAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = service.getAllAnnouncements(
            clubChannelId = 5083102
        )
        Truth.assertThat(result.getOrNull()?.list?.size).isEqualTo(1)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

    @Test
    fun `removeannouncement_刪除公告_失敗`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "Error":{"Code":103,"Message":"該公告不存在"}
        }""".trimIndent()

        val responseBody =
            gson.fromJson(json, IsRemoveAnnouncementSuccessWithError::class.java)

        //設定api成功時的回傳
        coEvery {
            oceanService.removeAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)
        val result = service.removeAnnouncements(
            clubChannelId = 5083102,
            articleId = 108696750,
            isPinned = false
        )
        checkServerException(result)
    }

    @Test
    fun `remove_announcement_成功`() = mainCoroutineRule.runBlockingTest {
        val json = """{
          "IsSuccess":true
        }""".trimIndent()
        val responseBody =
            gson.fromJson(json, IsRemoveAnnouncementSuccessWithError::class.java)
        //設定api成功時的回傳
        coEvery {
            oceanService.removeAnnouncement(
                authorization = any(),
                channelId = any(),
                requestBody = any()
            )
        } returns Response.success(responseBody)

        val result = service.removeAnnouncements(
            clubChannelId = 5083102,
            articleId = 108696750,
            isPinned = false
        )
        Truth.assertThat(result.getOrNull()?.isSuccess).isEqualTo(true)
        Truth.assertThat(result.exceptionOrNull()).isNull()
    }

}