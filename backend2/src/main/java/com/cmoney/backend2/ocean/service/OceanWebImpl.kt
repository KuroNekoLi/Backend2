package com.cmoney.backend2.ocean.service

import androidx.annotation.IntRange
import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.request.Constant
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.ocean.extension.checkOceanResponseBody
import com.cmoney.backend2.ocean.service.api.RequestIds
import com.cmoney.backend2.ocean.service.api.answers.AnswersBody
import com.cmoney.backend2.ocean.service.api.changeallbadge.ChangeAllBadgeRequestBody
import com.cmoney.backend2.ocean.service.api.changeallbadge.ChangeWearBadgeRequestBody
import com.cmoney.backend2.ocean.service.api.changecollectarticlestate.ChangeCollectArticleStateRequestBody
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusRequestBody
import com.cmoney.backend2.ocean.service.api.changememberstatus.ChangeMemberStatusResponseBody
import com.cmoney.backend2.ocean.service.api.changememberstatus.Operation
import com.cmoney.backend2.ocean.service.api.channelquestions.ChannelQuestionnaire
import com.cmoney.backend2.ocean.service.api.channelquestions.ChannelQuestionsBody
import com.cmoney.backend2.ocean.service.api.channelquestionsactivation.ChannelQuestionsActivationBody
import com.cmoney.backend2.ocean.service.api.channelquestionsactivation.ChannelQuestionsActivationResponse
import com.cmoney.backend2.ocean.service.api.channelwearbadge.ChannelWearBadge
import com.cmoney.backend2.ocean.service.api.channelwearbadge.ChannelWearBadgeRequestBody
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubComplete
import com.cmoney.backend2.ocean.service.api.checkHasJoinedClub.HasJoinedClubRequestBody
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedRequestBody
import com.cmoney.backend2.ocean.service.api.checkhasevaluated.CheckHasEvaluatedResponseBody
import com.cmoney.backend2.ocean.service.api.club.*
import com.cmoney.backend2.ocean.service.api.createannouncement.CreateAnnouncementRequest
import com.cmoney.backend2.ocean.service.api.createannouncement.CreateAnnouncementResponse
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubRequestBody
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBody
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.deletearticle.DeleteArticleRequestBody
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubRequestBody
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBody
import com.cmoney.backend2.ocean.service.api.getAnnouncements.GetAnnouncementsResponse
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleLIstRequestBody
import com.cmoney.backend2.ocean.service.api.getCollectArticleList.GetCollectArticleListResponseBody
import com.cmoney.backend2.ocean.service.api.getanswers.GetAnswersBody
import com.cmoney.backend2.ocean.service.api.getanswers.GetAnswersResponse
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleRequestBody
import com.cmoney.backend2.ocean.service.api.getasklatestarticle.GetAskLatestArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getbadgeandrequirement.GetBadgeAndRequirementResponse
import com.cmoney.backend2.ocean.service.api.getbadgescollection.GetBadgesCollection
import com.cmoney.backend2.ocean.service.api.getbadgescollection.GetBadgesCollectionRequestBody
import com.cmoney.backend2.ocean.service.api.getblacklist.GetBlackListResponseBody
import com.cmoney.backend2.ocean.service.api.getblocklist.GetBlockListResponseBody
import com.cmoney.backend2.ocean.service.api.getchannelinfo.GetChannelInfoRequestBody
import com.cmoney.backend2.ocean.service.api.getchannellatestarticle.GetChannelLatestArticleBody
import com.cmoney.backend2.ocean.service.api.getchannellatestarticle.GetChannelLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getchannelquestions.GetChannelQuestionsBody
import com.cmoney.backend2.ocean.service.api.getchannelquestions.GetChannelQuestionsResponse
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsRequestBody
import com.cmoney.backend2.ocean.service.api.getcomments.GetCommentsResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListRequestBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.GetEvaluationListResponseBody
import com.cmoney.backend2.ocean.service.api.getevaluationlist.SortType
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubRequestBody
import com.cmoney.backend2.ocean.service.api.getfanlistexcludejoinedclub.GetFansListExcludeJoinedClubResponseBody
import com.cmoney.backend2.ocean.service.api.getmanagerlist.GetManagerList
import com.cmoney.backend2.ocean.service.api.getmanagerlist.GetManagerListRequestBody
import com.cmoney.backend2.ocean.service.api.getmasters.GetMastersRequestBody
import com.cmoney.backend2.ocean.service.api.getmasters.GetMastersResponseBody
import com.cmoney.backend2.ocean.service.api.getmasters.MasterType
import com.cmoney.backend2.ocean.service.api.getmemberclubs.GetMemberClubsRequestBody
import com.cmoney.backend2.ocean.service.api.getmemberclubs.GetMemberClubsResponseBody
import com.cmoney.backend2.ocean.service.api.getmemberstatuslist.GetMemberStatusListRequestBody
import com.cmoney.backend2.ocean.service.api.getmemberstatuslist.GetMemberStatusListResponseBody
import com.cmoney.backend2.ocean.service.api.getmetricsstats.GetMetricsStats
import com.cmoney.backend2.ocean.service.api.getmetricsstats.GetMetricsStatsRequestBody
import com.cmoney.backend2.ocean.service.api.getnotify.GetNotifyRequestBody
import com.cmoney.backend2.ocean.service.api.getnotify.GetNotifyResponseBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.GetRecommendClubsRequestBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.GetRecommendClubsResponseBody
import com.cmoney.backend2.ocean.service.api.getrecommendclubs.RecommendClubsNeedInfo
import com.cmoney.backend2.ocean.service.api.getrelevantcomments.GetRelevantCommentsRequest
import com.cmoney.backend2.ocean.service.api.getrelevantcomments.GetRelevantCommentsResponse
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoRequestBody
import com.cmoney.backend2.ocean.service.api.getsimplechannelinfo.GetSimpleChannelInfoResponseBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleRequestBody
import com.cmoney.backend2.ocean.service.api.getsinglearticle.GetSingleArticleResponseBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesRequestBody
import com.cmoney.backend2.ocean.service.api.getstockandtopicarticles.GetStockAndTopicArticlesResponseBody
import com.cmoney.backend2.ocean.service.api.getstocklatestarticle.GetStockLatestArticleBody
import com.cmoney.backend2.ocean.service.api.getstocklatestarticle.GetStockLatestArticleResponse
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationRequestBody
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluation.GetStockMasterEvaluationResponseBody
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListRequestBody
import com.cmoney.backend2.ocean.service.api.getstockmasterevaluationlist.GetStockMasterEvaluationListResponseBody
import com.cmoney.backend2.ocean.service.api.getstockpopulararticle.GetStockPopularArticleBody
import com.cmoney.backend2.ocean.service.api.getstockpopulararticle.GetStockPopularArticleResponse
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesRequestBody
import com.cmoney.backend2.ocean.service.api.gettopicarticles.GetTopicArticlesResponseBody
import com.cmoney.backend2.ocean.service.api.getunreadbadges.GetUnreadBadgesRequestBody
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountRequestBody
import com.cmoney.backend2.ocean.service.api.getunreadcount.GetUnreadCountResponseBody
import com.cmoney.backend2.ocean.service.api.hadphoneauthentication.HadPhoneAuthResponse
import com.cmoney.backend2.ocean.service.api.hadphoneauthentication.HadPhoneAuthenticationBody
import com.cmoney.backend2.ocean.service.api.impeacharticle.ImpeachArticleBody
import com.cmoney.backend2.ocean.service.api.invite.InviteRequestBody
import com.cmoney.backend2.ocean.service.api.invite.InviteResponseBody
import com.cmoney.backend2.ocean.service.api.isinwhitelist.IsInCreateArticleWhiteListRequestBody
import com.cmoney.backend2.ocean.service.api.isinwhitelist.IsInCreateArticleWhiteListResponseBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubRequestBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubRequestBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBody
import com.cmoney.backend2.ocean.service.api.putonblacklist.PutOnBlackListRequestBody
import com.cmoney.backend2.ocean.service.api.removeannouncement.RemoveAnnouncementRequest
import com.cmoney.backend2.ocean.service.api.removeannouncement.RemoveAnnouncementResponse
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelRequestBody
import com.cmoney.backend2.ocean.service.api.searchchannel.SearchChannelResponseBody
import com.cmoney.backend2.ocean.service.api.setbadgeread.SetBadgeReadRequestBody
import com.cmoney.backend2.ocean.service.api.setevaluation.SetEvaluationRequestBody
import com.cmoney.backend2.ocean.service.api.setreaded.NotifyIdAndIsSpecificPair
import com.cmoney.backend2.ocean.service.api.setreaded.SetReadedRequestBody
import com.cmoney.backend2.ocean.service.api.spinoffblacklist.SpinOffBlackListRequestBody
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionRequestBody
import com.cmoney.backend2.ocean.service.api.updateclubdescription.UpdateClubDescriptionResponseBody
import com.cmoney.backend2.ocean.service.api.uploadchannelimage.UploadChannelImageResponseBody
import com.cmoney.backend2.ocean.service.api.variable.*
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import java.lang.reflect.Type

class OceanWebImpl(
    private val gson: Gson,
    private val oceanService: OceanService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : OceanWeb {

    /**
     * 全穿或脫掉所有徽章
     *
     * @param apiParam
     * @param isWear
     * @return
     */
    override suspend fun changeAllBadge(
        apiParam: MemberApiParam,
        isWear: Boolean
    ): Result<SuccessResult> = changeAllBadge(isWear)


    override suspend fun changeAllBadge(isWear: Boolean): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.changeAllBadge(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ChangeAllBadgeRequestBody(
                        wear = isWear,
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 更換裝備的徽章(目前單選由前端控制)
     *
     * @param apiParam
     * @param isWear
     * @param badgeIds
     * @return
     */
    override suspend fun changeWearBadge(
        apiParam: MemberApiParam,
        isWear: Boolean,
        badgeIds: List<Int>
    ): Result<SuccessResult> = changeWearBadge(isWear, badgeIds)

    override suspend fun changeWearBadge(
        isWear: Boolean,
        badgeIds: List<Int>
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.changeWearBadge(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = ChangeWearBadgeRequestBody(
                    badgeIds = badgeIds,
                    wear = isWear,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 取得所有達成徽章及達成條件
     *
     * @return
     */
    override suspend fun getBadgeAndRequirement(): Result<List<GetBadgeAndRequirementResponse>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = oceanService.getBadgeAndRequirement()
                solvedJsonArrayResponseQuestion<List<GetBadgeAndRequirementResponse>>(response)
            }
        }

    /**
     * 取得所有徽章持有狀態
     *
     * @param apiParam
     * @return
     */
    override suspend fun getBadgesCollection(
        apiParam: MemberApiParam
    ): Result<List<GetBadgesCollection>> = getBadgesCollection()

    override suspend fun getBadgesCollection(): Result<List<GetBadgesCollection>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = oceanService.getBadgesCollection(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetBadgesCollectionRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                )
                solvedJsonArrayResponseQuestion<List<GetBadgesCollection>>(response)
            }
        }


    /**
     * 取得目前所有指標清單
     *
     * @param apiParam
     * @return
     */
    override suspend fun getMetricsStats(
        apiParam: MemberApiParam
    ): Result<List<GetMetricsStats>> = getMetricsStats()

    override suspend fun getMetricsStats(): Result<List<GetMetricsStats>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = oceanService.getMetricsStats(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetMetricsStatsRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                )
                solvedJsonArrayResponseQuestion<List<GetMetricsStats>>(response)
            }
        }

    /**
     * 取得未讀徽章
     *
     * @param apiParam
     * @return
     */
    override suspend fun getUnreadBadges(
        apiParam: MemberApiParam
    ): Result<List<Int>> = getUnreadBadges()

    override suspend fun getUnreadBadges(): Result<List<Int>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = oceanService.getUnreadBadges(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetUnreadBadgesRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            )
            solvedJsonArrayResponseQuestion<List<Int>>(response)
        }
    }

    /**
     * 取得頻道裝備的徽章(多個頻道)
     *
     * @param apiParam
     * @param channelIds
     * @return
     */
    override suspend fun channelWearBadge(
        apiParam: MemberApiParam,
        channelIds: List<Long>
    ): Result<List<ChannelWearBadge>> = channelWearBadge(channelIds)

    override suspend fun channelWearBadge(channelIds: List<Long>): Result<List<ChannelWearBadge>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = oceanService.channelWearBadge(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ChannelWearBadgeRequestBody(
                        channelIds = channelIds,
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                )
                solvedJsonArrayResponseQuestion<List<ChannelWearBadge>>(response)
            }
        }

    /**
     * 標記徽章已讀
     *
     * @param apiParam
     * @param badgeId
     * @return
     */
    override suspend fun setBadgeRead(
        apiParam: MemberApiParam,
        badgeId: Long
    ): Result<SuccessResult> = setBadgeRead(badgeId)

    override suspend fun setBadgeRead(badgeId: Long): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.setBadgeRead(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = SetBadgeReadRequestBody(
                        badgeId = badgeId,
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得社團問卷
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    override suspend fun getChannelQuestions(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<GetChannelQuestionsResponse> = getChannelQuestions(channelId)

    override suspend fun getChannelQuestions(
        channelId: Long
    ): Result<GetChannelQuestionsResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getChannelQuestions(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetChannelQuestionsBody(
                    channelId = channelId,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 新增/更新社團問卷
     *
     * @param apiParam
     * @param questionnaire
     * @return
     */
    override suspend fun channelQuestions(
        apiParam: MemberApiParam,
        questionnaire: ChannelQuestionnaire
    ): Result<SuccessResult> = channelQuestions(questionnaire)

    override suspend fun channelQuestions(
        questionnaire: ChannelQuestionnaire
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.channelQuestions(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = ChannelQuestionsBody(
                    questionnaire = questionnaire,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 開啟或關閉加入社團問卷
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    override suspend fun channelQuestionsActivation(
        apiParam: MemberApiParam,
        isActive: Boolean,
        channelId: Long
    ): Result<ChannelQuestionsActivationResponse> = channelQuestionsActivation(isActive, channelId)

    override suspend fun channelQuestionsActivation(
        isActive: Boolean,
        channelId: Long
    ): Result<ChannelQuestionsActivationResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.channelQuestionsActivation(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ChannelQuestionsActivationBody(
                        isActive = isActive,
                        channelId = channelId,
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得問卷回答
     *
     * @param apiParam
     * @param channelId
     * @param memberChannelIds
     * @param questionIds
     * @return
     */
    override suspend fun getAnswers(
        apiParam: MemberApiParam,
        channelId: Long,
        memberChannelIds: List<Long>,
        questionIds: List<Long>
    ): Result<GetAnswersResponse> = getAnswers(channelId, memberChannelIds, questionIds)

    override suspend fun getAnswers(
        channelId: Long,
        memberChannelIds: List<Long>,
        questionIds: List<Long>
    ): Result<GetAnswersResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.getAnswers(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = GetAnswersBody(
                        memberChannelIds = memberChannelIds,
                        questionIds = questionIds,
                        channelId = channelId,
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 新增問卷回答
     *
     * @param apiParam
     * @param answers
     * @return
     */
    override suspend fun answers(
        apiParam: MemberApiParam,
        answers: List<AnswerParam>
    ): Result<SuccessResult> = answers(answers)

    override suspend fun answers(answers: List<AnswerParam>): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.answers(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = AnswersBody(
                        answers = answers,
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 查詢多用戶是否有手機權限
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    override suspend fun hadPhoneAuthentication(
        apiParam: MemberApiParam,
        channelId: List<Long>
    ): Result<HadPhoneAuthResponse> = hadPhoneAuthentication(channelId)

    override suspend fun hadPhoneAuthentication(channelId: List<Long>): Result<HadPhoneAuthResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.hadPhoneAuthentication(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = HadPhoneAuthenticationBody(
                        appId = setting.appId,
                        channelIdList = channelId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                )
                    .checkResponseBody(gson)
            }
        }

    /**
     * 取得股票最新文章清單
     *
     * @param apiParam
     * @param baseArticleId
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param stockIdList
     * @param articleNeedInfo
     * @param filterType
     * @return
     */
    override suspend fun getStockLatestArticle(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockLatestArticleResponse> = getStockLatestArticle(
        baseArticleId,
        fetchCount,
        isIncludeLimitedAskArticle,
        stockIdList,
        articleNeedInfo,
        filterType
    )

    override suspend fun getStockLatestArticle(
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockLatestArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getStockLatestArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetStockLatestArticleBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    isIncludeLimitedAskArticle = isIncludeLimitedAskArticle,
                    stockIdList = stockIdList,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    filterType = filterType.value
                )
            )
                .checkResponseBody(gson)
        }
    }

    /**
     * 取得頻道最新清單
     *
     * @param apiParam
     * @param baseArticleId
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param channelIdList
     * @param articleNeedInfo
     * @return
     */
    override suspend fun getChannelLatestArticle(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        @IntRange(from = 0, to = 20)
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        channelIdList: List<Long>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetChannelLatestArticleResponse> = getChannelLatestArticle(
        baseArticleId, fetchCount, isIncludeLimitedAskArticle, channelIdList, articleNeedInfo
    )

    override suspend fun getChannelLatestArticle(
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        channelIdList: List<Long>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetChannelLatestArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getChannelLatestArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetChannelLatestArticleBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    isIncludeLimitedAskArticle = isIncludeLimitedAskArticle,
                    channelIdList = channelIdList,
                    articleNeedInfo = articleNeedInfo.getCombinedResult()
                )
            )
                .checkResponseBody(gson)
        }
    }

    /**
     * 檢舉文章
     *
     * @param apiParam
     * @param articleId 被檢舉的文章Id
     * @param reason 檢舉原因 (最多50字)(檢舉原因有:垃圾廣告、人身攻擊、根本詐騙集團、與投資無關)
     * @return
     */
    override suspend fun impeachArticle(
        apiParam: MemberApiParam,
        articleId: Long,
        reason: String
    ): Result<SuccessResult> = impeachArticle(articleId, reason)

    override suspend fun impeachArticle(articleId: Long, reason: String): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.impeachArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = ImpeachArticleBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid(),
                        articleId = articleId,
                        reason = reason
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得股票最熱文章
     *
     * @param apiParam
     * @param skipCount
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @param stockIdList
     * @param articleNeedInfo
     * @param filterType
     * @return
     */
    override suspend fun getStockPopularArticle(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockPopularArticleResponse> = getStockPopularArticle(
        skipCount, fetchCount, isIncludeLimitedAskArticle, stockIdList, articleNeedInfo, filterType
    )

    override suspend fun getStockPopularArticle(
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType
    ): Result<GetStockPopularArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getStockPopularArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetStockPopularArticleBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount,
                    isIncludeLimitedAskArticle = isIncludeLimitedAskArticle,
                    stockIdList = stockIdList,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    filterType = filterType.value
                )
            )
                .checkResponseBody(gson)
        }
    }

    /**
     * 加入我的黑名單成功or失敗(原本就存在黑名單內視為成功)
     *
     * @param apiParam
     * @param blackChannelId
     * @return
     */
    override suspend fun putOnBlackList(
        apiParam: MemberApiParam,
        blackChannelId: Long
    ): Result<SuccessResult> = putOnBlackList(blackChannelId)


    override suspend fun putOnBlackList(blackChannelId: Long): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.putOnBlackList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = PutOnBlackListRequestBody(
                        blackChannelId = blackChannelId,
                        guid = setting.identityToken.getMemberGuid(),
                        appId = setting.appId
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 從我的黑名單移除成功or失敗(原本就不存在黑名單內也視為成功)
     *
     * @param apiParam
     * @param blackChannelId
     * @return
     */
    override suspend fun spinOffBlackList(
        apiParam: MemberApiParam,
        blackChannelId: Long
    ): Result<SuccessResult> = spinOffBlackList(blackChannelId)

    override suspend fun spinOffBlackList(blackChannelId: Long): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.spinOffBlackList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = SpinOffBlackListRequestBody(
                        blackChannelId = blackChannelId,
                        guid = setting.identityToken.getMemberGuid(),
                        appId = setting.appId
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得我的黑名單
     *
     * @param apiParam
     * @return
     */
    override suspend fun getBlackList(
        apiParam: MemberApiParam
    ): Result<GetBlackListResponseBody> = getBlackList()

    override suspend fun getBlackList(): Result<GetBlackListResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.getBlackList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = RequestIds(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得黑名單我的人
     *
     * @param apiParam
     * @return
     */
    override suspend fun getBlockList(
        apiParam: MemberApiParam
    ): Result<GetBlockListResponseBody> = getBlockList()

    override suspend fun getBlockList(): Result<GetBlockListResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.getBlockList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = RequestIds(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得通知
     *
     * @param apiParam
     * @param isIncludeClub
     * @param lowerBoundNotifyTime
     * @param notifyTypes
     * @param fetchCount
     * @return
     */
    override suspend fun getNotify(
        apiParam: MemberApiParam,
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        fetchCount: Int
    ): Result<GetNotifyResponseBody> = getNotify(
        isIncludeClub,
        lowerBoundNotifyTime,
        notifyTypes,
        fetchCount
    )

    override suspend fun getNotify(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        fetchCount: Int
    ): Result<GetNotifyResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getNotify(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetNotifyRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    isIncludeClub = isIncludeClub,
                    upperBoundNotifyTime = lowerBoundNotifyTime,
                    notifyTypes = notifyTypes.map { it.value },
                    fetchCount = fetchCount
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 取得未讀通知數
     *
     * @param apiParam
     * @param isIncludeClub
     * @param lowerBoundNotifyTime
     * @param notifyTypes
     * @return
     */
    override suspend fun getUnreadCount(
        apiParam: MemberApiParam,
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>
    ): Result<GetUnreadCountResponseBody> = getUnreadCount(
        isIncludeClub, lowerBoundNotifyTime, notifyTypes
    )

    override suspend fun getUnreadCount(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>
    ): Result<GetUnreadCountResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getUnreadCount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetUnreadCountRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    isIncludeClub = isIncludeClub,
                    lowerBoundNotifyTime = lowerBoundNotifyTime,
                    notifyTypesList = notifyTypes.map { it.value }
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 設定已讀通知
     *
     * @param apiParam
     * @param notifyIdAndIsSpecificPair
     * @return
     */
    override suspend fun setReaded(
        apiParam: MemberApiParam,
        notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>
    ): Result<Unit> = setReaded(notifyIdAndIsSpecificPair)

    override suspend fun setReaded(
        notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.setReaded(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = SetReadedRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    notifyIdAndIsSpecificPair = notifyIdAndIsSpecificPair
                )
            )
                .handleNoContent(gson)
        }
    }

    /**
     * 取得指定文章
     *
     * @param apiParam
     * @param articleId
     * @param articleNeedInfo
     * @return
     */
    override suspend fun getSingleArticle(
        apiParam: MemberApiParam,
        articleId: Long,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetSingleArticleResponseBody> = getSingleArticle(articleId, articleNeedInfo)

    override suspend fun getSingleArticle(
        articleId: Long,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetSingleArticleResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getSingleArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetSingleArticleRequestBody(
                    appId = setting.appId,
                    articleId = articleId,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 取得指定文章的回覆
     *
     * @param apiParam
     * @param articleId
     * @param upperBoundArticleId
     * @param fetchCount
     * @return
     */
    override suspend fun getComments(
        apiParam: MemberApiParam,
        articleId: Long,
        upperBoundArticleId: Long,
        fetchCount: Int
    ): Result<GetCommentsResponseBody> = getComments(
        articleId, upperBoundArticleId, fetchCount
    )

    override suspend fun getComments(
        articleId: Long,
        upperBoundArticleId: Long,
        fetchCount: Int
    ): Result<GetCommentsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getComments(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetCommentsRequestBody(
                    appId = setting.appId,
                    articleId = articleId,
                    fetchCount = fetchCount,
                    guid = setting.identityToken.getMemberGuid(),
                    upperBoundArticleId = upperBoundArticleId
                )
            ).checkResponseBody(gson)
        }
    }


    override suspend fun getManagerList(
        apiParam: MemberApiParam,
        needInfo: Int,
        channelId: Long
    ): Result<GetManagerList> = getManagerList(needInfo, channelId)

    override suspend fun getManagerList(needInfo: Int, channelId: Long): Result<GetManagerList> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.getManagerList(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = GetManagerListRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid(),
                        channelId = channelId,
                        needInfo = needInfo
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 解決回傳是JsonArray的方法
     *
     * @param T
     * @param response
     * @return
     */
    @Throws(ServerException::class)
    private inline fun <reified T> solvedJsonArrayResponseQuestion(
        response: Response<JsonElement>
    ): T {
        val jsonString = response
            .checkIsSuccessful()
            .requireBody()
        val result: T? = if (jsonString.isJsonArray) {
            try {
                gson.parseResult<T>(jsonString, object : TypeToken<T>() {}.type)
            } catch (ex: JsonSyntaxException) {
                null
            }
        } else {
            null
        }

        return if (result != null) {
            result
        } else {
            val error =
                gson.parseResult<CMoneyError>(jsonString, object : TypeToken<CMoneyError>() {}.type)
            throw ServerException(
                error?.detail?.code ?: Constant.SERVICE_ERROR_CODE,
                error?.detail?.message.orEmpty()
            )
        }
    }

    override suspend fun deleteArticle(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<SuccessResult> = deleteArticle(articleId)

    override suspend fun deleteArticle(articleId: Long): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.deleteArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = DeleteArticleRequestBody.Builder().build(setting, articleId)
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 是否有參加的社團
     * @param apiParam
     * @param channelId 查詢會員頻道編號
     * @param relation 跟社團的關係
     */
    override suspend fun isJoinedClub(
        apiParam: MemberApiParam,
        channelId: Long,
        relation: Relation
    ): Result<HasJoinedClubComplete> = isJoinedClub(
        channelId, relation
    )

    override suspend fun isJoinedClub(
        channelId: Long,
        relation: Relation
    ): Result<HasJoinedClubComplete> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.isJoinClub(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = HasJoinedClubRequestBody(
                    setting.appId,
                    setting.identityToken.getMemberGuid(),
                    channelId,
                    relation.value
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 取得指定頻道資訊(其他類型頻道)
     *
     * @param apiParam
     * @param channelId 頻道Id
     * @param needInfo 需要額外附加的資訊
     * @return
     */
    override suspend fun getOtherChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Other>
    ): Result<ChannelInfo.ChannelBaseInfo> = getOtherChannelInfo(channelId, needInfo)

    override suspend fun getOtherChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Other>
    ): Result<ChannelInfo.ChannelBaseInfo> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getOtherChannelInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = setting.appId,
                    channelId = channelId,
                    guid = setting.identityToken.getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 取得指定頻道資訊(RSS、訊號頻道資訊)
     *
     * @param apiParam
     * @param channelId 頻道Id
     * @param needInfo 需要額外附加的資訊
     * @return
     */
    override suspend fun getRssSignalChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.RssSignal>
    ): Result<ChannelInfo.RssSignalChannelInfo> = getRssSignalChannelInfo(
        channelId, needInfo
    )

    override suspend fun getRssSignalChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.RssSignal>
    ): Result<ChannelInfo.RssSignalChannelInfo> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getRssSignalChannelInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = setting.appId,
                    channelId = channelId,
                    guid = setting.identityToken.getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 取得指定頻道資訊(會員頻道資訊)
     *
     * @param apiParam
     * @param channelId 頻道Id
     * @param needInfo 需要額外附加的資訊
     * @return
     */
    override suspend fun getMemberChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<ChannelInfo.MemberChannelInfo> = getMemberChannelInfo(channelId, needInfo)

    override suspend fun getMemberChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<ChannelInfo.MemberChannelInfo> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = oceanService.getMemberChannelInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = setting.appId,
                    channelId = channelId,
                    guid = setting.identityToken.getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            )
            response.checkResponseBody(gson)
        }
    }

    /**
     * 取得指定頻道資訊(社團頻道資訊)
     *
     * @param apiParam
     * @param channelId 頻道Id
     * @param needInfo 需要額外附加的資訊
     * @return
     */
    override suspend fun getClubChannelInfo(
        apiParam: MemberApiParam,
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>
    ): Result<ChannelInfo.ClubChannelInfo> = getClubChannelInfo(channelId, needInfo)

    override suspend fun getClubChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>
    ): Result<ChannelInfo.ClubChannelInfo> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getClubChannelInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = setting.appId,
                    channelId = channelId,
                    guid = setting.identityToken.getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkResponseBody(gson)
        }
    }


    /**
     * 搜尋社團 ( 搜尋頻道 )
     *
     * @param apiParam
     * @param channelTypes 頻道類型
     * @param fetchCount 取得數量
     * @param keyword 查詢關鍵字
     */
    override suspend fun searchChannel(
        apiParam: MemberApiParam,
        channelTypes: ChannelTypes,
        fetchCount: Int,
        keyword: String
    ) = searchChannel(channelTypes, fetchCount, keyword)

    override suspend fun searchChannel(
        channelTypes: ChannelTypes,
        fetchCount: Int,
        keyword: String
    ): Result<SearchChannelResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.searchChannel(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = SearchChannelRequestBody(
                    appId = setting.appId,
                    channelTypes = channelTypes,
                    fetchCount = fetchCount,
                    guid = setting.identityToken.getMemberGuid(),
                    keyword = keyword
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 取得排除加入社團的粉絲清單(已邀請或審核中或黑名單的粉絲也會排除)
     *
     * @param apiParam
     * @param needInfo 需要額外附加的資訊
     * @param excludeClubChannelId 排除已加入的社團頻道編號
     * @param fetchCount 清單取得的數量
     * @param skipCount 清單前面跳過的數量
     */
    override suspend fun getFanListExcludeJoinedClub(
        apiParam: MemberApiParam,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        excludeClubChannelId: Long,
        fetchCount: Int,
        skipCount: Int
    ) = getFanListExcludeJoinedClub(needInfo, excludeClubChannelId, fetchCount, skipCount)

    override suspend fun getFanListExcludeJoinedClub(
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        excludeClubChannelId: Long,
        fetchCount: Int,
        skipCount: Int
    ): Result<GetFansListExcludeJoinedClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getFanListExcludeJoinedClub(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody =
                GetFansListExcludeJoinedClubRequestBody(
                    appId = setting.appId,
                    excludeClubChannelId = excludeClubChannelId,
                    fetchCount = fetchCount,
                    guid = setting.identityToken.getMemberGuid(),
                    needInfo = needInfo.getCombinedResult(),
                    skipCount = skipCount
                )
            ).checkResponseBody(gson)
        }
    }

    /**
     * 取得頻道基本資訊(多筆)
     *
     * @param apiParam
     * @param channelIds 頻道清單
     * @return
     */
    override suspend fun getSimpleChannelInfo(
        apiParam: MemberApiParam,
        channelIds: List<Long>
    ): Result<GetSimpleChannelInfoResponseBody> = getSimpleChannelInfo(channelIds)

    override suspend fun getSimpleChannelInfo(channelIds: List<Long>): Result<GetSimpleChannelInfoResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.getSimpleChannelInfo(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = GetSimpleChannelInfoRequestBody(
                        appId = setting.appId,
                        channelIds = channelIds,
                        guid = setting.identityToken.getMemberGuid()
                    )
                ).checkResponseBody(gson)
            }
        }

    /**
     * 設定評價
     *
     * @param apiParam
     * @param channelId 頻道編號
     * @param content 評價內容(限200字)
     * @param score 給幾顆星(最多5顆星)
     * @return
     */
    override suspend fun setEvaluation(
        apiParam: MemberApiParam,
        channelId: Long,
        content: String,
        score: Int
    ): Result<SuccessResult> = setEvaluation(channelId, content, score)

    override suspend fun setEvaluation(
        channelId: Long,
        content: String,
        score: Int
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.setEvaluation(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = SetEvaluationRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = channelId,
                    content = content,
                    score = score
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 是否給過評價
     *
     * @param apiParam
     * @param channelId 頻道編號
     * @return
     */
    override suspend fun checkHasEvaluated(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<CheckHasEvaluatedResponseBody> = checkHasEvaluated(channelId)

    override suspend fun checkHasEvaluated(channelId: Long): Result<CheckHasEvaluatedResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                oceanService.checkHasEvaluated(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    requestBody = CheckHasEvaluatedRequestBody(
                        appId = setting.appId,
                        guid = setting.identityToken.getMemberGuid(),
                        channelId = channelId
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 取得指定用戶的評價清單
     *
     * @param apiParam
     * @param channelId 頻道編號
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param sortType 排序方式:使用Enum(SortType)
     * @return
     */
    override suspend fun getEvaluationList(
        apiParam: MemberApiParam,
        channelId: Long,
        fetchCount: Int,
        skipCount: Int,
        sortType: SortType
    ): Result<GetEvaluationListResponseBody> =
        getEvaluationList(channelId, fetchCount, skipCount, sortType)

    override suspend fun getEvaluationList(
        channelId: Long,
        fetchCount: Int,
        skipCount: Int,
        sortType: SortType
    ): Result<GetEvaluationListResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getEvaluationList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetEvaluationListRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = channelId,
                    fetchCount = fetchCount,
                    skipCount = skipCount,
                    sortType = sortType.value
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun changeCollectArticleState(
        articleId: Long,
        isCollect: Boolean
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = oceanService.changeCollectArticleState(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = ChangeCollectArticleStateRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    articleId = articleId,
                    isCollected = isCollect
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun changeCollectArticleState(
        apiParam: MemberApiParam,
        articleId: Long,
        isCollect: Boolean
    ): Result<SuccessResult> = changeCollectArticleState(articleId, isCollect)

    /**
     * 取得收藏文章清單
     *
     * @param apiParam MemberApiParam
     * @param skipCount 略過幾筆
     * @param fetchCount 取得幾筆
     * @param articleNeedInfo 需要額外的文章相關資訊(二進位表示,參考通訊備註8-A)
     * @param isIncludeLimitedAskArticle 是否包未解答的問答文章 (預設:false)
     */
    override suspend fun getCollectArticleList(
        apiParam: MemberApiParam,
        skipCount: Int,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetCollectArticleListResponseBody> = getCollectArticleList(
        skipCount, fetchCount, articleNeedInfo, isIncludeLimitedAskArticle
    )

    override suspend fun getCollectArticleList(
        skipCount: Int,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetCollectArticleListResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = oceanService.getCollectArticleList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetCollectArticleLIstRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    skipCount = skipCount,
                    fetchCount = fetchCount,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    isIncludeLimitedAskArticle = isIncludeLimitedAskArticle
                )
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 取得大師排行榜
     *
     * @param apiParam MemberApiParam
     * @param masterType MasterType 解答大師排名(1-熱門值,2-粉絲增長,3-鑽石增長)
     * @param fetchCount Int 取得幾筆
     */
    override suspend fun getMasters(
        apiParam: MemberApiParam,
        masterType: MasterType,
        fetchCount: Int
    ): Result<GetMastersResponseBody> = getMasters(masterType, fetchCount)

    override suspend fun getMasters(
        masterType: MasterType,
        fetchCount: Int
    ): Result<GetMastersResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getMasters(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetMastersRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    masterType = masterType.value,
                    fetchCount = fetchCount
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 取得問答最新文章
     *
     * @param apiParam MemberApiParam
     * @param baseArticleId 從哪篇文章Id開取拿文章
     * @param fetchCount 拿取篇數
     * @param stockListList 篩選需要哪些股票問答的清單
     * @param articleNeedInfo 需要那些文章資訊
     * @return
     */
    override suspend fun getAskLatestArticle(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        fetchCount: Int,
        stockListList: List<String>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetAskLatestArticleResponseBody> =
        getAskLatestArticle(baseArticleId, fetchCount, stockListList, articleNeedInfo)

    override suspend fun getAskLatestArticle(
        baseArticleId: Long,
        fetchCount: Int,
        stockListList: List<String>,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetAskLatestArticleResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getAskLatestArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetAskLatestArticleRequestBody(
                    appId = setting.appId,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    guid = setting.identityToken.getMemberGuid(),
                    stockIdList = stockListList
                )
            ).checkOceanResponseBody(gson)
        }
    }

    override suspend fun getStockMasterEvaluationList(
        apiParam: MemberApiParam,
        stockIdList: List<String>
    ): Result<GetStockMasterEvaluationListResponseBody> = getStockMasterEvaluationList(stockIdList)

    /**
     * 取得多股大師評價分數
     */
    override suspend fun getStockMasterEvaluationList(
        stockIdList: List<String>
    ): Result<GetStockMasterEvaluationListResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getStockMasterEvaluationList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetStockMasterEvaluationListRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    stockIds = stockIdList
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getStockMasterEvaluation(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<GetStockMasterEvaluationResponseBody> = getStockMasterEvaluation(stockId)

    /**
     * 取得個股大師評價
     * @param apiParam MemberApiParam
     * @param stockId
     */
    override suspend fun getStockMasterEvaluation(
        stockId: String
    ): Result<GetStockMasterEvaluationResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getStockMasterEvaluation(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetStockMasterEvaluationRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    stockId = stockId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun uploadChannelImage(
        apiParam: MemberApiParam,
        channelId: Long,
        image: File?
    ): Result<UploadChannelImageResponseBody> = uploadChannelImage(channelId, image)

    /**
     * 上傳社團頭像
     * @param apiParam MemberApiParam
     * @param channelId 欲變更的頻道編號
     * @param image 欲變更的頭像
     */
    override suspend fun uploadChannelImage(
        channelId: Long,
        image: File?
    ): Result<UploadChannelImageResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.apply {
                addFormDataPart("AppId", setting.appId.toString())
                addFormDataPart("Guid", setting.identityToken.getMemberGuid())
                addFormDataPart("ChannelId", channelId.toString())
                image?.apply {
                    addFormDataPart("File", this.name, this.asRequestBody())
                }
            }

            val response = oceanService.uploadChannelImage(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = builder.build()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun createClub(
        apiParam: MemberApiParam,
        clubName: String,
        description: String,
        joinMethod: JoinMethod
    ): Result<CreateClubResponseBody> = createClub(clubName, description, joinMethod)

    /**
     * 創建社團
     * @param apiParam MemberApiParam
     * @param clubName 社團名稱(12字以內)
     * @param description 社團介紹
     * @param joinMethod 加入方式 1:公開社團(自由參加，不需審核) 4:非公開社團(需審核)
     */
    override suspend fun createClub(
        clubName: String,
        description: String,
        joinMethod: JoinMethod
    ): Result<CreateClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.createClub(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = CreateClubRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    name = clubName,
                    description = description,
                    joinMethod = joinMethod.value
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()

        }
    }

    override suspend fun deleteClub(
        apiParam: MemberApiParam,
        clubChannelId: Long
    ): Result<DeleteClubResponseBody> = deleteClub(clubChannelId)

    /**
     * 關閉社團
     * apiParam: MemberApiParam,
     * @param clubChannelId 社團頻道編號
     */
    override suspend fun deleteClub(
        clubChannelId: Long
    ): Result<DeleteClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.deleteClub(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = DeleteClubRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    clubChannelId = clubChannelId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun leaveClub(
        apiParam: MemberApiParam,
        clubChannelId: Long
    ): Result<LeaveClubResponseBody> = leaveClub(clubChannelId)

    /**
     * 離開社團
     * apiParam: MemberApiParam,
     * @param clubChannelId 社團頻道編號
     */
    override suspend fun leaveClub(
        clubChannelId: Long
    ): Result<LeaveClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.leaveClub(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = LeaveClubRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    clubChannelId = clubChannelId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun invite(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        memberChannelIds: List<Long>
    ): Result<InviteResponseBody> = invite(clubChannelId, memberChannelIds)

    /**
     * 邀請加入社團
     * apiParam: MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param memberChannelIds 邀請加入的會員頻道編號清單(以 , 分隔)
     */
    override suspend fun invite(
        clubChannelId: Long,
        memberChannelIds: List<Long>
    ): Result<InviteResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.invite(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = InviteRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    clubChannelId = clubChannelId,
                    memberChannelIds = memberChannelIds.joinToString()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun joinClub(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        message: String
    ): Result<JoinClubResponseBody> = joinClub(clubChannelId, message)

    /**
     * 加入社團
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param message 申請訊息(審核用，可不填) (40字以內)
     */
    override suspend fun joinClub(
        clubChannelId: Long,
        message: String
    ): Result<JoinClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.joinClub(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = JoinClubRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = clubChannelId,
                    message = message
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getMemberClubs(
        apiParam: MemberApiParam,
        memberChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        relation: Relation
    ): Result<GetMemberClubsResponseBody> = getMemberClubs(memberChannelId, needInfo, relation)

    /**
     * 取得指定會員的社團清單
     * @param apiParam MemberApiParam
     * @param memberChannelId 查詢會員頻道編號
     * @param needInfo 需要額外的頻道資訊
     */
    override suspend fun getMemberClubs(
        memberChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        relation: Relation
    ): Result<GetMemberClubsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getMemberClubs(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetMemberClubsRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    memberChannelId = memberChannelId,
                    needInfo = needInfo.getCombinedResult(),
                    relation = relation.value
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getRecommendClubs(
        apiParam: MemberApiParam,
        fetchCount: Int,
        skipCount: Int,
        needInfo: RecommendClubsNeedInfo
    ): Result<GetRecommendClubsResponseBody> = getRecommendClubs(
        fetchCount, skipCount, needInfo
    )

    /**
     * 取得推薦社團
     * @param apiParam MemberApiParam
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param needInfo 需要額外的頻道資訊
     */
    override suspend fun getRecommendClubs(
        fetchCount: Int,
        skipCount: Int,
        needInfo: RecommendClubsNeedInfo
    ): Result<GetRecommendClubsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getRecommendClubs(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetRecommendClubsRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    fetchCount = fetchCount,
                    skipCount = skipCount,
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun changeMemberStatus(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        operation: Operation
    ): Result<ChangeMemberStatusResponseBody> = changeMemberStatus(
        clubChannelId,
        memberChannelIds,
        operation
    )

    /**
     * 改變會員身分
     * @param apiParam MemberApiParam
     * @param clubChannelId 社團頻道編號
     * @param memberChannelIds 以 , 分隔的會員頻道清單
     * @param operation 執行操作
     */
    override suspend fun changeMemberStatus(
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        operation: Operation
    ): Result<ChangeMemberStatusResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.changeMemberStatus(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = ChangeMemberStatusRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    clubChannelId = clubChannelId,
                    memberChannelIds = memberChannelIds.joinToString(),
                    operation = operation.value
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun updateClubDescription(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        description: String
    ): Result<UpdateClubDescriptionResponseBody> = updateClubDescription(
        clubChannelId, description
    )

    /**
     * 改變會員身分
     * @param clubChannelId 社團頻道編號
     * @param description 社團介紹 (140字以內)
     */
    override suspend fun updateClubDescription(
        clubChannelId: Long,
        description: String
    ): Result<UpdateClubDescriptionResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.updateClubDescription(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = UpdateClubDescriptionRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    clubChannelId = clubChannelId,
                    description = description
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getMemberStatusList(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        memberPosition: MemberPosition,
        fetchCount: Int,
        skipCount: Int,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetMemberStatusListResponseBody> = getMemberStatusList(
        clubChannelId,
        memberPosition,
        fetchCount,
        skipCount,
        needInfo
    )

    /**
     * 取得指定身份的社團成員清單(審核清單,黑名單)
     * @param apiParam MemberApiParam
     * @param clubChannelId 查詢社團頻道編號
     * @param memberPosition 會員在社團的狀態
     * @param fetchCount 取得幾筆
     * @param skipCount 略過幾筆
     * @param needInfo 需要額外的頻道資訊
     */
    override suspend fun getMemberStatusList(
        clubChannelId: Long,
        memberPosition: MemberPosition,
        fetchCount: Int,
        skipCount: Int,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetMemberStatusListResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getMemberStatusList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetMemberStatusListRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = clubChannelId,
                    status = memberPosition.value,
                    fetchCount = fetchCount,
                    skipCount = skipCount,
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getManagerList(
        apiParam: MemberApiParam,
        clubChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetManagerList> = getManagerList(clubChannelId, needInfo)

    /**
     * 取得社團管理者清單
     * @param apiParam MemberApiParam
     * @param clubChannelId 查詢社團頻道編號
     * @param needInfo 需要額外的頻道資訊
     */
    override suspend fun getManagerList(
        clubChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>
    ): Result<GetManagerList> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getManagerList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                requestBody = GetManagerListRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = clubChannelId,
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAnnouncements(
        channelId: Long
    ): Result<GetAnnouncementsResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getAnnouncements(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = RequestIds(
                    setting.identityToken.getMemberGuid(),
                    setting.appId
                ),
                channelId = channelId.toString()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun removeAnnouncements(
        channelId: Long,
        isPinned: Boolean,
        articleId: Long
    ): Result<RemoveAnnouncementResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.removeAnnouncement(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = RemoveAnnouncementRequest(
                    articleId = articleId,
                    isPinned = isPinned,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId.toLong()
                ),
                channelId = channelId.toString()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun createAnnouncement(
        channelId: Long,
        isPinned: Boolean,
        articleId: Long
    ): Result<CreateAnnouncementResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.createAnnouncement(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = CreateAnnouncementRequest(
                    articleId = articleId,
                    isPinned = isPinned,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                ),
                channelId = channelId.toString()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getRelevantComments(
        articleIds: List<Long>,
        fetch: Long
    ): Result<GetRelevantCommentsResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getRelevantComments(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetRelevantCommentsRequest(
                    articleIds = articleIds,
                    fetch = fetch,
                    guid = setting.identityToken.getMemberGuid(),
                    appId = setting.appId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getTopicArticles(
        topic: String,
        baseArticleId: Long,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetTopicArticlesResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getTopicArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetTopicArticlesRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    topic = topic,
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    articleNeedInfo = articleNeedInfo.getCombinedResult()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getStockAndTopicArticles(
        stockId: String,
        topic: String,
        baseArticleId: Long,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo
    ): Result<GetStockAndTopicArticlesResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.getStockAndTopicArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = GetStockAndTopicArticlesRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    stockId = stockId,
                    topic = topic,
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    articleNeedInfo = articleNeedInfo.getCombinedResult()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 新增或更新公告
     * @param clubChannelId Long
     * @param articleId Long
     * @param isPinned Boolean
     */
    override suspend fun createOrUpdateAnnouncement(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean
    ): Result<IsCreateOrUpdateSuccessResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.createOrUpdateAnnouncement(
                authorization = setting.accessToken.createAuthorizationBearer(),
                channelId = clubChannelId,
                requestBody = CreateOrUpdateAnnouncementRequestBody(
                    articleId = articleId,
                    isPinned = isPinned,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 拿到所有公告
     * @param clubChannelId Long
     */
    override suspend fun getAllAnnouncements(
        clubChannelId: Long
    ): Result<AnnouncementListResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.readAnnouncement(
                authorization = setting.accessToken.createAuthorizationBearer(),
                channelId = clubChannelId,
                requestBody = ReadAnnouncementsRequestBody(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 刪除特定公告
     * @param clubChannelId Long
     * @param articleId Long
     * @param isPinned Boolean
     */
    override suspend fun removeAnnouncements(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean
    ): Result<IsRemoveAnnouncementSuccessResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            oceanService.removeAnnouncement(
                authorization = setting.accessToken.createAuthorizationBearer(),
                channelId = clubChannelId,
                requestBody = RemoveAnnouncementRequestBody(
                    articleId = articleId,
                    isPinned = isPinned,
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }


    /**
     * 解析json回傳
     *
     * @param T
     * @param jsonString
     * @param type
     * @return
     */
    @Throws(JsonSyntaxException::class)
    private inline fun <reified T> Gson.parseResult(
        jsonString: JsonElement?,
        type: Type
    ): T? {
        return this.fromJson<T>(jsonString, type)
    }
}