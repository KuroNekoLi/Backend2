package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articlestate.ArticleState
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag.CommodityTagInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo
import com.google.gson.annotations.SerializedName

sealed class ArticleContent(
    @Transient
    open val text: String?
) {

    /**
     * 未知的文章內容
     *
     * @property text
     */

    /**
     * 未知的文章內容
     *
     * @property text 文章內容
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property anonymous 是否匿名
     * @property appId 發文平台
     * @property creatorId 發文者Id
     * @property groupId 社團Id
     * @property position 有帶此參數，代表以社團名義發文
     * @property newsId 新聞Id
     * @property title 新聞標題
     * @property publishTime 新聞發佈時間
     * @property sharedPostsArticleId 轉推文章Id
     * @property botId 訊號Id
     * @property voteOptions 投票選項
     * @property voteMinutes 發文起算投票截止分鐘數
     * @property askPoint 問答支付P幣
     * @property bestAnswerCommentId 最佳解答回文Id
     * @property articleState 文章狀態
     * @property topics 標籤名稱
     */
    data class UnknownContent(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("anonymous", alternate = ["Anonymous"])
        override val anonymous: Any?,
        @SerializedName("appId", alternate = ["AppId"])
        override val appId: Int?,
        @SerializedName("topics", alternate = ["Topics"])
        override val topics: List<String>?,
        @SerializedName("creatorId", alternate = ["CreatorId"])
        override val creatorId: Long?,
        @SerializedName("groupId", alternate = ["GroupId"])
        override val groupId: Long?,
        @SerializedName("position", alternate = ["Position"])
        override val position: Any?,
        @SerializedName("newsId", alternate = ["NewsId"])
        override val newsId: Long?,
        @SerializedName("title", alternate = ["Title"])
        override val title: String?,
        @SerializedName("publishTime", alternate = ["PublishTime"])
        override val publishTime: Long?,
        @SerializedName("sharedPostsArticleId", alternate = ["SharedPostsArticleId"])
        override val sharedPostsArticleId: Long?,
        @SerializedName("botId", alternate = ["BotId"])
        override val botId: Long?,
        @SerializedName("voteOptions", alternate = ["VoteOptions"])
        override val voteOptions: List<String>?,
        @SerializedName("voteMinutes", alternate = ["VoteMinutes"])
        override val voteMinutes: Int?,
        @SerializedName("askPoint", alternate = ["AskPoint"])
        override val askPoint: Int?,
        @SerializedName("bestAnswerCommentId", alternate = ["BestAnswerCommentId"])
        override val bestAnswerCommentId: Long?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?,
        @SerializedName("authType", alternate = ["AuthType"])
        override val authType: String?,
        @SerializedName("pCoin", alternate = ["PCoin"])
        override val pCoin: Long?,
        @SerializedName("exchangeCount", alternate = ["ExchangeCount"])
        override val exchangeCount: Long?,
        @SerializedName("articleType", alternate = ["ArticleType"])
        override val articleType: String?
    ) : ArticleContent(text),
        MultiMediaInfo,
        TagInfo,
        AnonymousInfo,
        CreatorInfo,
        GroupInfo,
        NewsInfo,
        ShareArticleInfo,
        SignalInfo,
        VoteOptionInfo,
        QuestionInfo,
        ArticleStateInfo,
        TopicInfo,
        ColumnInfo

    /**
     * 一般文章
     *
     * @property text 文章內容
     * @property appId 發文平台
     * @property creatorId 發文者資訊
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property voteOptions 投票選項
     * @property voteMinutes 發文起算投票截止分鐘數
     * @property articleState 文章狀態
     * @property topics 標籤名稱
     */
    data class General(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("appId", alternate = ["AppId"])
        override val appId: Int?,
        @SerializedName("creatorId", alternate = ["CreatorId"])
        override val creatorId: Long?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("topics", alternate = ["Topics"])
        override val topics: List<String>?,
        @SerializedName("voteOptions", alternate = ["VoteOptions"])
        override val voteOptions: List<String>?,
        @SerializedName("voteMinutes", alternate = ["VoteMinutes"])
        override val voteMinutes: Int?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        CreatorInfo,
        MultiMediaInfo,
        TagInfo,
        VoteOptionInfo,
        ArticleStateInfo,
        TopicInfo

    /**
     * 社團文章
     *
     * @property text 文章內容
     * @property appId 發文平台
     * @property creatorId 發文者Id
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property groupId 社團Id
     * @property position 有帶此參數，代表以社團名義發文
     * @property voteOptions 投票選項
     * @property voteMinutes 發文起算投票截止分鐘數
     * @property articleState 文章狀態
     */
    data class Group(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("appId", alternate = ["AppId"])
        override val appId: Int?,
        @SerializedName("creatorId", alternate = ["CreatorId"])
        override val creatorId: Long?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("groupId", alternate = ["GroupId"])
        override val groupId: Long?,
        @SerializedName("position", alternate = ["Position"])
        override val position: Any?,
        @SerializedName("voteOptions", alternate = ["VoteOptions"])
        override val voteOptions: List<String>?,
        @SerializedName("voteMinutes", alternate = ["VoteMinutes"])
        override val voteMinutes: Int?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        CreatorInfo,
        MultiMediaInfo,
        TagInfo,
        GroupInfo,
        VoteOptionInfo,
        ArticleStateInfo

    /**
     * 轉推文章
     *
     * @property text 文章內容
     * @property appId 發文平台
     * @property creatorId 發文者Id
     * @property sharedPostsArticleId 轉推文章Id
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property voteOptions 投票選項
     * @property voteMinutes 發文起算投票截止分鐘數
     * @property articleState 文章狀態
     * @property topics 標籤名稱
     */
    data class Shared(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("appId", alternate = ["AppId"])
        override val appId: Int?,
        @SerializedName("creatorId", alternate = ["CreatorId"])
        override val creatorId: Long?,
        @SerializedName("sharedPostsArticleId", alternate = ["SharedPostsArticleId"])
        override val sharedPostsArticleId: Long?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("topics", alternate = ["Topics"])
        override val topics: List<String>?,
        @SerializedName("voteOptions", alternate = ["VoteOptions"])
        override val voteOptions: List<String>?,
        @SerializedName("voteMinutes", alternate = ["VoteMinutes"])
        override val voteMinutes: Int?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        CreatorInfo,
        ShareArticleInfo,
        MultiMediaInfo,
        TagInfo,
        VoteOptionInfo,
        ArticleStateInfo,
        TopicInfo

    /**
     * 新聞文章
     *
     * @property text 文章內容
     * @property newsId 新聞Id
     * @property title 新聞標題
     * @property publishTime 新聞發布時間
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property articleState 文章狀態
     */
    data class News(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("newsId", alternate = ["NewsId"])
        override val newsId: Long?,
        @SerializedName("title", alternate = ["Title"])
        override val title: String?,
        @SerializedName("publishTime", alternate = ["PublishTime"])
        override val publishTime: Long?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        NewsInfo,
        MultiMediaInfo,
        TagInfo,
        ArticleStateInfo

    /**
     * 訊號文章
     *
     * @property text 文章內容
     * @property botId 訊號來源Id
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property articleState 文章狀態
     */
    data class Signal(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("botId", alternate = ["BotId"])
        override val botId: Long?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        SignalInfo,
        MultiMediaInfo,
        TagInfo,
        ArticleStateInfo

    /**
     * 問答文章
     *
     * @property text 文章內容
     * @property appId 發文平台
     * @property creatorId 發文者Id
     * @property anonymous 是否匿名
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property askPoint 問答支付P幣
     * @property bestAnswerCommentId 最佳解答回文Id
     * @property articleState 文章狀態
     * @property topics 標籤名稱
     */
    data class Question(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("appId", alternate = ["AppId"])
        override val appId: Int?,
        @SerializedName("creatorId", alternate = ["CreatorId"])
        override val creatorId: Long?,
        @SerializedName("anonymous", alternate = ["Anonymous"])
        override val anonymous: Any?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("topics", alternate = ["Topics"])
        override val topics: List<String>?,
        @SerializedName("askPoint", alternate = ["AskPoint"])
        override val askPoint: Int?,
        @SerializedName("bestAnswerCommentId", alternate = ["BestAnswerCommentId"])
        override val bestAnswerCommentId: Long?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        CreatorInfo,
        MultiMediaInfo,
        TagInfo,
        AnonymousInfo,
        QuestionInfo,
        ArticleStateInfo,
        TopicInfo

    /**
     * 個人文章
     *
     * @property text 文章內容
     * @property appId 發文平台
     * @property creatorId 發文者資訊
     * @property multiMedia 多媒體資訊
     * @property commodityTags 股票Tag資訊
     * @property articleState 文章狀態
     * @property topics 標籤名稱
     */
    data class Personal(
        @SerializedName("text", alternate = ["Text"])
        override val text: String?,
        @SerializedName("appId", alternate = ["AppId"])
        override val appId: Int?,
        @SerializedName("creatorId", alternate = ["CreatorId"])
        override val creatorId: Long?,
        @SerializedName("multiMedia", alternate = ["MultiMedia"])
        override val multiMedia: List<MediaTypeInfo>?,
        @SerializedName("commodityTags", alternate = ["CommodityTags"])
        override val commodityTags: List<CommodityTagInfo>?,
        @SerializedName("topics", alternate = ["Topics"])
        override val topics: List<String>?,
        @SerializedName("articleState", alternate = ["ArticleState"])
        override val articleState: ArticleState?
    ) : ArticleContent(text),
        CreatorInfo,
        MultiMediaInfo,
        TagInfo,
        ArticleStateInfo,
        TopicInfo
}