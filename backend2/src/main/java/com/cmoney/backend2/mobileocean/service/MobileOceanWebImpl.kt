package com.cmoney.backend2.mobileocean.service

import android.text.TextUtils
import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.request.ApiParam
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponse
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponse
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponse
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponse
import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.cmoney.backend2.mobileocean.service.api.common.article.FilterType
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
import com.cmoney.backend2.mobileocean.service.api.getchannellatestarticles.GetChannelLatestArticlesResponse
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
import com.cmoney.backend2.mobileocean.service.api.getstocklatestarticles.GetStockLatestArticlesResponse
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.GetStockPictureResponse
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.PictureType
import com.cmoney.backend2.mobileocean.service.api.getstockpopulararticles.GetStockPopularArticlesResponse
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
    private val service: MobileOceanService,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : MobileOceanWeb {

    /**
     * 服務1-1-2. 發文到給....一點建議(加上身份識別)
     */
    override suspend fun createArticleToOcean(
        apiParam: MemberApiParam,
        device: Int,
        submitAdviceParam: SubmitAdviceParam
    ): Result<CreateArticleToOceanResponse> = createArticleToOcean(submitAdviceParam)

    override suspend fun createArticleToOcean(submitAdviceParam: SubmitAdviceParam): Result<CreateArticleToOceanResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.createArticleToOcean(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    content = submitAdviceParam.content,
                    osVersion = submitAdviceParam.osVersion,
                    appVersion = submitAdviceParam.appVersion,
                    deviceName = submitAdviceParam.deviceName,
                    device = setting.platform.code
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    /**
     * 服務1-2. 發表文章到股票追訊頻道
     */
    override suspend fun createArticle(
        apiParam: MemberApiParam,
        articleContent: String,
        appendQuestionParam: ArticleAppendQuestionParam?,
        appendClubParam: ArticleAppendClubParam?,
        appendParam: ArticleAppendParam?
    ): Result<CreateArticleResponse> = createArticle(
        articleContent, appendQuestionParam, appendClubParam, appendParam
    )

    override suspend fun createArticle(
        articleContent: String,
        appendQuestionParam: ArticleAppendQuestionParam?,
        appendClubParam: ArticleAppendClubParam?,
        appendParam: ArticleAppendParam?
    ): Result<CreateArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.apply {
                addFormDataPart("action", "CreateArticle")
                addFormDataPart("guid", setting.identityToken.getMemberGuid())
                addFormDataPart("appId", setting.appId.toString())
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
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = builder.build()
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError() // Check enveloped error message (newer)
                .toRealResponse()
                .checkISuccess()  // Check in-body error code (old)
        }
    }

    /**
     * 服務1-3. 回應文章到追訊頻道
     *
     * @param apiParam
     * @param stockId
     * @param articleId
     * @param content
     * @param isUseClubToReply 是否以社團名義回文
     * @param image 上傳的圖檔(server限制暫時是4Mb,不縮圖不限長寬)
     */
    override suspend fun replyArticle(
        apiParam: MemberApiParam,
        stockId: String,
        articleId: Long,
        content: String,
        isUseClubToReply: Boolean,
        image: File?
    ): Result<ReplyArticleResponse> = replyArticle(
        stockId, articleId, content, isUseClubToReply, image
    )

    override suspend fun replyArticle(
        stockId: String,
        articleId: Long,
        content: String,
        isUseClubToReply: Boolean,
        image: File?
    ): Result<ReplyArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            builder.apply {
                addFormDataPart("action", "ReplyArticle")
                addFormDataPart("guid", setting.identityToken.getMemberGuid())
                addFormDataPart("appId", setting.appId.toString())
                addFormDataPart("StockId", stockId)
                addFormDataPart("ArticleId", articleId.toString())
                addFormDataPart("Content", content)
                addFormDataPart("IsUseClubToReply", isUseClubToReply.toString())

                image?.apply {
                    addFormDataPart("Image", this.name, this.asRequestBody())
                }
            }

            service.replyArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                body = builder.build()
            ).checkIsSuccessful()
                .requireBody()
                .checkISuccess()
        }
    }

    override suspend fun getRepliedArticleIds(
        apiParam: MemberApiParam,
        articleIds: List<Long>
    ): Result<RepliedArticleIds> = getRepliedArticleIds(articleIds)

    override suspend fun getRepliedArticleIds(
        articleIds: List<Long>
    ): Result<RepliedArticleIds> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val articleIdsString = TextUtils.join(",", articleIds)
            val response = service.getRepliedArticleIds(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                articleIds = articleIdsString
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkISuccess()
                .toRealResponse()
        }
    }

    /**
     * 服務2-2. 取得追訊股票討論區文章
     *
     * @param apiParam
     * @param stockId
     * @param articleId
     * @param fetchSize 要取的筆數
     * @param replyFetchSize 要取得的回應數量 (選填 預設為0)
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    override suspend fun getStockArticleList(
        apiParam: MemberApiParam,
        articleId: Long,
        stockId: String,
        fetchSize: Int,
        replyFetchSize: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetStockArticleListResponse> = getStockArticleList(
        articleId,
        stockId,
        fetchSize,
        replyFetchSize,
        isIncludeLimitedAskArticle
    )

    override suspend fun getStockArticleList(
        articleId: Long,
        stockId: String,
        fetchSize: Int,
        replyFetchSize: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetStockArticleListResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getStockArticleList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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

    /**
     * 服務2-3. 取得個股討論區回應文章列表
     *
     * @param apiParam
     * @param articleId
     * @return
     */
    override suspend fun getReplyArticleList(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<GetReplyArticleListResponse> = getReplyArticleList(articleId)


    override suspend fun getReplyArticleList(
        articleId: Long
    ): Result<GetReplyArticleListResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getReplyArticleList(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                articleId = articleId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務2-4. 取得最新討論區文章
     *
     * @param apiParam
     * @param baseArticleId 由哪一個ArticleId開始哪起，第一次填0
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    override suspend fun getForumLatestArticles(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetForumLatestArticlesResponse> = getForumLatestArticles(
        baseArticleId, fetchCount, IsIncludeLimitedAskArticle
    )

    override suspend fun getForumLatestArticles(
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetForumLatestArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getForumLatestArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                baseArticleId = baseArticleId,
                fetchCount = fetchCount,
                IsIncludeLimitedAskArticle = IsIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務2-5. 取得最熱門討論區文章
     *
     * @param apiParam
     * @param skipCount 拿取文章的Index
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    override suspend fun getForumPopularArticles(
        apiParam: MemberApiParam,
        skipCount: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetForumPopularArticlesResponse> = getForumPopularArticles(
        skipCount, fetchCount, IsIncludeLimitedAskArticle
    )

    override suspend fun getForumPopularArticles(
        skipCount: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetForumPopularArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getForumPopularArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                skipCount = skipCount,
                fetchCount = fetchCount,
                IsIncludeLimitedAskArticle = IsIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務2-7. 取得多檔股票的最新文章清單(可訪客)
     *
     * @param apiParam
     * @param stockIdList 股票清單 EX:  2330,1101,....
     * @param filterType  篩選文章性質類型
     * @param baseArticleId 由哪一個ArticleId開始往前拿資料，第一次填 0
     * @param fetchCount 要取得筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    override suspend fun getStockLatestArticles(
        apiParam: ApiParam,
        stockIdList: List<String>,
        filterType: FilterType,
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetStockLatestArticlesResponse> = getStockLatestArticles(
        stockIdList, filterType, baseArticleId, fetchCount, IsIncludeLimitedAskArticle
    )

    override suspend fun getStockLatestArticles(
        stockIdList: List<String>,
        filterType: FilterType,
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetStockLatestArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getStockLatestArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                stockIdList = stockIdList,
                filterType = filterType.value,
                baseArticleId = baseArticleId,
                fetchCount = fetchCount,
                IsIncludeLimitedAskArticle = IsIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務2-8 取得多檔股票的熱門文章清單(可訪客)
     *
     * @param apiParam
     * @param stockIdList 股票清單 EX:  2330,1101,....
     * @param filterType  篩選文章性質類型
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取得筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    override suspend fun getStockPopularArticles(
        apiParam: ApiParam,
        stockIdList: List<String>,
        filterType: FilterType,
        skipCount: Int,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetStockPopularArticlesResponse> = getStockPopularArticles(
        stockIdList, filterType, skipCount, fetchCount, IsIncludeLimitedAskArticle
    )

    override suspend fun getStockPopularArticles(
        stockIdList: List<String>,
        filterType: FilterType,
        skipCount: Int,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean
    ): Result<GetStockPopularArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getStockPopularArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                stockIdList = stockIdList,
                filterType = filterType.value,
                skipCount = skipCount,
                fetchCount = fetchCount,
                IsIncludeLimitedAskArticle = IsIncludeLimitedAskArticle
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }


    override suspend fun getChannelLatestArticles(
        apiParam: MemberApiParam,
        channelId: Long,
        baseArticleId: Long,
        fetchCount: Int
    ): Result<GetChannelLatestArticlesResponse> =
        getChannelLatestArticles(channelId, baseArticleId, fetchCount)

    override suspend fun getChannelLatestArticles(
        channelId: Long,
        baseArticleId: Long,
        fetchCount: Int
    ): Result<GetChannelLatestArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getChannelLatestArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                channelIdList = channelId.toString(),
                baseArticleId = baseArticleId,
                fetchCount = fetchCount,
                isIncludeLimitedAskArticle = false
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getChannelPopularArticles(
        apiParam: MemberApiParam,
        channelIdList: List<Long>,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetChannelPopularArticlesResponse> = getChannelPopularArticles(
        channelIdList, skipCount, fetchCount, isIncludeLimitedAskArticle
    )

    /**
     * 服務2-10. 取得多個頻道的熱門文章清單(可訪客)
     *
     * @param channelIdList
     * @param skipCount
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @return
     */
    override suspend fun getChannelPopularArticles(
        channelIdList: List<Long>,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetChannelPopularArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getChannelPopularArticles(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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
        apiParam: ApiParam,
        articleId: Long
    ): Result<GetSingleArticleResponse> = getSingleArticle(articleId)

    override suspend fun getSingleArticle(articleId: Long): Result<GetSingleArticleResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getSingleArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    articleId = articleId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務2-12. 取得追蹤頻道的最新文章清單
     *
     * @param channelCategory 頻道分類
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料(第一次填 long.MaxValue = 9223372036854775807)
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章( 預設:false )
     */
    override suspend fun getFollowedChannelArticles(
        apiParam: MemberApiParam,
        channelCategory: ChannelCategory,
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetFollowedChannelArticlesResponse> = getFollowedChannelArticles(
        channelCategory,
        baseArticleId,
        fetchCount,
        isIncludeLimitedAskArticle
    )

    override suspend fun getFollowedChannelArticles(
        channelCategory: ChannelCategory,
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetFollowedChannelArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getFollowedChannelArticles(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                authorization = setting.accessToken.createAuthorizationBearer(),
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

    /**
     * 服務2-13. 取得最熱問答文章清單(可訪客)
     *
     * @param apiParam
     * @param stockIdList 股票清單，填空字串，表示拿所有問答  EX:  2330,1101,...
     * @param skipCount 略過的筆數
     * @param fetchCount 要取得筆數
     * @return
     */
    override suspend fun getPopularQuestionArticles(
        apiParam: MemberApiParam,
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetPopularQuestionArticlesResponse> = getPopularQuestionArticles(
        stockIdList, skipCount, fetchCount
    )

    override suspend fun getPopularQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetPopularQuestionArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getPopularQuestionArticles(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                authorization = setting.accessToken.createAuthorizationBearer(),
                stockIdList = stockIdList,
                fetchCount = fetchCount,
                skipCount = skipCount
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     *  服務2-14. 取得最新問答文章清單(可訪客)
     *
     * @param apiParam
     * @param stockIdList
     * @param skipCount
     * @param fetchCount
     * @return
     */
    override suspend fun getLatestQuestionArticles(
        apiParam: MemberApiParam,
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetLatestQuestionArticlesResponse> = getLatestQuestionArticles(
        stockIdList, skipCount, fetchCount
    )

    override suspend fun getLatestQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetLatestQuestionArticlesResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getLatestQuestionArticles(
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                authorization = setting.accessToken.createAuthorizationBearer(),
                stockIdList = stockIdList,
                fetchCount = fetchCount,
                skipCount = skipCount
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     *  服務3-1. 按讚
     */
    override suspend fun likeArticle(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<LikeArticleResponse> = likeArticle(articleId)

    override suspend fun likeArticle(
        articleId: Long
    ): Result<LikeArticleResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.likeArticle(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                articleId = articleId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務3-3. 會員打賞文章
     *
     * @param apiParam
     * @param articleId 被打賞文章Id
     * @param tip 打賞金額
     * @return
     */
    override suspend fun giveArticleTip(
        apiParam: MemberApiParam,
        articleId: Long,
        tip: Int
    ): Result<GiveArticleTipResponse> = giveArticleTip(articleId, tip)

    override suspend fun giveArticleTip(articleId: Long, tip: Int): Result<GiveArticleTipResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.giveArticleTip(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    articleId = articleId,
                    tip = tip
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務3-4. 我也想知道回答
     *
     * @param apiParam
     * @param articleId 想知道回答的文章Id
     * @param points 支付點數
     * @return
     */
    override suspend fun addInterestedInArticleInfo(
        apiParam: MemberApiParam,
        articleId: Long,
        points: Int
    ): Result<AddInterestedInArticleInfoResponse> = addInterestedInArticleInfo(articleId, points)

    override suspend fun addInterestedInArticleInfo(
        articleId: Long,
        points: Int
    ): Result<AddInterestedInArticleInfoResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.addInterestedInArticleInfo(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                articleId = articleId,
                points = points
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務3-5. 按噓
     *
     * @param apiParam
     * @param articleId 被按噓文章Id
     * @return
     */
    override suspend fun dislikeArticle(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<DisLikeArticleResponse> = dislikeArticle(articleId)

    override suspend fun dislikeArticle(articleId: Long): Result<DisLikeArticleResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.dislikeArticle(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    articleId = articleId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務4-1. 詢問今天是否跪求過
     */
    override suspend fun isTodayAskedStockTendency(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<IsTodayAskedStockTendencyResponse> = isTodayAskedStockTendency(stockId)

    override suspend fun isTodayAskedStockTendency(stockId: String): Result<IsTodayAskedStockTendencyResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.isTodayAskedStockTendency(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    stockId = stockId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     *   服務4-2. 跪求股票趨勢
     */
    override suspend fun addAskStockTendencyLog(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<AddAskStockTendencyLogResponse> = addAskStockTendencyLog(stockId)

    override suspend fun addAskStockTendencyLog(stockId: String): Result<AddAskStockTendencyLogResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.addAskStockTendencyLog(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    stockId = stockId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     *   服務4-3 取得跪求股票趨勢數量
     */
    override suspend fun getAskStockTendencyAmount(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<AskStockTendencyAmountResponse> = getAskStockTendencyAmount(stockId)

    override suspend fun getAskStockTendencyAmount(
        stockId: String
    ): Result<AskStockTendencyAmountResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAskStockTredencyAmount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                stockId = stockId
            ).checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    /**
     * 服務6-1. 追蹤頻道
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    override suspend fun followChannel(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<FollowChannelResponse> = followChannel(channelId)

    override suspend fun followChannel(channelId: Long): Result<FollowChannelResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.followChannel(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = channelId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務6-2. 離開頻道(取消追蹤)
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    override suspend fun leaveChannel(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<LeaveChannelResponse> = leaveChannel(channelId)

    override suspend fun leaveChannel(channelId: Long): Result<LeaveChannelResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.leaveChannel(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    channelId = channelId
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務6-3. 更新頻道描述
     *
     * @param apiParam
     * @param description
     * @return
     */
    override suspend fun updateChannelDescription(
        apiParam: MemberApiParam,
        description: String
    ): Result<UpdateChannelIdDescriptionResponse> = updateChannelDescription(description)

    override suspend fun updateChannelDescription(description: String): Result<UpdateChannelIdDescriptionResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.updateChannelDescription(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    description = description
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務7-1. 取得熱門股票(可訪客)
     */
    override suspend fun getPopularStocks(
        apiParam: ApiParam,
        param: GetPopularStocksParam
    ): Result<PopularStockCollection> = getPopularStocks(param)

    override suspend fun getPopularStocks(param: GetPopularStocksParam): Result<PopularStockCollection> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getPopularStocks(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    fetchCount = param.fetchCount,
                    needMoreInfo = param.needMoreInfo,
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getArticleTips(
        apiParam: MemberApiParam,
        articleId: Long,
        fetchCount: Int,
        skipCount: Int
    ): Result<GetArticleTipsResponse> = getArticleTips(articleId, fetchCount, skipCount)

    /**
     * 服務7-3. 取得指定文章的會員打賞清單(可訪客)
     *
     * @param apiParam
     * @param articleId
     * @param fetchCount
     * @param skipCount
     * @return
     */
    override suspend fun getArticleTips(
        articleId: Long,
        fetchCount: Int,
        skipCount: Int
    ): Result<GetArticleTipsResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getArticleTips(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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
        apiParam: ApiParam,
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetFansChannelResponse> = getFansChannel(
        channelId, needInfo, skipCount, fetchCount
    )

    override suspend fun getFansChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetFansChannelResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getFansChannel(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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
        apiParam: ApiParam,
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetAttentionChannelResponse> =
        getAttentionChannel(channelId, needInfo, skipCount, fetchCount)

    override suspend fun getAttentionChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetAttentionChannelResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            service.getAttentionChannel(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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

    /**
     * 服務9-1. 產生股票推圖
     *
     * @param apiParam
     * @param stockId
     * @param type
     * @return
     */
    override suspend fun getStockPicture(
        apiParam: ApiParam,
        stockId: String,
        type: PictureType
    ): Result<GetStockPictureResponse> = getStockPicture(stockId, type)

    override suspend fun getStockPicture(
        stockId: String,
        type: PictureType
    ): Result<GetStockPictureResponse> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getStockPicture(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                stockId = stockId,
                pictureType = type.value
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getMemberMasterRanking(
        apiParam: MemberApiParam
    ): Result<GetMemberMasterRankingResponse> = getMemberMasterRanking()

    /**
     * 服務9-2. 取得指定會員的達人名次和社群熱度
     */
    override suspend fun getMemberMasterRanking(): Result<GetMemberMasterRankingResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.getMemberMasterRanking(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun activeFollow(
        apiParam: MemberApiParam
    ): Result<ActiveFollowResponse> = activeFollow()

    override suspend fun activeFollow(): Result<ActiveFollowResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                service.activeFollow(
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    authorization = setting.accessToken.createAuthorizationBearer()
                ).checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }
}