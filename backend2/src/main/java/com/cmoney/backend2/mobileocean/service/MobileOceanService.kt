package com.cmoney.backend2.mobileocean.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponseWithError
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponseWithError
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponseWithError
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponseWithError
import com.cmoney.backend2.mobileocean.service.api.createarticle.responsebody.CreateArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.createarticletoocean.responsebody.CreateArticleToOceanResponse
import com.cmoney.backend2.mobileocean.service.api.dislikearticle.DisLikeArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.followchannel.FollowChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getarticletips.GetArticleTipsResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getattentionchannel.GetAttentionChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getchannellatestarticles.GetChannelLatestArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getchannelpopulararticles.GetChannelPopularArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getfanschannel.GetFansChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getfollowedchannelarticles.GetFollowedChannelArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getforumlatestarticles.GetForumLatestArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getforumpopulararticles.GetForumPopularArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getlatestquestionarticles.GetLatestQuestionArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getmembermasterranking.GetMemberMasterRankingResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getpopularquestionarticles.GetPopularQuestionArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody.PopularStockCollectionWithError
import com.cmoney.backend2.mobileocean.service.api.getrepliedarticleIds.RepliedArticleIdsWithError
import com.cmoney.backend2.mobileocean.service.api.getreplyarticlelist.GetReplyArticleListResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getsinglearticle.GetSingleArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockarticlelist.GetStockArticleListResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstocklatestarticles.GetStockLatestArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.GetStockPictureResponseWithError
import com.cmoney.backend2.mobileocean.service.api.getstockpopulararticles.GetStockPopularArticlesResponseWithError
import com.cmoney.backend2.mobileocean.service.api.givearticletip.GiveArticleTipResponseWithError
import com.cmoney.backend2.mobileocean.service.api.istodayaskedstocktendency.IsTodayAskedStockTendencyResponseWithError
import com.cmoney.backend2.mobileocean.service.api.leavechannel.LeaveChannelResponseWithError
import com.cmoney.backend2.mobileocean.service.api.likearticle.LikeArticleResponseWithError
import com.cmoney.backend2.mobileocean.service.api.replyarticle.ReplyArticleResponse
import com.cmoney.backend2.mobileocean.service.api.updatechanneldescription.UpdateChannelIdDescriptionResponseWithError
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * MobileService-追訊
 */
interface MobileOceanService {

    /**
     * 服務1-1-2. 發文到給....一點建議(加上身份識別)
     */
    @RecordApi(cmoneyAction = "createarticletoocean")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun createArticleToOcean(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "createarticletoocean",
        @Field("Device") device: Int,
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("Content") content: String,
        @Field("OsVersion") osVersion: String,
        @Field("AppVersion") appVersion: String,
        @Field("DeviceName") deviceName: String
    ): Response<CreateArticleToOceanResponse>

    /**
     * 服務1-2. 發表文章到股票追訊頻道
     */
    @RecordApi
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun createArticle(
        @Header("Authorization") authorization: String,
        @Body body: MultipartBody
    ): Response<CreateArticleResponseWithError>

    /**
     * 服務1-3. 回應文章到追訊頻道
     *
     */
    @RecordApi
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun replyArticle(
        @Header("Authorization") authorization: String,
        @Body body: MultipartBody
    ): Response<ReplyArticleResponse>

    /**
     * 服務2-1-2. 取得追訊文章最新一篇回應ID(加上身份識別)
     */
    @RecordApi(cmoneyAction = "getrepliedarticleids")
    @GET("MobileService/ashx/MobileCode.ashx")
    suspend fun getRepliedArticleIds(
        @Header("Authorization") authorization: String,
        @Query("Action") action: String = "getrepliedarticleids",
        @Query("AppId") appId: Int,
        @Query("Guid") guid: String,
        @Query("ArticleIds") articleIds: String
    ): Response<RepliedArticleIdsWithError>

    /**
     * 服務2-2. 取得追訊股票討論區文章
     *
     * @param action
     * @param appId
     * @param guid
     * @param stockId
     * @param articleId 0為取得所有文章，取得比指定文章編號早的文章
     * @param fetchSize 要取的筆數
     * @param replyFetchSize 要取得的回應數量 (選填 預設為0)
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @RecordApi(cmoneyAction = "getstockarticlelist")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getStockArticleList(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getstockarticlelist",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("stockId") stockId: String,
        @Field("articleId") articleId: Long,
        @Field("fetchsize") fetchSize: Int,
        @Field("ReplyFetchSize") replyFetchSize: Int = 0,
        @Field("IsIncludeLimitedAskArticle") isIncludeLimitedAskArticle: Boolean = false
    ): Response<GetStockArticleListResponseWithError>

    /**
     * 服務2-3. 取得個股討論區回應文章列表
     *
     * @param action
     * @param appId
     * @param guid
     * @param articleId
     * @return
     */
    @RecordApi(cmoneyAction = "getreplyarticlelist")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getReplyArticleList(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getreplyarticlelist",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long
    ): Response<GetReplyArticleListResponseWithError>

    /**
     * 服務2-4. 取得最新討論區文章
     *
     * @param action
     * @param appId
     * @param guid
     * @param baseArticleId 由哪一個ArticleId開始哪起，第一次填0
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @RecordApi(cmoneyAction = "getforumlatestarticles")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getForumLatestArticles(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getforumlatestarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("baseArticleId") baseArticleId: Long,
        @Field("fetchCount") fetchCount: Int,
        @Field("isIncludeLimitedAskArticle") IsIncludeLimitedAskArticle: Boolean = false
    ): Response<GetForumLatestArticlesResponseWithError>


    /**
     * 服務2-5. 取得最熱門討論區文章
     *
     * @param action
     * @param appId
     * @param guid
     * @param skipCount 拿取文章的Index
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @RecordApi(cmoneyAction = "getforumpopulararticles")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getForumPopularArticles(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getforumpopulararticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("skipCount") skipCount: Long,
        @Field("fetchCount") fetchCount: Int,
        @Field("isIncludeLimitedAskArticle") IsIncludeLimitedAskArticle: Boolean = false
    ): Response<GetForumPopularArticlesResponseWithError>

    /**
     * 服務2-7. 取得多檔股票的最新文章清單(可訪客)
     *
     * @param action
     * @param appId
     * @param guid guid ( 訪客請填空字串 )
     * @param stockIdList 股票清單 EX:  2330,1101,....
     * @param filterType 篩選文章性質類型
     * @param baseArticleId 由哪一個ArticleId開始往前拿資料，第一次填 0
     * @param fetchCount 要取得筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @RecordApi(cmoneyAction = "getstocklatestarticles")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    @Deprecated("Use Ocean version")
    suspend fun getStockLatestArticles(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getstocklatestarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockIdList") stockIdList: List<String>,
        @Field("FilterType") filterType: Int,
        @Field("baseArticleId") baseArticleId: Long,
        @Field("fetchCount") fetchCount: Int,
        @Field("isIncludeLimitedAskArticle") IsIncludeLimitedAskArticle: Boolean = false
    ): Response<GetStockLatestArticlesResponseWithError>

    /**
     * 服務2-8. 取得多檔股票的熱門文章清單(可訪客)
     *
     * @param action
     * @param appId
     * @param guid guid ( 訪客請填空字串 )
     * @param stockIdList 股票清單 EX:  2330,1101,....
     * @param filterType 篩選文章性質類型
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取得筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @return
     */
    @RecordApi(cmoneyAction = "getstockpopulararticles")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    @Deprecated("Use Ocean version")
    suspend fun getStockPopularArticles(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getstockpopulararticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockIdList") stockIdList: List<String>,
        @Field("FilterType") filterType: Int,
        @Field("skipCount") skipCount: Int,
        @Field("fetchCount") fetchCount: Int,
        @Field("isIncludeLimitedAskArticle") IsIncludeLimitedAskArticle: Boolean = false
    ): Response<GetStockPopularArticlesResponseWithError>

    /**
     * 服務2-9. 取得多個頻道的最新文章清單(可訪客)
     *
     * @param channelIdList 頻道編號清單 EX:  2295354,1652358,....
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料，第一次填 0
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     */
    @RecordApi(cmoneyAction = "getchannellatestarticles")
    @POST("MobileService/ashx/MobileCode.ashx")
    @FormUrlEncoded
    @Deprecated("Use Ocean version")
    suspend fun getChannelLatestArticles(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getchannellatestarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("ChannelIdList") channelIdList: String,
        @Field("BaseArticleId") baseArticleId: Long,
        @Field("FetchCount") fetchCount: Int,
        @Field("IsIncludeLimitedAskArticle") isIncludeLimitedAskArticle: Boolean = false
    ): Response<GetChannelLatestArticlesResponseWithError>

    /**
     * 服務2-10. 取得多個頻道的熱門文章清單(可訪客)
     *
     * @param action
     * @param appId
     * @param guid
     * @param channelIdList
     * @param skipCount
     * @param fetchCount
     * @param isIncludeLimitedAskArticle
     * @return
     */
    @RecordApi(cmoneyAction = "getchannelpopulararticles")
    @POST("MobileService/ashx/MobileCode.ashx")
    @FormUrlEncoded
    suspend fun getChannelPopularArticles(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getchannelpopulararticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("ChannelIdList") channelIdList: String,
        @Field("SkipCount") skipCount: Int,
        @Field("FetchCount") fetchCount: Int,
        @Field("IsIncludeLimitedAskArticle") isIncludeLimitedAskArticle: Boolean = false
    ): Response<GetChannelPopularArticlesResponseWithError>

    /**
     *  服務2-11. 取得指定文章(可訪客)
     */
    @RecordApi(cmoneyAction = "getsinglearticle")
    @POST("MobileService/ashx/MobileCode.ashx")
    @FormUrlEncoded
    suspend fun getSingleArticle(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getsinglearticle",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("ArticleId") articleId: Long
    ): Response<GetSingleArticleResponseWithError>

    /**
     * 服務2-12. 取得追蹤頻道的最新文章清單
     *
     * @param action
     * @param appId
     * @param guid
     * @param channelCategory 頻道分類
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料(第一次填 long.MaxValue = 9223372036854775807)
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章( 預設:false )
     */
    @RecordApi(cmoneyAction = "getfollowedchannelarticles")
    @POST("MobileService/ashx/MobileCode.ashx")
    @FormUrlEncoded
    suspend fun getFollowedChannelArticles(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getfollowedchannelarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("Category") channelCategory: Int,
        @Field("BaseArticleId") baseArticleId: Long,
        @Field("FetchCount") fetchCount: Int,
        @Field("IsIncludeLimitedAskArticle") isIncludeLimitedAskArticle: Boolean
    ) : Response<GetFollowedChannelArticlesResponseWithError>

    /**
     * 服務2-13. 取得最熱問答文章清單(可訪客)
     *
     * @param action
     * @param appId
     * @param guid
     * @param stockIdList 股票清單，填空字串，表示拿所有問答
     * @param skipCount 略過的筆數
     * @param fetchCount 要取得筆數
     * @return
     */
    @RecordApi(cmoneyAction = "getpopularquestionarticles")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getPopularQuestionArticles(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getpopularquestionarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockIdList") stockIdList: List<String>,
        @Field("skipCount") skipCount: Int,
        @Field("fetchCount") fetchCount: Int
    ): Response<GetPopularQuestionArticlesResponseWithError>

    /**
     * 服務2-14. 取得最新問答文章清單(可訪客)
     *
     * @param action
     * @param appId
     * @param guid
     * @param stockIdList 股票清單，填空字串，表示拿所有問答
     * @param skipCount 略過的筆數
     * @param fetchCount 要取得筆數
     * @return
     */
    @RecordApi(cmoneyAction = "getlatestquestionarticles")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getLatestQuestionArticles(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getlatestquestionarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockIdList") stockIdList: List<String>,
        @Field("skipCount") skipCount: Int,
        @Field("fetchCount") fetchCount: Int
    ): Response<GetLatestQuestionArticlesResponseWithError>

    /**
     *  服務3-1. 按讚
     */
    @RecordApi(cmoneyAction = "likearticle")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun likeArticle(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "likearticle",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long
    ): Response<LikeArticleResponseWithError>

    /**
     *  服務3-3. 會員打賞文章
     */
    @RecordApi(cmoneyAction = "givearticletip")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun giveArticleTip(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "givearticletip",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long,
        @Field("Tip") tip: Int
    ): Response<GiveArticleTipResponseWithError>

    /**
     *  服務3-4. 我也想知道回答
     */
    @RecordApi(cmoneyAction = "addinterestedinarticleinfo")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun addInterestedInArticleInfo(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "addinterestedinarticleinfo",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long,
        @Field("Points") points: Int
    ): Response<AddInterestedInArticleInfoResponseWithError>

    /**
     *  服務3-5. 按噓
     */
    @RecordApi(cmoneyAction = "dislikearticle")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun dislikeArticle(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "dislikearticle",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long
    ): Response<DisLikeArticleResponseWithError>

    /**
     * 服務4-1. 詢問今天是否跪求過
     */
    @RecordApi(cmoneyAction = "istodayaskedstocktendency")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun isTodayAskedStockTendency(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "istodayaskedstocktendency",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String
    ): Response<IsTodayAskedStockTendencyResponseWithError>

    /**
     *  服務4-2. 跪求股票趨勢
     */
    @RecordApi(cmoneyAction = "addaskstocktendnecylog")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun addAskStockTendencyLog(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "addaskstocktendnecylog",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String
    ): Response<AddAskStockTendencyLogResponseWithError>

    /**
     *  服務4-3 取得跪求股票趨勢數量
     */
    @RecordApi(cmoneyAction = "getaskstocktendencyamount")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getAskStockTredencyAmount(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getaskstocktendencyamount",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String
    ): Response<AskStockTendencyAmountResponseWithError>

    /**
     * 服務6-1. 追蹤頻道
     *
     * @return
     */
    @RecordApi(cmoneyAction = "followchannel")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun followChannel(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "followchannel",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("channelid") channelId: Long
    ): Response<FollowChannelResponseWithError>

    /**
     * 服務6-2. 離開頻道(取消追蹤)
     *
     */
    @RecordApi(cmoneyAction = "leavechannel")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun leaveChannel(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "leavechannel",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("channelid") channelId: Long
    ): Response<LeaveChannelResponseWithError>

    /**
     * 服務6-3. 更新頻道描述
     *
     */
    @RecordApi(cmoneyAction = "updatechanneldescription")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun updateChannelDescription(
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "updatechanneldescription",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("description") description: String
    ): Response<UpdateChannelIdDescriptionResponseWithError>

    /**
     * 服務7-1. 取得熱門股票(可訪客)
     */
    @RecordApi(cmoneyAction = "getpopularstocks")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getPopularStocks(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getpopularstocks",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String? = null,
        @Field("FetchCount") fetchCount: Int,
        /**
         * 取得更多資訊，傳入 1 .取得最熱或最新文章
         */
        @Field("NeedMoreInfo") needMoreInfo: Int
    ): Response<PopularStockCollectionWithError>

    /**
     * 服務7-3. 取得指定文章的會員打賞清單(可訪客)
     *
     * @param action
     * @param appId
     * @param guid
     * @param articleId 指定文章Id
     * @param fetchCount 取得數量
     * @param skipCount 跳過數量
     * @return
     */
    @RecordApi(cmoneyAction = "getarticletips")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getArticleTips(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getarticletips",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("ArticleId") articleId: Long,
        @Field("FetchCount") fetchCount: Int,
        @Field("SkipCount") skipCount : Int
    ): Response<GetArticleTipsResponseWithError>

    /**
     * 服務8-3. 取得指定頻道的粉絲清單(被哪些人追蹤)
     * NeedInfo 參考文件 https://docs.google.com/document/d/1AQRWgjmuVKZ3j69U-uutpMgTcfzk87WEFN75HyqBKH8/edit#heading=h.6bfg5lqmur1v
     */
    @RecordApi(cmoneyAction = "getfanschannel")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getFansChannel(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getfanschannel",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String? = null,
        @Field("ChannelId") channelId: Long,
        @Field("NeedInfo") needInfo: Int,
        @Field("SkipCount") skipCount: Int,
        @Field("FetchCount") fetchCount: Int
    ): Response<GetFansChannelResponseWithError>

    /**
     * 服務8-4. 取得指定頻道所關注的頻道清單(追蹤哪些人)
     * NeedInfo 參考文件 https://docs.google.com/document/d/1AQRWgjmuVKZ3j69U-uutpMgTcfzk87WEFN75HyqBKH8/edit#heading=h.6bfg5lqmur1v
     */
    @RecordApi(cmoneyAction = "getattentionchannel")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getAttentionChannel(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getattentionchannel",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String? = null,
        @Field("ChannelId") channelId: Long,
        @Field("NeedInfo") needInfo: Int,
        @Field("SkipCount") skipCount: Int,
        @Field("FetchCount") fetchCount: Int
    ): Response<GetAttentionChannelResponseWithError>

    /**
     * 服務9-1. 產生股票推圖
     *
     * @param action
     * @param appId
     * @param guid
     * @param stockId
     * @param pictureType
     * @return
     */
    @RecordApi(cmoneyAction = "getstockpicture")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getStockPicture(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getstockpicture",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String,
        @Field("PictureType") pictureType : Int
    ): Response<GetStockPictureResponseWithError>

    /**
     * 服務9-3. 啟用追訊，拿50購物金
     *
     * @param action
     * @param appId
     * @param guid
     * @return
     */
    @RecordApi(cmoneyAction = "getmembermasterranking")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun getMemberMasterRanking(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getmembermasterranking",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetMemberMasterRankingResponseWithError>

    /**
     * 服務9-3. 啟用追訊，拿50購物金
     *
     * @param action
     * @param appId
     * @param guid
     * @return
     */
    @RecordApi(cmoneyAction = "activiefollow")
    @FormUrlEncoded
    @POST("MobileService/ashx/MobileCode.ashx")
    suspend fun activeFollow(
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "activiefollow",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<ActiveFollowResponseWithError>
}
