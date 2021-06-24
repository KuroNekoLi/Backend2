package com.cmoney.backend2.mobileocean.service.api.getsinglearticle


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.mobileocean.service.api.common.DiamondInfo
import com.cmoney.backend2.mobileocean.service.api.common.LevelInfo
import com.cmoney.backend2.mobileocean.service.api.common.Reply
import com.cmoney.backend2.mobileocean.service.api.common.article.AskInfo
import com.cmoney.backend2.mobileocean.service.api.common.article.StockInfo
import com.cmoney.backend2.mobileocean.service.api.common.article.StockTag
import com.cmoney.backend2.mobileocean.service.api.common.article.TipInfo
import com.google.gson.annotations.SerializedName

data class GetSingleArticleResponseWithError(
    @SerializedName("ArticleFrom")
    val articleFrom: String?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("ArticleType")
    val articleType: Int?,
    @SerializedName("AskInfo")
    val askInfo: AskInfo?,
    @SerializedName("AuthorChannelId")
    val authorChannelId: Long?,
    @SerializedName("AuthorImage")
    val authorImage: String?,
    @SerializedName("AuthorName")
    val authorName: String?,
    @SerializedName("Content")
    val content: String?,
    @SerializedName("ContentImage")
    val contentImage: String?,
    @SerializedName("ContentVideoPath")
    val contentVideoPath: String?,
    @SerializedName("CreateTime")
    val createTime: Long?,
    @SerializedName("DiamondInfo")
    val diamondInfo: DiamondInfo?,
    @SerializedName("DislikeCount")
    val dislikeCount: Int?,
    @SerializedName("HasAuthToReadReply")
    val hasAuthToReadReply: Boolean?,
    @SerializedName("IsCollected")
    val isCollected: Boolean?,
    @SerializedName("IsDisliked")
    val isDisliked: Boolean?,
    @SerializedName("IsFollowedAuthor")
    val isFollowedAuthor: Boolean?,
    @SerializedName("IsLiked")
    val isLiked: Boolean?,
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("LikeCount")
    val likeCount: Int?,
    @SerializedName("MultipleImages")
    val multipleImages: List<String?>?,
    @SerializedName("ReplyCount")
    val replyCount: Int?,
    @SerializedName("ReplyList")
    val replyList: List<Reply?>?,
    @SerializedName("SourceUrl")
    val sourceUrl: String?,
    @SerializedName("TipInfo")
    val tipInfo: TipInfo?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("StockTags")
    val stockTags: List<StockTag?>?,
    @SerializedName("StockInfos")
    val stockInfos: List<StockInfo>?
) : CMoneyError(), IWithError<GetSingleArticleResponse> {

    override fun toRealResponse(): GetSingleArticleResponse {
        return GetSingleArticleResponse(
            articleFrom,
            articleId,
            articleType,
            askInfo,
            authorChannelId,
            authorImage,
            authorName,
            content,
            contentImage,
            contentVideoPath,
            createTime,
            diamondInfo,
            dislikeCount,
            hasAuthToReadReply,
            isCollected,
            isDisliked,
            isFollowedAuthor,
            isLiked,
            levelInfo,
            likeCount,
            multipleImages,
            replyCount,
            replyList,
            sourceUrl,
            tipInfo,
            title,
            stockTags,
            stockInfos
        )
    }
}