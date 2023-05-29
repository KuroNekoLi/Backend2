package com.cmoney.backend2.mobileocean.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.mobileocean.service.api.activefollow.ActiveFollowResponse
import com.cmoney.backend2.mobileocean.service.api.addaskstocktendnecylog.AddAskStockTendencyLogResponse
import com.cmoney.backend2.mobileocean.service.api.addinterestedinarticleinfo.AddInterestedInArticleInfoResponse
import com.cmoney.backend2.mobileocean.service.api.askstocktendencyamount.AskStockTendencyAmountResponse
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
import java.io.File

interface MobileOceanWeb {

    val manager: GlobalBackend2Manager

    /**
     * 服務1-1-2. 發文到給....一點建議
     *
     * @param submitAdviceParam 發文參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createArticleToOcean(
        submitAdviceParam: SubmitAdviceParam,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<CreateArticleToOceanResponse>

    /**
     * 服務1-2. 發表文章到股票追訊頻道
     *
     * @param articleContent 文章內容
     * @param appendQuestionParam 文章問題參數
     * @param appendClubParam 文章社團參數
     * @param appendParam 文章參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun createArticle(
        articleContent: String,
        appendQuestionParam: ArticleAppendQuestionParam? = null,
        appendClubParam: ArticleAppendClubParam? = null,
        appendParam: ArticleAppendParam? = null,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<CreateArticleResponse>

    /**
     * 服務1-3. 回應文章到追訊頻道
     *
     * @param stockId 股票代碼
     * @param articleId 文章ID
     * @param content 文章內容
     * @param isUseClubToReply 是否以社團名義回文
     * @param image 上傳的圖檔(server限制暫時是4Mb,不縮圖不限長寬)
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun replyArticle(
        stockId: String,
        articleId: Long,
        content: String,
        isUseClubToReply: Boolean,
        image: File? = null,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<ReplyArticleResponse>

    /**
     * 服務2-1-2. 取得追訊文章最新一篇回應ID(加上身份識別)
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     * @param articleIds 文章ID
     *
     */
    suspend fun getRepliedArticleIds(
        articleIds: List<Long>,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<RepliedArticleIds>

    /**
     * 服務2-2. 取得追訊股票討論區文章
     *
     * @param articleId 文章ID
     * @param stockId 股票代碼
     * @param fetchSize 要取的筆數
     * @param replyFetchSize 要取得的回應數量 (選填 預設為0)
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockArticleList(
        articleId: Long,
        stockId: String,
        fetchSize: Int,
        replyFetchSize: Int = 0,
        isIncludeLimitedAskArticle: Boolean = false,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetStockArticleListResponse>

    /**
     * 服務2-3. 取得個股討論區回應文章列表
     *
     * @param articleId 文章ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getReplyArticleList(
        articleId: Long,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetReplyArticleListResponse>

    /**
     * 服務2-4. 取得最新討論區文章
     *
     * @param baseArticleId 由哪一個ArticleId開始哪起，第一次填0
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getForumLatestArticles(
        baseArticleId: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetForumLatestArticlesResponse>

    /**
     * 服務2-5. 取得最熱門討論區文章
     *
     * @param skipCount 拿取文章的Index
     * @param fetchCount 要取的筆數
     * @param IsIncludeLimitedAskArticle 是否包含時效內的問答文章 ( 預設:false )
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getForumPopularArticles(
        skipCount: Long,
        fetchCount: Int,
        IsIncludeLimitedAskArticle: Boolean = false,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetForumPopularArticlesResponse>

    /**
     * 服務2-10. 取得多個頻道的熱門文章清單(可訪客)
     *
     * @param channelIdList 頻道編號清單 EX:  2295354,1652358,....
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getChannelPopularArticles(
        channelIdList: List<Long>,
        skipCount: Int,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean = false,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetChannelPopularArticlesResponse>

    /**
     *  服務2-11. 取得指定文章(可訪客)
     *
     * @param articleId 文章ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSingleArticle(
        articleId: Long,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetSingleArticleResponse>

    /**
     * 服務2-12. 取得追蹤頻道的最新文章清單
     *
     * @param channelCategory 頻道分類
     * @param baseArticleId 由哪一個ArticleId 開始往前拿資料(第一次填 long.MaxValue = 9223372036854775807)
     * @param fetchCount 要取得筆數
     * @param isIncludeLimitedAskArticle 是否包含時效內的問答文章( 預設:false )
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getFollowedChannelArticles(
        channelCategory: ChannelCategory,
        baseArticleId: Long,
        fetchCount: Int,
        isIncludeLimitedAskArticle: Boolean,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetFollowedChannelArticlesResponse>

    /**
     * 服務2-13. 取得最熱問答文章清單(可訪客)
     *
     * @param stockIdList 股票代碼清單
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPopularQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetPopularQuestionArticlesResponse>

    /**
     * 服務2-14. 取得最新問答文章清單(可訪客)
     *
     * @param stockIdList 股票代碼清單
     * @param skipCount 略過幾筆再開始拿
     * @param fetchCount 要取得筆數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getLatestQuestionArticles(
        stockIdList: List<String>,
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetLatestQuestionArticlesResponse>

    /**
     *  服務3-1. 按讚
     *
     * @param articleId 文章ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun likeArticle(
        articleId: Long,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<LikeArticleResponse>

    /**
     * 服務3-3. 會員打賞文章
     *
     * @param articleId 文章ID
     * @param tip 打賞金額
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun giveArticleTip(
        articleId: Long,
        tip: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GiveArticleTipResponse>

    /**
     * 服務3-4. 我也想知道回答
     *
     * @param articleId 文章ID
     * @param points 追蹤文章的分數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun addInterestedInArticleInfo(
        articleId: Long,
        points: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<AddInterestedInArticleInfoResponse>

    /**
     * 服務3-5. 按噓
     *
     * @param articleId 文章ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun dislikeArticle(
        articleId: Long,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<DisLikeArticleResponse>

    /**
     * 服務4-1. 詢問今天是否跪求過
     *
     * @param stockId 股票代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun isTodayAskedStockTendency(
        stockId: String,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<IsTodayAskedStockTendencyResponse>

    /**
     * 服務4-2. 跪求股票趨勢
     *
     * @param stockId 股票代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun addAskStockTendencyLog(
        stockId: String,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<AddAskStockTendencyLogResponse>

    /**
     * 服務4-3 取得跪求股票趨勢數量
     *
     * @param stockId 股票代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAskStockTendencyAmount(
        stockId: String,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<AskStockTendencyAmountResponse>

    /**
     * 服務6-1. 追蹤頻道
     *
     * @param channelId 頻道ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun followChannel(
        channelId: Long,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<FollowChannelResponse>

    /**
     * 服務6-2. 離開頻道(取消追蹤)
     *
     * @param channelId 頻道ID
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun leaveChannel(
        channelId: Long,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<LeaveChannelResponse>

    /**
     * 服務6-3. 更新頻道描述
     *
     * @param description 頻道描述
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateChannelDescription(
        description: String,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<UpdateChannelIdDescriptionResponse>

    /**
     * 服務7-1. 取得熱門股票(可訪客)
     *
     * @param param 參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPopularStocks(
        param: GetPopularStocksParam,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<PopularStockCollection>

    /**
     * 服務7-3. 取得指定文章的會員打賞清單(可訪客)
     *
     * @param articleId 指定文章Id
     * @param fetchCount 取得數量
     * @param skipCount 跳過數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getArticleTips(
        articleId: Long,
        fetchCount: Int,
        skipCount: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetArticleTipsResponse>

    /**
     * 服務8-3. 取得指定頻道的粉絲清單(被哪些人追蹤)
     *
     */
    suspend fun getFansChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetFansChannelResponse>

    /**
     * 服務8-4. 取得指定頻道所關注的頻道清單(追蹤哪些人)
     * NeedInfo 參考文件 https://docs.google.com/document/d/1AQRWgjmuVKZ3j69U-uutpMgTcfzk87WEFN75HyqBKH8/edit#heading=h.6bfg5lqmur1v
     *
     * @param channelId 頻道ID
     * @param needInfo 需要的資訊
     * @param skipCount 跳過數量
     * @param fetchCount 取得數量
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAttentionChannel(
        channelId: Long,
        needInfo: NeedInfo,
        skipCount: Int,
        fetchCount: Int,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetAttentionChannelResponse>

    /**
     * 服務9-1. 產生股票推圖
     *
     * @param stockId 股票代號
     * @param type 推圖類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getStockPicture(
        stockId: String,
        type: PictureType,
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetStockPictureResponse>

    /**
     * 服務9-2. 取得指定會員的達人名次和社群熱度
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getMemberMasterRanking(
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<GetMemberMasterRankingResponse>

    /**
     * 服務9-3. 啟用追訊，拿50購物金
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun activeFollow(
        domain: String = manager.getMobileOceanSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobileCode.ashx"
    ): Result<ActiveFollowResponse>
}