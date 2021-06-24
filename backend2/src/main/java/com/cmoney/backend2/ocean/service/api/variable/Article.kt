package com.cmoney.backend2.ocean.service.api.variable
import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("ArticleFrom")
    val articleFrom: String?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("ArticleType")
    val articleType: OceanArticleType?,
    @SerializedName("AskInfo")
    val askInfo: AskInfo?,
    @SerializedName("AuthorInfo")
    val authorInfo: AuthorInfo?,
    @SerializedName("ClickCount")
    val clickCount: Int?,
    @SerializedName("CollectCount")
    val collectCount: Int?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("ContentImage")
    val contentImage: String?,
    @SerializedName("ContentVideoUrl")
    val contentVideoUrl: String?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("DislikeCount")
    val dislikeCount: Int?,
    @SerializedName("IsAnonymous")
    val isAnonymous: Boolean?,
    @SerializedName("IsCollected")
    val isCollected: Boolean?,
    @SerializedName("IsDisliked")
    val isDisliked: Boolean?,
    @SerializedName("IsFollowedAuthor")
    val isFollowedAuthor: Boolean?,
    @SerializedName("IsLiked")
    val isLiked: Boolean?,
    @SerializedName("LikeCount")
    val likeCount: Int?,
    @SerializedName("LocationChannelInfo")
    val locationChannelInfo: LocationChannelInfo?,
    @SerializedName("ReplyCount")
    val replyCount: Int?,
    @SerializedName("RetweetCount")
    val retweetCount: Int?,
    @SerializedName("RetweetOriginalArticle")
    val retweetOriginalArticle: Article?,
    @SerializedName("SourceUrl")
    val sourceUrl: String?,
    @SerializedName("StockTags")
    val stockTags: List<StockTag>?,
    @SerializedName("StockInfos")
    val stockInfos: List<StockInfo>?,
    @SerializedName("TipInfo")
    val tipInfo: TipInfo?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("MultipleImages")
    val multipleImages : List<String>?
)

