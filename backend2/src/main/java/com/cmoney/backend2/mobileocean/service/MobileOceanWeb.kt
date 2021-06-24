package com.cmoney.backend2.mobileocean.service

import com.cmoney.backend2.base.model.request.ApiParam
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponse
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponse
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponse
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponse
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
import java.io.File

interface MobileOceanWeb {

    /**
     * 服務1-1-2. 發文到給....一點建議(加上身份識別)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun createArticleToOcean(
        apiParam: MemberApiParam,
        device: Int,
        submitAdviceParam: SubmitAdviceParam
    ): Result<CreateArticleToOceanResponse>

    /**
     * 服務1-1-2. 發文到給....一點建議(加上身份識別)
     */
    suspend fun createArticleToOcean(submitAdviceParam: SubmitAdviceParam): Result<CreateArticleToOceanResponse>

    /**
     * 服務1-2. 發表文章到股票追訊頻道
     */
    @Deprecated("ApiParam no longer required")
    suspend fun createArticle(
        apiParam: MemberApiParam,
        articleContent: String,
        appendQuestionParam: ArticleAppendQuestionParam? = null,
        appendClubParam: ArticleAppendClubParam? = null,
        appendParam: ArticleAppendParam? = null
    ): Result<CreateArticleResponse>

    /**
     * 服務1-2. 發表文章到股票追訊頻道
     */
    suspend fun createArticle(
        articleContent: String,
        appendQuestionParam: ArticleAppendQuestionParam? = null,
        appendClubParam: ArticleAppendClubParam? = null,
        appendParam: ArticleAppendParam? = null
    ): Result<CreateArticleResponse>

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
    @Deprecated("ApiParam no longer required")
    suspend fun replyArticle(
        apiParam: MemberApiParam,
        stockId: String,
        articleId: Long,
        content: String,
        isUseClubToReply: Boolean,
        image: File? = null
    ): Result<ReplyArticleResponse>

    /**
     * 服務1-3. 回應文章到追訊頻道
     *
     * @param stockId
     * @param articleId
     * @param content
     * @param isUseClubToReply 是否以社團名義回文
     * @param image 上傳的圖檔(server限制暫時是4Mb,不縮圖不限長寬)
     */
    suspend fun replyArticle(
        stockId: String,
        articleId: Long,
        content: String,
        isUseClubToReply: Boolean,
        image: File? = null
    ): Result<ReplyArticleResponse>

    /**
     * 服務2-1-2. 取得追訊文章最新一篇回應ID(加上身份識別)
     * @param apiParam
     * @param articleIds
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getRepliedArticleIds(
        apiParam: MemberApiParam,
        articleIds: List<Long>
    ): Result<RepliedArticleIds>

    /**
     * 服務2-1-2. 取得追訊文章最新一篇回應ID(加上身份識別)
     * @param articleIds
     */
    suspend fun getRepliedArticleIds(
        articleIds: List<Long>
    ): Result<RepliedArticleIds>

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
    @Deprecated("ApiParam no longer required")
    suspend fun getStockArticleList(
        apiParam: MemberApiParam,
        articleId: Long,
        stockId: String,
        fetchSize: Int,
        replyFetchSize: Int = 0,
        isIncludeLimitedAskArticle: Boolean = false
    ): Result<GetStockArticleListResponse>

    /**
     * 服務2-2. 取得追訊股票討論區文章
     *
     * @param stockId
     * @param articleId
     * @param fetchSize 要取的筆數
     * @param replyFetchSize 要取得的回應數量 (選填 預設為0)
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    suspend fun getStockArticleList(
        articleId: Long,
        stockId: String,
        fetchSize: Int,
        replyFetchSize: Int = 0,
        isIncludeLimitedAskArticle: Boolean = false
    ): Result<GetStockArticleListResponse>

    /**
     * 服務2-3. 取得個股討論區回應文章列表
     *
     * @param apiParam
     * @param articleId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getReplyArticleList(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<GetReplyArticleListResponse>

    /**
     * 服務2-3. 取得個股討論區回應文章列表
     *
     * @param articleId
     * @return
     */
    suspend fun getReplyArticleList(articleId: Long): Result<GetReplyArticleListResponse>

    /**
     * 服務2-4. 取得最新討論區文章
     *
     * @param apiParam
     * @param baseArticleId 由哪一個ArticleId開始哪起，第一次填0
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getForumLatestArticles(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetForumLatestArticlesResponse>

    /**
     * 服務2-4. 取得最新討論區文章
     *
     * @param baseArticleId 由哪一個ArticleId開始哪起，第一次填0
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    suspend fun getForumLatestArticles(
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetForumLatestArticlesResponse>

    /**
     * 服務2-5. 取得最熱門討論區文章
     *
     * @param apiParam
     * @param skipCount 拿取文章的Index
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getForumPopularArticles(
        apiParam: MemberApiParam,
        skipCount: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetForumPopularArticlesResponse>

    /**
     * 服務2-5. 取得最熱門討論區文章
     *
     * @param skipCount 拿取文章的Index
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    suspend fun getForumPopularArticles(
        skipCount: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetForumPopularArticlesResponse>


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
    @Deprecated("Use Ocean version")
    suspend fun getStockLatestArticles(
        apiParam: ApiParam,
        stockIdList: List<String>,
        filterType: FilterType,
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetStockLatestArticlesResponse>

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
    @Deprecated("Use Ocean version")
    suspend fun getStockLatestArticles(
        stockIdList: List<String>,
        filterType: FilterType,
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetStockLatestArticlesResponse>

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
    @Deprecated("Use Ocean version")
    suspend fun getStockPopularArticles(
        apiParam: ApiParam,
        stockIdList: List<String>,
        filterType: FilterType,
        skipCount: Int,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetStockPopularArticlesResponse>

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
    @Deprecated("Use Ocean version")
    suspend fun getStockPopularArticles(
        stockIdList: List<String>,
        filterType: FilterType,
        skipCount: Int,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false
    ): Result<GetStockPopularArticlesResponse>

    /**
     * 服務2-9. 取得多個頻道的最新文章清單(可訪客)
     *
     * @param channelId 頻道編號清單 EX:  2295354,1652358,....
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料，第一次填 0
     * @param fetchCount 要取得筆數
     */
    @Deprecated("Use Ocean version.")
    suspend fun getChannelLatestArticles(
        apiParam: MemberApiParam,
        channelId: Long,
        baseArticleId: Long,
        fetchCount: Int
    ): Result<GetChannelLatestArticlesResponse>

    /**
     * 服務2-9. 取得多個頻道的最新文章清單(可訪客)
     *
     * @param channelId 頻道編號清單 EX:  2295354,1652358,....
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料，第一次填 0
     * @param fetchCount 要取得筆數
     */
    @Deprecated("Use Ocean version.")
    suspend fun getChannelLatestArticles(
        channelId: Long,
        baseArticleId: Long,
        fetchCount: Int
    ): Result<GetChannelLatestArticlesResponse>

    @Deprecated("ApiParam no longer required")
    suspend fun getChannelPopularArticles(
        apiParam: MemberApiParam,
        channelIdList: List<Long>,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle : Boolean = false
    ): Result<GetChannelPopularArticlesResponse>

    /**
     * 服務2-10. 取得多個頻道的熱門文章清單(可訪客)
     *
     * @param channelIdList 頻道編號清單 EX:  2295354,1652358,....
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取得筆數
     * @return
     */
    suspend fun getChannelPopularArticles(
        channelIdList: List<Long>,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle : Boolean = false
    ): Result<GetChannelPopularArticlesResponse>

    /**
     *  服務2-11. 取得指定文章(可訪客)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getSingleArticle(
        apiParam: ApiParam,
        articleId: Long
    ): Result<GetSingleArticleResponse>

    /**
     *  服務2-11. 取得指定文章(可訪客)
     */
    suspend fun getSingleArticle(
        articleId: Long
    ): Result<GetSingleArticleResponse>

    /**
     * 服務2-12. 取得追蹤頻道的最新文章清單
     *
     * @param apiParam 會員資訊
     * @param channelCategory 頻道分類
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料(第一次填 long.MaxValue = 9223372036854775807)
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章( 預設:false )
     */
    @Deprecated("ApiParam no longer required")

    suspend fun getFollowedChannelArticles(
        apiParam: MemberApiParam,
        channelCategory: ChannelCategory,
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetFollowedChannelArticlesResponse>

    /**
     * 服務2-12. 取得追蹤頻道的最新文章清單
     *
     * @param apiParam 會員資訊
     * @param channelCategory 頻道分類
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料(第一次填 long.MaxValue = 9223372036854775807)
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章( 預設:false )
     */

    suspend fun getFollowedChannelArticles(
        channelCategory: ChannelCategory,
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean
    ): Result<GetFollowedChannelArticlesResponse>

    /**
     * 服務2-13. 取得最熱問答文章清單(可訪客)
     *
     * @param apiParam
     * @param stockIdList
     * @param skipCount
     * @param fetchCount
     * @return
     */
    @Deprecated("ApiParam no longer required")

    suspend fun getPopularQuestionArticles(
        apiParam: MemberApiParam,
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetPopularQuestionArticlesResponse>

    /**
     * 服務2-13. 取得最熱問答文章清單(可訪客)
     *
     * @param stockIdList
     * @param skipCount
     * @param fetchCount
     * @return
     */

    suspend fun getPopularQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetPopularQuestionArticlesResponse>

    /**
     * 服務2-14. 取得最新問答文章清單(可訪客)
     *
     * @param apiParam
     * @param stockIdList
     * @param skipCount
     * @param fetchCount
     * @return
     */
    @Deprecated("ApiParam no longer required")

    suspend fun getLatestQuestionArticles(
        apiParam: MemberApiParam,
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetLatestQuestionArticlesResponse>

    /**
     * 服務2-14. 取得最新問答文章清單(可訪客)
     *
     * @param stockIdList
     * @param skipCount
     * @param fetchCount
     * @return
     */

    suspend fun getLatestQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetLatestQuestionArticlesResponse>

    /**
     *  服務3-1. 按讚
     */
    @Deprecated("ApiParam no longer required")
    suspend fun likeArticle(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<LikeArticleResponse>

    /**
     *  服務3-1. 按讚
     */
    suspend fun likeArticle(articleId: Long): Result<LikeArticleResponse>

    /**
     * 服務3-3. 會員打賞文章
     *
     * @param apiParam
     * @param articleId 被打賞文章Id
     * @param tip 打賞金額
     * @return
     */
    suspend fun giveArticleTip(
        apiParam: MemberApiParam,
        articleId: Long,
        tip: Int
    ): Result<GiveArticleTipResponse>

    suspend fun giveArticleTip(
        articleId: Long,
        tip: Int
    ): Result<GiveArticleTipResponse>

    /**
     * 服務3-4. 我也想知道回答
     *
     * @param apiParam
     * @param articleId 想知道回答的文章Id
     * @param points 支付點數
     * @return
     */
    suspend fun addInterestedInArticleInfo(
        apiParam: MemberApiParam,
        articleId: Long,
        points: Int
    ): Result<AddInterestedInArticleInfoResponse>

    suspend fun addInterestedInArticleInfo(
        articleId: Long,
        points: Int
    ): Result<AddInterestedInArticleInfoResponse>

    /**
     * 服務3-5. 按噓
     *
     * @param apiParam
     * @param articleId 被按噓文章Id
     * @return
     */
    suspend fun dislikeArticle(
        apiParam: MemberApiParam,
        articleId: Long
    ): Result<DisLikeArticleResponse>

    suspend fun dislikeArticle(articleId: Long): Result<DisLikeArticleResponse>

    /**
     * 服務4-1. 詢問今天是否跪求過
     */
    @Deprecated("ApiParam no longer required")
    suspend fun isTodayAskedStockTendency(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<IsTodayAskedStockTendencyResponse>

    /**
     * 服務4-1. 詢問今天是否跪求過
     */
    suspend fun isTodayAskedStockTendency(stockId: String): Result<IsTodayAskedStockTendencyResponse>

    /**
     *   服務4-2. 跪求股票趨勢
     */
    @Deprecated("ApiParam no longer required")
    suspend fun addAskStockTendencyLog(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<AddAskStockTendencyLogResponse>

    /**
     *   服務4-2. 跪求股票趨勢
     */
    suspend fun addAskStockTendencyLog(stockId: String): Result<AddAskStockTendencyLogResponse>

    /**
     *   服務4-3 取得跪求股票趨勢數量
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAskStockTendencyAmount(
        apiParam: MemberApiParam,
        stockId: String
    ): Result<AskStockTendencyAmountResponse>

    /**
     *   服務4-3 取得跪求股票趨勢數量
     */
    suspend fun getAskStockTendencyAmount(stockId: String): Result<AskStockTendencyAmountResponse>

    /**
     * 服務6-1. 追蹤頻道
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun followChannel(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<FollowChannelResponse>

    /**
     * 服務6-1. 追蹤頻道
     *
     * @param channelId
     * @return
     */
    suspend fun followChannel(channelId: Long): Result<FollowChannelResponse>

    /**
     * 服務6-2. 離開頻道(取消追蹤)
     *
     * @param apiParam
     * @param channelId
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun leaveChannel(
        apiParam: MemberApiParam,
        channelId: Long
    ): Result<LeaveChannelResponse>

    /**
     * 服務6-2. 離開頻道(取消追蹤)
     *
     * @param channelId
     * @return
     */
    suspend fun leaveChannel(channelId: Long): Result<LeaveChannelResponse>

    /**
     * 服務6-3. 更新頻道描述
     *
     * @param apiParam
     * @param description
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun updateChannelDescription(
        apiParam: MemberApiParam,
        description: String
    ): Result<UpdateChannelIdDescriptionResponse>

    /**
     * 服務6-3. 更新頻道描述
     *
     * @param description
     * @return
     */
    suspend fun updateChannelDescription(description: String): Result<UpdateChannelIdDescriptionResponse>

    /**
     * 服務7-1. 取得熱門股票(可訪客)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getPopularStocks(
        apiParam: ApiParam,
        param: GetPopularStocksParam
    ): Result<PopularStockCollection>

    /**
     * 服務7-1. 取得熱門股票(可訪客)
     */
    suspend fun getPopularStocks(param: GetPopularStocksParam): Result<PopularStockCollection>

    @Deprecated("ApiParam no longer required")
    suspend fun getArticleTips(
        apiParam: MemberApiParam,
        articleId: Long,
        fetchCount: Int,
        skipCount : Int
    ):Result<GetArticleTipsResponse>

    /**
     * 服務7-3. 取得指定文章的會員打賞清單(可訪客)
     *
     * @param articleId 指定文章Id
     * @param fetchCount 取得數量
     * @param skipCount 跳過數量
     * @return
     */
    suspend fun getArticleTips(
        articleId: Long,
        fetchCount: Int,
        skipCount : Int
    ):Result<GetArticleTipsResponse>

    @Deprecated("ApiParam no longer required")
    suspend fun getFansChannel(
        apiParam: ApiParam,
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetFansChannelResponse>

    /**
     * 服務8-3. 取得指定頻道的粉絲清單(被哪些人追蹤)
     * NeedInfo 參考文件 https://docs.google.com/document/d/1AQRWgjmuVKZ3j69U-uutpMgTcfzk87WEFN75HyqBKH8/edit#heading=h.6bfg5lqmur1v
     */
    suspend fun getFansChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetFansChannelResponse>

    @Deprecated("ApiParam no longer required")
    suspend fun getAttentionChannel(
        apiParam: ApiParam,
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetAttentionChannelResponse>

    /**
     * 服務8-4. 取得指定頻道所關注的頻道清單(追蹤哪些人)
     * NeedInfo 參考文件 https://docs.google.com/document/d/1AQRWgjmuVKZ3j69U-uutpMgTcfzk87WEFN75HyqBKH8/edit#heading=h.6bfg5lqmur1v
     */
    suspend fun getAttentionChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int
    ): Result<GetAttentionChannelResponse>

    @Deprecated("ApiParam no longer required")
    suspend fun getStockPicture(
        apiParam: ApiParam,
        stockId : String,
        type : PictureType
    ) : Result<GetStockPictureResponse>
    /**
     * 服務9-1. 產生股票推圖
     *
     * @param stockId 股票代號
     * @param type 推圖類型
     * @return
     */
    suspend fun getStockPicture(
        stockId : String,
        type : PictureType
    ) : Result<GetStockPictureResponse>

    @Deprecated("ApiParam no longer required")
    suspend fun getMemberMasterRanking(
        apiParam: MemberApiParam
    ) : Result<GetMemberMasterRankingResponse>

    /**
     * 服務9-2. 取得指定會員的達人名次和社群熱度
     *
     * @param apiParam
     * @return
     */
    suspend fun getMemberMasterRanking() : Result<GetMemberMasterRankingResponse>

    @Deprecated("ApiParam no longer required")
    suspend fun activeFollow(
        apiParam: MemberApiParam
    ) : Result<ActiveFollowResponse>


    /**
     * 服務9-3. 啟用追訊，拿50購物金
     *
     * @return
     */
    suspend fun activeFollow() : Result<ActiveFollowResponse>
}