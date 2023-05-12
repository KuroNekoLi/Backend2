package com.cmoney.backend2.mobileocean.service

import android.text.TextUtils
import com.cmoney.backend2.base.extension.checkISuccess
import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponse
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponse
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponse
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponse
import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.cmoney.backend2.mobileocean.service.api.common.channel.NeedInfo
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleAppendClubParam
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleAppendParam
import com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody.ArticleAppendQuestionParam
import com.cmoney.backend2.mobileocean.service.api.createarticle.responsebody.CreateArticleResponse
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.requestbody.SubmitAdviceParam
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.responsebody.CreateArticleToOceanResponse
import com.cmoney.backend2.mobileocean.service.api.dislikearticle.DisLikeArticleResponse
import com.cmoney.backend2.mobileocean.service.api.followchannel.FollowChannelResponse
import com.cmoney.backend2.mobileocean.service.api.getarticletips.GetArticleTipsResponse
import com.cmoney.backend2.mobileocean.service.api.getattentionchannel.GetAttentionChannelResponse
import com.cmoney.backend2.mobileocean.service.api.getchannelpopulararticles.GetChannelPopularArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getfanschannel.GetFansChannelResponse
import com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles.ChannelCategory
import com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles.GetFollowedChannelArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getforumlatestarticles.GetForumLatestArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getforumpopulararticles.GetForumPopularArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getlatestquestionarticles.GetLatestQuestionArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getmembermasterranking.GetMemberMasterRankingResponse
import com.cmoney.backend2.mobileocean.service.api.getpopularquestionarticles.GetPopularQuestionArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.requestbody.GetPopularStocksParam
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody.PopularStockCollection
import com.cmoney.backend2.mobileocean.service.api.getrepliedarticleIds.RepliedArticleIds
import com.cmoney.backend2.mobileocean.service.api.getreplyarticlelist.GetReplyArticleListResponse
import com.cmoney.backend2.mobileocean.service.api.getsinglearticle.GetSingleArticleResponse
import com.cmoney.backend2.mobileocean.service.api.getstockarticlelist.GetStockArticleListResponse
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.GetStockPictureResponse
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.PictureType
import com.cmoney.backend2.mobileocean.service.api.givearticletip.GiveArticleTipResponse
import com.cmoney.backend2.mobileocean.service.api.istodayaskedstocktendency.IsTodayAskedStockTendencyResponse
import com.cmoney.backend2.mobileocean.service.api.leavechannel.LeaveChannelResponse
import com.cmoney.backend2.mobileocean.service.api.likearticle.LikeArticleResponse
import com.cmoney.backend2.mobileocean.service.api.replyarticle.ReplyArticleResponse
import com.cmoney.backend2.mobileocean.service.api.updatechanneldescription.UpdateChannelIdDescriptionResponse
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MobileOceanWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: MobileOceanService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider,
) : MobileOceanWeb {

    override suspend fun createArticleToOcean(
        submitAdviceParam: SubmitAdviceParam,
        domain: String,
        url: String
    ): Result<CreateArticleToOceanResponse> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.createArticleToOcean(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                content = submitAdviceParam.content,
                osVersion = submitAdviceParam.osVersion,
                appVersion = submitAdviceParam.appVersion,
                deviceName = submitAdviceParam.deviceName,
                device = manager.getPlatform().code
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    override suspend fun createArticle(
        articleContent: String,
        appendQuestionParam: ArticleAppendQuestionParam?,
        appendClubParam: ArticleAppendClubParam?,
        appendParam: ArticleAppendParam?,
        domain: String,
        url: String
    ): Result<CreateArticleResponse> = withContext(dispatcher.io()) {
        runCatching {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.apply {
                addFormDataPart("action", "CreateArticle")
                addFormDataPart("guid", manager.getIdentityToken().getMemberGuid())
                addFormDataPart("appId", manager.getAppId().toString())
                addFormDataPart("Content", articleContent)
                addFormDataPart(
                    "ArticleType", if (appendQuestionParam == null) {
                        ArticleType.GeneralArticle.value
                    } else {
                        ArticleType.Question.value
                    }.toString()
                )
                appendQuestionParam?.askBonus?.apply {
                    addFormDataPart("AskBouns", this.toString())
                }
                appendQuestionParam?.isAnonymous?.apply {
                    addFormDataPart("IsAnonymous", this.toString())
                }
                appendParam?.stockId?.apply {
                    addFormDataPart("StockId", this)
                }
                appendParam?.mentionTag?.joinToString(",") {
                    "${it.stockId}:${it.stockTag.value}"
                }?.apply {
                    addFormDataPart("MentionTags", this)
                }
                appendParam?.stockImage?.apply {
                    addFormDataPart("StockImage", this)
                }
                appendClubParam?.clubChannelId?.apply {
                    addFormDataPart("ClubChannelId", this.toString())
                }
                appendClubParam?.isUseClubToPost?.apply {
                    addFormDataPart("IsUseClubToPost", this.toString())
                }
                appendParam?.videoUrl?.apply {
                    addFormDataPart("videoUrl", this)
                }
                appendParam?.image?.forEach {
                    addFormDataPart("Image", it.name, it.asRequestBody())
                }
            }

            service.createArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = builder.build()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError() // Check enveloped error message (newer)
                .toRealResponse()
                .checkISuccess()  // Check in-body error code (old)
        }
    }

    override suspend fun replyArticle(
        stockId: String,
        articleId: Long,
        content: String,
        isUseClubToReply: Boolean,
        image: File?,
        domain: String,
        url: String
    ): Result<ReplyArticleResponse> = withContext(dispatcher.io()) {
        runCatching {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.apply {
                addFormDataPart("action", "ReplyArticle")
                addFormDataPart("guid", manager.getIdentityToken().getMemberGuid())
                addFormDataPart("appId", manager.getAppId().toString())
                addFormDataPart("StockId", stockId)
                addFormDataPart("ArticleId", articleId.toString())
                addFormDataPart("Content", content)
                addFormDataPart("IsUseClubToReply", isUseClubToReply.toString())

                image?.apply {
                    addFormDataPart("Image", this.name, this.asRequestBody())
                }
            }

            service.replyArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                body = builder.build()
            ).checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    override suspend fun getRepliedArticleIds(
        articleIds: List<Long>,
        domain: String,
        url: String
    ): Result<RepliedArticleIds> = withContext(dispatcher.io()) {
        runCatching {
            val articleIdsString = TextUtils.join(",", articleIds)
            val response = service.getRepliedArticleIds(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleIds = articleIdsString
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
                .toRealResponse()
        }
    }

    override suspend fun getStockArticleList(
        articleId: Long,
        stockId: String,
        fetchSize: Int,
        replyFetchSize: Int,
        isIncludeLimitedAskArticle: Boolean,
        domain: String,
        url: String
    ): Result<GetStockArticleListResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getStockArticleList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId,
                stockId = stockId,
                fetchSize = fetchSize,
                replyFetchSize = replyFetchSize,
                isIncludeLimitedAskArticle = isIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getReplyArticleList(
        articleId: Long,
        domain: String,
        url: String
    ): Result<GetReplyArticleListResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getReplyArticleList(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getForumLatestArticles(
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean,
        domain: String,
        url: String
    ): Result<GetForumLatestArticlesResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getForumLatestArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                baseArticleId = baseArticleId,
                fetchCount = fetchCount,
                IsIncludeLimitedAskArticle = IsIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getForumPopularArticles(
        skipCount: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean,
        domain: String,
        url: String
    ): Result<GetForumPopularArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getForumPopularArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                skipCount = skipCount,
                fetchCount = fetchCount,
                IsIncludeLimitedAskArticle = IsIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getChannelPopularArticles(
        channelIdList: List<Long>,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        domain: String,
        url: String
    ): Result<GetChannelPopularArticlesResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getChannelPopularArticles(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                channelIdList = channelIdList.joinToString(separator = ","),
                skipCount = skipCount,
                fetchCount = fetchCount,
                isIncludeLimitedAskArticle = isIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getSingleArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<GetSingleArticleResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getSingleArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getFollowedChannelArticles(
        channelCategory: ChannelCategory,
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        domain: String,
        url: String
    ): Result<GetFollowedChannelArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getFollowedChannelArticles(
                url = url,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                channelCategory = channelCategory.value,
                baseArticleId = baseArticleId,
                fetchCount = fetchCount,
                isIncludeLimitedAskArticle = isIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getPopularQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetPopularQuestionArticlesResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getPopularQuestionArticles(
                url = url,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                stockIdList = stockIdList,
                fetchCount = fetchCount,
                skipCount = skipCount
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getLatestQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetLatestQuestionArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getLatestQuestionArticles(
                url = url,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                stockIdList = stockIdList,
                fetchCount = fetchCount,
                skipCount = skipCount
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun likeArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<LikeArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.likeArticle(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun giveArticleTip(
        articleId: Long,
        tip: Int,
        domain: String,
        url: String
    ): Result<GiveArticleTipResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.giveArticleTip(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId,
                tip = tip
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun addInterestedInArticleInfo(
        articleId: Long,
        points: Int,
        domain: String,
        url: String
    ): Result<AddInterestedInArticleInfoResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.addInterestedInArticleInfo(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId,
                points = points
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun dislikeArticle(
        articleId: Long,
        domain: String,
        url: String
    ): Result<DisLikeArticleResponse> =
        withContext(dispatcher.io()) {
            runCatching {
                service.dislikeArticle(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    articleId = articleId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun isTodayAskedStockTendency(
        stockId: String,
        domain: String,
        url: String
    ): Result<IsTodayAskedStockTendencyResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.isTodayAskedStockTendency(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    stockId = stockId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun addAskStockTendencyLog(
        stockId: String,
        domain: String,
        url: String
    ): Result<AddAskStockTendencyLogResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.addAskStockTendencyLog(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    stockId = stockId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getAskStockTendencyAmount(
        stockId: String,
        domain: String,
        url: String
    ): Result<AskStockTendencyAmountResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getAskStockTredencyAmount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                stockId = stockId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun followChannel(
        channelId: Long,
        domain: String,
        url: String
    ): Result<FollowChannelResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.followChannel(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                channelId = channelId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun leaveChannel(
        channelId: Long,
        domain: String,
        url: String
    ): Result<LeaveChannelResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.leaveChannel(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                channelId = channelId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun updateChannelDescription(
        description: String,
        domain: String,
        url: String
    ): Result<UpdateChannelIdDescriptionResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.updateChannelDescription(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                description = description
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getPopularStocks(
        param: GetPopularStocksParam,
        domain: String,
        url: String
    ): Result<PopularStockCollection> = withContext(dispatcher.io()) {
        runCatching {
            service.getPopularStocks(
                url = url,
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                fetchCount = param.fetchCount,
                needMoreInfo = param.needMoreInfo,
                authorization = manager.getAccessToken().createAuthorizationBearer()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getArticleTips(
        articleId: Long,
        fetchCount: Int,
        skipCount: Int,
        domain: String,
        url: String
    ): Result<GetArticleTipsResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getArticleTips(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                articleId = articleId,
                fetchCount = fetchCount,
                skipCount = skipCount
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getFansChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetFansChannelResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getFansChannel(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                channelId = channelId,
                needInfo = needInfo.value,
                skipCount = skipCount,
                fetchCount = fetchCount
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAttentionChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int,
        domain: String,
        url: String
    ): Result<GetAttentionChannelResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getAttentionChannel(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                channelId = channelId,
                needInfo = needInfo.value,
                skipCount = skipCount,
                fetchCount = fetchCount
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getStockPicture(
        stockId: String,
        type: PictureType,
        domain: String,
        url: String
    ): Result<GetStockPictureResponse> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getStockPicture(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                stockId = stockId,
                pictureType = type.value
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務9-2. 取得指定會員的達人名次和社群熱度
     */
    override suspend fun getMemberMasterRanking(
        domain: String,
        url: String
    ): Result<GetMemberMasterRankingResponse> = withContext(dispatcher.io()) {
        runCatching {
            service.getMemberMasterRanking(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun activeFollow(domain: String, url: String): Result<ActiveFollowResponse> =
        withContext(dispatcher.io()) {
            runCatching {
                service.activeFollow(
                    url = url,
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    authorization = manager.getAccessToken().createAuthorizationBearer()
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }
}