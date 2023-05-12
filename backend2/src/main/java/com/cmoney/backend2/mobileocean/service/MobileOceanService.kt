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
import com.cmoney.backend2.mobileocean.service.api.getstockpicture.GetStockPictureResponseWithError
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
     *
     * @param device ios為1，android為2
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param content 文章內容
     * @param osVersion 裝置OS的版本
     * @param appVersion 裝置App應用程式的版本
     * @param deviceName 裝置名稱
     *
     */
    @RecordApi(cmoneyAction = "createarticletoocean")
    @FormUrlEncoded
    @POST
    suspend fun createArticleToOcean(
        @Url url: String,
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
    @POST
    suspend fun createArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: MultipartBody
    ): Response<CreateArticleResponseWithError>

    /**
     * 服務1-3. 回應文章到追訊頻道
     *
     */
    @RecordApi
    @POST
    suspend fun replyArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: MultipartBody
    ): Response<ReplyArticleResponse>

    /**
     * 服務2-1-2. 取得追訊文章最新一篇回應ID(加上身份識別)
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleIds 文章編號(逗號區隔)
     *
     */
    @RecordApi(cmoneyAction = "getrepliedarticleids")
    @GET
    suspend fun getRepliedArticleIds(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Action") action: String = "getrepliedarticleids",
        @Query("AppId") appId: Int,
        @Query("Guid") guid: String,
        @Query("ArticleIds") articleIds: String
    ): Response<RepliedArticleIdsWithError>

    /**
     * 服務2-2. 取得追訊股票討論區文章
     *
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockId 股票編號
     * @param articleId 0為取得所有文章，取得比指定文章編號早的文章
     * @param fetchSize 要取的筆數
     * @param replyFetchSize 要取得的回應數量 (選填 預設為0)
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     *
     */
    @RecordApi(cmoneyAction = "getstockarticlelist")
    @FormUrlEncoded
    @POST
    suspend fun getStockArticleList(
        @Url url: String,
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
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 文章ID
     *
     */
    @RecordApi(cmoneyAction = "getreplyarticlelist")
    @FormUrlEncoded
    @POST
    suspend fun getReplyArticleList(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getreplyarticlelist",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long
    ): Response<GetReplyArticleListResponseWithError>

    /**
     * 服務2-4. 取得最新討論區文章
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param baseArticleId 由哪一個ArticleId開始哪起，第一次填0
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     *
     */
    @RecordApi(cmoneyAction = "getforumlatestarticles")
    @FormUrlEncoded
    @POST
    suspend fun getForumLatestArticles(
        @Url url: String,
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
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param skipCount 拿取文章的Index
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     *
     */
    @RecordApi(cmoneyAction = "getforumpopulararticles")
    @FormUrlEncoded
    @POST
    suspend fun getForumPopularArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getforumpopulararticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("skipCount") skipCount: Long,
        @Field("fetchCount") fetchCount: Int,
        @Field("isIncludeLimitedAskArticle") IsIncludeLimitedAskArticle: Boolean = false
    ): Response<GetForumPopularArticlesResponseWithError>

    /**
     * 服務2-10. 取得多個頻道的熱門文章清單(可訪客)
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param channelIdList 頻道編號列表(以逗號區隔)
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取的筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     *
     */
    @RecordApi(cmoneyAction = "getchannelpopulararticles")
    @POST
    @FormUrlEncoded
    suspend fun getChannelPopularArticles(
        @Url url: String,
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
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 文章ID
     *
     */
    @RecordApi(cmoneyAction = "getsinglearticle")
    @POST
    @FormUrlEncoded
    suspend fun getSingleArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getsinglearticle",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("ArticleId") articleId: Long
    ): Response<GetSingleArticleResponseWithError>

    /**
     * 服務2-12. 取得追蹤頻道的最新文章清單
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param channelCategory 頻道分類
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料(第一次填 long.MaxValue = 9223372036854775807)
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章( 預設:false )
     *
     */
    @RecordApi(cmoneyAction = "getfollowedchannelarticles")
    @POST
    @FormUrlEncoded
    suspend fun getFollowedChannelArticles(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getfollowedchannelarticles",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("Category") channelCategory: Int,
        @Field("BaseArticleId") baseArticleId: Long,
        @Field("FetchCount") fetchCount: Int,
        @Field("IsIncludeLimitedAskArticle") isIncludeLimitedAskArticle: Boolean
    ): Response<GetFollowedChannelArticlesResponseWithError>

    /**
     * 服務2-13. 取得最熱問答文章清單(可訪客)
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockIdList 股票清單，填空字串，表示拿所有問答
     * @param skipCount 略過的筆數
     * @param fetchCount 要取得筆數
     *
     */
    @RecordApi(cmoneyAction = "getpopularquestionarticles")
    @FormUrlEncoded
    @POST
    suspend fun getPopularQuestionArticles(
        @Url url: String,
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
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockIdList 股票清單，填空字串，表示拿所有問答
     * @param skipCount 略過的筆數
     * @param fetchCount 要取得筆數
     *
     */
    @RecordApi(cmoneyAction = "getlatestquestionarticles")
    @FormUrlEncoded
    @POST
    suspend fun getLatestQuestionArticles(
        @Url url: String,
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
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 文章ID
     *
     */
    @RecordApi(cmoneyAction = "likearticle")
    @FormUrlEncoded
    @POST
    suspend fun likeArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "likearticle",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long
    ): Response<LikeArticleResponseWithError>

    /**
     * 服務3-3. 會員打賞文章
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 打賞文章代號
     * @param tip 打賞金額
     *
     */
    @RecordApi(cmoneyAction = "givearticletip")
    @FormUrlEncoded
    @POST
    suspend fun giveArticleTip(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "givearticletip",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long,
        @Field("Tip") tip: Int
    ): Response<GiveArticleTipResponseWithError>

    /**
     * 服務3-4. 我也想知道回答
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 文章ID
     * @param points 想知道回答所付出的點數
     *
     */
    @RecordApi(cmoneyAction = "addinterestedinarticleinfo")
    @FormUrlEncoded
    @POST
    suspend fun addInterestedInArticleInfo(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "addinterestedinarticleinfo",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long,
        @Field("Points") points: Int
    ): Response<AddInterestedInArticleInfoResponseWithError>

    /**
     * 服務3-5. 按噓
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 文章ID
     *
     */
    @RecordApi(cmoneyAction = "dislikearticle")
    @FormUrlEncoded
    @POST
    suspend fun dislikeArticle(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "dislikearticle",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("articleId") articleId: Long
    ): Response<DisLikeArticleResponseWithError>

    /**
     * 服務4-1. 詢問今天是否跪求過
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockId 股票代號
     *
     */
    @RecordApi(cmoneyAction = "istodayaskedstocktendency")
    @FormUrlEncoded
    @POST
    suspend fun isTodayAskedStockTendency(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "istodayaskedstocktendency",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String
    ): Response<IsTodayAskedStockTendencyResponseWithError>

    /**
     * 服務4-2. 跪求股票趨勢
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockId 股票代號
     *
     */
    @RecordApi(cmoneyAction = "addaskstocktendnecylog")
    @FormUrlEncoded
    @POST
    suspend fun addAskStockTendencyLog(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "addaskstocktendnecylog",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String
    ): Response<AddAskStockTendencyLogResponseWithError>

    /**
     * 服務4-3 取得跪求股票趨勢數量
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockId 股票代號
     *
     */
    @RecordApi(cmoneyAction = "getaskstocktendencyamount")
    @FormUrlEncoded
    @POST
    suspend fun getAskStockTredencyAmount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getaskstocktendencyamount",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String
    ): Response<AskStockTendencyAmountResponseWithError>

    /**
     * 服務6-1. 追蹤頻道
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param channelId 頻道代號
     */
    @RecordApi(cmoneyAction = "followchannel")
    @FormUrlEncoded
    @POST
    suspend fun followChannel(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "followchannel",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("channelid") channelId: Long
    ): Response<FollowChannelResponseWithError>

    /**
     * 服務6-2. 離開頻道(取消追蹤)
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param channelId 頻道代號
     *
     */
    @RecordApi(cmoneyAction = "leavechannel")
    @FormUrlEncoded
    @POST
    suspend fun leaveChannel(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "leavechannel",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("channelid") channelId: Long
    ): Response<LeaveChannelResponseWithError>

    /**
     * 服務6-3. 更新頻道描述
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param description 頻道描述
     *
     */
    @RecordApi(cmoneyAction = "updatechanneldescription")
    @FormUrlEncoded
    @POST
    suspend fun updateChannelDescription(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "updatechanneldescription",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("description") description: String
    ): Response<UpdateChannelIdDescriptionResponseWithError>

    /**
     * 服務7-1. 取得熱門股票(可訪客)
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param fetchCount 取得筆數
     *
     */
    @RecordApi(cmoneyAction = "getpopularstocks")
    @FormUrlEncoded
    @POST
    suspend fun getPopularStocks(
        @Url url: String,
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
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param articleId 指定文章Id
     * @param fetchCount 取得數量
     * @param skipCount 跳過數量
     *
     */
    @RecordApi(cmoneyAction = "getarticletips")
    @FormUrlEncoded
    @POST
    suspend fun getArticleTips(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getarticletips",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("ArticleId") articleId: Long,
        @Field("FetchCount") fetchCount: Int,
        @Field("SkipCount") skipCount: Int
    ): Response<GetArticleTipsResponseWithError>

    /**
     * 服務8-3. 取得指定頻道的粉絲清單(被哪些人追蹤)
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param channelId 頻道代號
     * @param needInfo 需要的資訊 ( 複合列舉 )，使用方式請洽備註8B
     * @param skipCount 跳過數量
     * @param fetchCount 取得數量
     *
     */
    @RecordApi(cmoneyAction = "getfanschannel")
    @FormUrlEncoded
    @POST
    suspend fun getFansChannel(
        @Url url: String,
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
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param channelId 頻道代號
     * @param needInfo 需要的資訊 ( 複合列舉 )，使用方式請洽備註8B
     * @param skipCount 跳過數量
     * @param fetchCount 取得數量
     *
     */
    @RecordApi(cmoneyAction = "getattentionchannel")
    @FormUrlEncoded
    @POST
    suspend fun getAttentionChannel(
        @Url url: String,
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
     * @param appId App 編號
     * @param guid 該會員的Guid
     * @param stockId 股票代號
     * @param pictureType 推圖類型( 1.即時走勢 , 2.日K , 3.週K  , 4.月K , 6.還原日收盤K)
     *
     */
    @RecordApi(cmoneyAction = "getstockpicture")
    @FormUrlEncoded
    @POST
    suspend fun getStockPicture(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getstockpicture",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String,
        @Field("PictureType") pictureType: Int
    ): Response<GetStockPictureResponseWithError>

    /**
     * 服務9-3. 啟用追訊，拿50購物金
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     *
     */
    @RecordApi(cmoneyAction = "getmembermasterranking")
    @FormUrlEncoded
    @POST
    suspend fun getMemberMasterRanking(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getmembermasterranking",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetMemberMasterRankingResponseWithError>

    /**
     * 服務9-3. 啟用追訊，拿50購物金
     *
     * @param appId App 編號
     * @param guid 該會員的Guid
     *
     */
    @RecordApi(cmoneyAction = "activiefollow")
    @FormUrlEncoded
    @POST
    suspend fun activeFollow(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "activiefollow",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<ActiveFollowResponseWithError>
}
