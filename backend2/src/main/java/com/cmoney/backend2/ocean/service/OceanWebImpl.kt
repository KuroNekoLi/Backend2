package com.cmoney.backend2.ocean.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.handleNoContent
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.extension.toJsonArrayWithErrorResponse
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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
import com.cmoney.backend2.ocean.service.api.club.AnnouncementListResponse
import com.cmoney.backend2.ocean.service.api.club.CreateOrUpdateAnnouncementRequestBody
import com.cmoney.backend2.ocean.service.api.club.IsCreateOrUpdateSuccessResponse
import com.cmoney.backend2.ocean.service.api.club.IsRemoveAnnouncementSuccessResponse
import com.cmoney.backend2.ocean.service.api.club.ReadAnnouncementsRequestBody
import com.cmoney.backend2.ocean.service.api.club.RemoveAnnouncementRequestBody
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubRequestBody
import com.cmoney.backend2.ocean.service.api.createclub.CreateClubResponseBody
import com.cmoney.backend2.ocean.service.api.createclub.JoinMethod
import com.cmoney.backend2.ocean.service.api.deletearticle.DeleteArticleRequestBody
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubRequestBody
import com.cmoney.backend2.ocean.service.api.deleteclub.DeleteClubResponseBody
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
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubRequestBody
import com.cmoney.backend2.ocean.service.api.joinclub.JoinClubResponseBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubRequestBody
import com.cmoney.backend2.ocean.service.api.leaveclub.LeaveClubResponseBody
import com.cmoney.backend2.ocean.service.api.putonblacklist.PutOnBlackListRequestBody
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
import com.cmoney.backend2.ocean.service.api.variable.AnswerParam
import com.cmoney.backend2.ocean.service.api.variable.ArticleNeedInfo
import com.cmoney.backend2.ocean.service.api.variable.ChannelInfoOption
import com.cmoney.backend2.ocean.service.api.variable.ChannelNeedInfo
import com.cmoney.backend2.ocean.service.api.variable.ChannelTypes
import com.cmoney.backend2.ocean.service.api.variable.FilterType
import com.cmoney.backend2.ocean.service.api.variable.MemberPosition
import com.cmoney.backend2.ocean.service.api.variable.NotificationType
import com.cmoney.backend2.ocean.service.api.variable.Relation
import com.cmoney.backend2.ocean.service.api.variable.SuccessResult
import com.cmoney.backend2.ocean.service.api.variable.channelinfo.ChannelInfo
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class OceanWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: OceanService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : OceanWeb {

    override suspend fun changeAllBadge(
        isWear: Boolean,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.changeAllBadge(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ChangeAllBadgeRequestBody(
                    wear = isWear,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun changeWearBadge(
        isWear: Boolean,
        badgeIds: List<Int>,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.changeWearBadge(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ChangeWearBadgeRequestBody(
                    badgeIds = badgeIds,
                    wear = isWear,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 取得所有達成徽章及達成條件
     */
    override suspend fun getBadgeAndRequirement(
        domain: String,
        url: String
    ): Result<List<GetBadgeAndRequirementResponse>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getBadgeAndRequirement(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            )
            response.toJsonArrayWithErrorResponse(gson = gson)
        }
    }

    override suspend fun getBadgesCollection(
        domain: String,
        url: String
    ): Result<List<GetBadgesCollection>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getBadgesCollection(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetBadgesCollectionRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            )
            response.toJsonArrayWithErrorResponse(gson = gson)
        }
    }

    override suspend fun getMetricsStats(
        domain: String,
        url: String
    ): Result<List<GetMetricsStats>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getMetricsStats(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetMetricsStatsRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            )
            response.toJsonArrayWithErrorResponse(gson = gson)
        }
    }


    override suspend fun getUnreadBadges(domain: String, url: String): Result<List<Int>> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getUnreadBadges(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = GetUnreadBadgesRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid()
                    )
                )
                response.toJsonArrayWithErrorResponse(gson = gson)
            }
        }

    override suspend fun channelWearBadge(
        channelIds: List<Long>,
        domain: String,
        url: String
    ): Result<List<ChannelWearBadge>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.channelWearBadge(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ChannelWearBadgeRequestBody(
                    channelIds = channelIds,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            )
            response.toJsonArrayWithErrorResponse(gson = gson)
        }
    }

    override suspend fun setBadgeRead(
        badgeId: Long,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.setBadgeRead(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = SetBadgeReadRequestBody(
                    badgeId = badgeId,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getChannelQuestions(
        channelId: Long,
        domain: String,
        url: String
    ): Result<GetChannelQuestionsResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getChannelQuestions(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetChannelQuestionsBody(
                    channelId = channelId,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun channelQuestions(
        questionnaire: ChannelQuestionnaire,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.channelQuestions(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ChannelQuestionsBody(
                    questionnaire = questionnaire,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun channelQuestionsActivation(
        isActive: Boolean,
        channelId: Long,
        domain: String,
        url: String
    ): Result<ChannelQuestionsActivationResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.channelQuestionsActivation(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ChannelQuestionsActivationBody(
                    isActive = isActive,
                    channelId = channelId,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAnswers(
        channelId: Long,
        memberChannelIds: List<Long>,
        questionIds: List<Long>,
        domain: String,
        url: String
    ): Result<GetAnswersResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getAnswers(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetAnswersBody(
                    memberChannelIds = memberChannelIds,
                    questionIds = questionIds,
                    channelId = channelId,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun answers(
        answers: List<AnswerParam>,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.answers(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = AnswersBody(
                    answers = answers,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun hadPhoneAuthentication(
        channelId: List<Long>,
        domain: String,
        url: String
    ): Result<HadPhoneAuthResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.hadPhoneAuthentication(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = HadPhoneAuthenticationBody(
                    appId = manager.getAppId(),
                    channelIdList = channelId,
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getStockLatestArticle(
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType,
        domain: String,
        url: String
    ): Result<GetStockLatestArticleResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockLatestArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetStockLatestArticleBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    isIncludeLimitedAskArticle = isIncludeLimitedAskArticle,
                    stockIdList = stockIdList,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    filterType = filterType.value
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getChannelLatestArticle(
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        channelIdList: List<Long>,
        articleNeedInfo: ArticleNeedInfo,
        domain: String,
        url: String
    ): Result<GetChannelLatestArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getChannelLatestArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetChannelLatestArticleBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun changeCollectArticleState(
        articleId: Long,
        isCollect: Boolean,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.changeCollectArticleState(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ChangeCollectArticleStateRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun impeachArticle(
        articleId: Long,
        reason: String,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.impeachArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = ImpeachArticleBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    articleId = articleId,
                    reason = reason
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun deleteArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<SuccessResult> =
        withContext(dispatcher.io()) {
            runCatching {
                service.deleteArticle(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    requestBody = DeleteArticleRequestBody(
                        appId = manager.getAppId(),
                        guid = manager.getIdentityToken().getMemberGuid(),
                        articleId = articleId
                    )
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getStockPopularArticle(
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        stockIdList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        filterType: FilterType,
        domain: String,
        url: String
    ): Result<GetStockPopularArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getStockPopularArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetStockPopularArticleBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun putOnBlackList(
        blackChannelId: Long,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.putOnBlackList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = PutOnBlackListRequestBody(
                    blackChannelId = blackChannelId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun spinOffBlackList(
        blackChannelId: Long,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.spinOffBlackList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = SpinOffBlackListRequestBody(
                    blackChannelId = blackChannelId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getBlackList(
        domain: String,
        url: String
    ): Result<GetBlackListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getBlackList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = RequestIds(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getBlockList(
        domain: String,
        url: String
    ): Result<GetBlockListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getBlockList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = RequestIds(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }


    override suspend fun getNotify(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetNotifyResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getNotify(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetNotifyRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    isIncludeClub = isIncludeClub,
                    upperBoundNotifyTime = lowerBoundNotifyTime,
                    notifyTypes = notifyTypes.map { it.value },
                    fetchCount = fetchCount
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getUnreadCount(
        isIncludeClub: Boolean,
        lowerBoundNotifyTime: Long,
        notifyTypes: List<NotificationType>,
        domain: String,
        url: String
    ): Result<GetUnreadCountResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getUnreadCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetUnreadCountRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    isIncludeClub = isIncludeClub,
                    lowerBoundNotifyTime = lowerBoundNotifyTime,
                    notifyTypesList = notifyTypes.map { it.value }
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun setReaded(
        notifyIdAndIsSpecificPair: List<NotifyIdAndIsSpecificPair>,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        runCatching {
            service.setReaded(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = SetReadedRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    notifyIdAndIsSpecificPair = notifyIdAndIsSpecificPair
                )
            ).handleNoContent(gson)
        }
    }

    override suspend fun getSingleArticle(
        articleId: Long,
        articleNeedInfo: ArticleNeedInfo,
        domain: String,
        url: String
    ): Result<GetSingleArticleResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getSingleArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetSingleArticleRequestBody(
                    appId = manager.getAppId(),
                    articleId = articleId,
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getComments(
        articleId: Long,
        upperBoundArticleId: Long,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetCommentsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getComments(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetCommentsRequestBody(
                    appId = manager.getAppId(),
                    articleId = articleId,
                    fetchCount = fetchCount,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    upperBoundArticleId = upperBoundArticleId
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getManagerList(
        needInfo: Int,
        channelId: Long,
        domain: String,
        url: String
    ): Result<GetManagerList> = withContext(dispatcher.io()) {
        runCatching {
            service.getManagerList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetManagerListRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    channelId = channelId,
                    needInfo = needInfo
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getManagerList(
        clubChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        domain: String,
        url: String
    ): Result<GetManagerList> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getManagerList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetManagerListRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    channelId = clubChannelId,
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun isJoinedClub(
        channelId: Long,
        relation: Relation,
        domain: String,
        url: String
    ): Result<HasJoinedClubComplete> = withContext(dispatcher.io()) {
        runCatching {
            service.isJoinClub(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = HasJoinedClubRequestBody(
                    manager.getAppId(),
                    manager.getIdentityToken().getMemberGuid(),
                    channelId,
                    relation.value
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getOtherChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Other>,
        domain: String,
        url: String
    ): Result<ChannelInfo.ChannelBaseInfo> = withContext(dispatcher.io()) {
        runCatching {
            service.getOtherChannelInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = manager.getAppId(),
                    channelId = channelId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getMemberChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        domain: String,
        url: String
    ): Result<ChannelInfo.MemberChannelInfo> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getMemberChannelInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = manager.getAppId(),
                    channelId = channelId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            )
            response.checkResponseBody(gson)
        }
    }

    override suspend fun getClubChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        domain: String,
        url: String
    ): Result<ChannelInfo.ClubChannelInfo> = withContext(dispatcher.io()) {
        runCatching {
            service.getClubChannelInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = manager.getAppId(),
                    channelId = channelId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getRssSignalChannelInfo(
        channelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.RssSignal>,
        domain: String,
        url: String
    ): Result<ChannelInfo.RssSignalChannelInfo> = withContext(dispatcher.io()) {
        runCatching {
            service.getRssSignalChannelInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetChannelInfoRequestBody(
                    appId = manager.getAppId(),
                    channelId = channelId,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    needInfo = needInfo.getCombinedResult()
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun searchChannel(
        channelTypes: ChannelTypes,
        fetchCount: Int,
        keyword: String,
        domain: String,
        url: String
    ): Result<SearchChannelResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.searchChannel(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = SearchChannelRequestBody(
                    appId = manager.getAppId(),
                    channelTypes = channelTypes,
                    fetchCount = fetchCount,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    keyword = keyword
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getFanListExcludeJoinedClub(
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        excludeClubChannelId: Long,
        fetchCount: Int,
        skipCount: Int,
        domain: String,
        url: String
    ): Result<GetFansListExcludeJoinedClubResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getFanListExcludeJoinedClub(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody =
                GetFansListExcludeJoinedClubRequestBody(
                    appId = manager.getAppId(),
                    excludeClubChannelId = excludeClubChannelId,
                    fetchCount = fetchCount,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    needInfo = needInfo.getCombinedResult(),
                    skipCount = skipCount
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun getSimpleChannelInfo(
        channelIds: List<Long>,
        domain: String,
        url: String
    ): Result<GetSimpleChannelInfoResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getSimpleChannelInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetSimpleChannelInfoRequestBody(
                    appId = manager.getAppId(),
                    channelIds = channelIds,
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkResponseBody(gson)
        }
    }

    override suspend fun setEvaluation(
        channelId: Long,
        content: String,
        score: Int,
        domain: String,
        url: String
    ): Result<SuccessResult> = withContext(dispatcher.io()) {
        runCatching {
            service.setEvaluation(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = SetEvaluationRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun checkHasEvaluated(
        channelId: Long,
        domain: String,
        url: String
    ): Result<CheckHasEvaluatedResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.checkHasEvaluated(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = CheckHasEvaluatedRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    channelId = channelId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getEvaluationList(
        channelId: Long,
        fetchCount: Int,
        skipCount: Int,
        sortType: SortType,
        domain: String,
        url: String
    ): Result<GetEvaluationListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getEvaluationList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetEvaluationListRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun getCollectArticleList(
        skipCount: Int,
        fetchCount: Int,
        articleNeedInfo: ArticleNeedInfo,
        isIncludeLimitedAskArticle: Boolean,
        domain: String,
        url: String
    ): Result<GetCollectArticleListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getCollectArticleList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetCollectArticleLIstRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun getMasters(
        masterType: MasterType,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetMastersResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getMasters(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetMastersRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    masterType = masterType.value,
                    fetchCount = fetchCount
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAskLatestArticle(
        baseArticleId: Long,
        fetchCount: Int,
        stockListList: List<String>,
        articleNeedInfo: ArticleNeedInfo,
        domain: String,
        url: String
    ): Result<GetAskLatestArticleResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAskLatestArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetAskLatestArticleRequestBody(
                    appId = manager.getAppId(),
                    articleNeedInfo = articleNeedInfo.getCombinedResult(),
                    baseArticleId = baseArticleId,
                    fetchCount = fetchCount,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    stockIdList = stockListList
                )
            ).checkOceanResponseBody(gson)
        }
    }

    /**
     * 取得多股大師評價分數
     */
    override suspend fun getStockMasterEvaluationList(
        stockIdList: List<String>,
        domain: String,
        url: String
    ): Result<GetStockMasterEvaluationListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockMasterEvaluationList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetStockMasterEvaluationListRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    stockIds = stockIdList
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getStockMasterEvaluation(
        stockId: String,
        domain: String,
        url: String
    ): Result<GetStockMasterEvaluationResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockMasterEvaluation(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetStockMasterEvaluationRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    stockId = stockId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun uploadChannelImage(
        channelId: Long,
        image: File?,
        domain: String,
        url: String
    ): Result<UploadChannelImageResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.apply {
                addFormDataPart("AppId", manager.getAppId().toString())
                addFormDataPart("Guid", manager.getIdentityToken().getMemberGuid())
                addFormDataPart("ChannelId", channelId.toString())
                image?.apply {
                    addFormDataPart("File", this.name, this.asRequestBody())
                }
            }

            val response = service.uploadChannelImage(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = builder.build()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun createClub(
        clubName: String,
        description: String,
        joinMethod: JoinMethod,
        domain: String,
        url: String
    ): Result<CreateClubResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.createClub(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = CreateClubRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        clubChannelId: Long,
        domain: String,
        url: String
    ): Result<DeleteClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.deleteClub(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = DeleteClubRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    clubChannelId = clubChannelId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun leaveClub(
        clubChannelId: Long,
        domain: String,
        url: String
    ): Result<LeaveClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.leaveClub(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = LeaveClubRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    clubChannelId = clubChannelId
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun invite(
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        domain: String,
        url: String
    ): Result<InviteResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.invite(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = InviteRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        clubChannelId: Long,
        message: String,
        domain: String,
        url: String
    ): Result<JoinClubResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.joinClub(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = JoinClubRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        memberChannelId: Long,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Club>,
        relation: Relation,
        domain: String,
        url: String
    ): Result<GetMemberClubsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getMemberClubs(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetMemberClubsRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        fetchCount: Int,
        skipCount: Int,
        needInfo: RecommendClubsNeedInfo,
        domain: String,
        url: String
    ): Result<GetRecommendClubsResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getRecommendClubs(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetRecommendClubsRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        clubChannelId: Long,
        memberChannelIds: List<Long>,
        operation: Operation,
        domain: String,
        url: String
    ): Result<ChangeMemberStatusResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.changeMemberStatus(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = ChangeMemberStatusRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        clubChannelId: Long,
        description: String,
        domain: String,
        url: String
    ): Result<UpdateClubDescriptionResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.updateClubDescription(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = UpdateClubDescriptionRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        clubChannelId: Long,
        memberPosition: MemberPosition,
        fetchCount: Int,
        skipCount: Int,
        needInfo: ChannelNeedInfo<ChannelInfoOption.Member>,
        domain: String,
        url: String
    ): Result<GetMemberStatusListResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getMemberStatusList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = GetMemberStatusListRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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

    override suspend fun getRelevantComments(
        articleIds: List<Long>,
        fetch: Long,
        domain: String,
        url: String
    ): Result<GetRelevantCommentsResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getRelevantComments(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetRelevantCommentsRequest(
                    articleIds = articleIds,
                    fetch = fetch,
                    guid = manager.getIdentityToken().getMemberGuid(),
                    appId = manager.getAppId()
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
        articleNeedInfo: ArticleNeedInfo,
        domain: String,
        url: String
    ): Result<GetTopicArticlesResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getTopicArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetTopicArticlesRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        articleNeedInfo: ArticleNeedInfo,
        domain: String,
        url: String
    ): Result<GetStockAndTopicArticlesResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockAndTopicArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = GetStockAndTopicArticlesRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
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
        isPinned: Boolean,
        domain: String,
        url: String
    ): Result<IsCreateOrUpdateSuccessResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.createOrUpdateAnnouncement(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = CreateOrUpdateAnnouncementRequestBody(
                    articleId = articleId,
                    isPinned = isPinned,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAllAnnouncements(
        clubChannelId: Long,
        domain: String,
        url: String
    ): Result<AnnouncementListResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.readAnnouncement(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = ReadAnnouncementsRequestBody(
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun removeAnnouncements(
        clubChannelId: Long,
        articleId: Long,
        isPinned: Boolean,
        domain: String,
        url: String
    ): Result<IsRemoveAnnouncementSuccessResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.removeAnnouncement(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                requestBody = RemoveAnnouncementRequestBody(
                    articleId = articleId,
                    isPinned = isPinned,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }
}