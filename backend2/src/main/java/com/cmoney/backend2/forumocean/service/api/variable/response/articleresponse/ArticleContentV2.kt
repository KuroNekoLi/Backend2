package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse

import com.cmoney.backend2.forumocean.service.api.article.OpenGraph
import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag.CommodityTagInfo
import com.cmoney.backend2.forumocean.service.api.variable.response.mediatype.MediaTypeInfo
import com.google.gson.annotations.SerializedName

data class ArticleContentV2(
    @SerializedName("text", alternate = ["Text"])
    val text: String?,
    @SerializedName("multiMedia", alternate = ["MultiMedia"])
    val multiMedia: List<MediaTypeInfo>?,
    @SerializedName("commodityTags", alternate = ["CommodityTags"])
    val commodityTags: List<CommodityTagInfo>?,
    @SerializedName("appId", alternate = ["AppId"])
    val appId: Int?,
    @SerializedName("topics", alternate = ["Topics"])
    val topics: List<String>?,
    @SerializedName("groupId", alternate = ["GroupId"])
    val groupId: Long?,
    @SerializedName("boardId", alternate = ["BoardId"])
    val boardId: Long?,
    @SerializedName("position", alternate = ["Position"])
    val position: Any?,
    @SerializedName("newsId", alternate = ["NewsId"])
    val newsId: Long?,
    @SerializedName("title", alternate = ["Title"])
    val title: String?,
    @SerializedName("publishTime", alternate = ["PublishTime"])
    val publishTime: Long?,
    @SerializedName("sharedPostsArticleId", alternate = ["SharedPostsArticleId"])
    val sharedPostsArticleId: Long?,
    @SerializedName("botId", alternate = ["BotId"])
    val botId: Long?,
    @SerializedName("voteOptions", alternate = ["VoteOptions"])
    val voteOptions: List<String>?,
    @SerializedName("voteMinutes", alternate = ["VoteMinutes"])
    val voteMinutes: Int?,
    @SerializedName("askPoint", alternate = ["AskPoint"])
    val askPoint: Int?,
    @SerializedName("bestAnswerCommentId", alternate = ["BestAnswerCommentId"])
    val bestAnswerCommentId: Long?,
    @SerializedName("pCoin", alternate = ["PCoin"])
    val pCoin: Long?,
    @SerializedName("exchangeCount", alternate = ["ExchangeCount"])
    val exchangeCount: Long?,
    @SerializedName("articleType", alternate = ["ArticleType"])
    val articleType: String?,
    @SerializedName("openGraph")
    val openGraph: OpenGraph?
)