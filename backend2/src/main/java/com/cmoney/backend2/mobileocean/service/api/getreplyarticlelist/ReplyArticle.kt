package com.cmoney.backend2.mobileocean.service.api.getreplyarticlelist

import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.cmoney.backend2.mobileocean.service.api.common.DiamondInfo
import com.cmoney.backend2.mobileocean.service.api.common.LevelInfo
import com.google.gson.annotations.SerializedName

/**
 * 回應文章
 *
 * @property articleFrom 文章來源
 * @property articleId 文章編號
 * @property articleType 文章類型
 * @property authorChannelId 發文者頻道編號
 * @property authorImage 發文者頭像
 * @property authorName 發文者名稱
 * @property content 文章內容
 * @property contentImage 內文圖片的連結(沒有的話是string.empty)
 * @property contentVideoPath 內文影片的連結(沒有的話是string.empty)
 * @property createTime 建立時間
 * @property diamondInfo 鑽石資料(發文者是人的時候才會有)
 * @property dislikeCount
 * @property hasAuthToReadReply 是否有觀看回文的權限
 * @property isAnonymous
 * @property isDisliked
 * @property isLiked 使用者是否按讚
 * @property levelInfo 追訊等級(發文者是人的時候才會有)
 * @property likeCount 按讚數
 * @property replyCount 回應數
 * @property replyArticle 回應的文章
 */
data class ReplyArticle(
    @SerializedName("ArticleFrom")
    val articleFrom: String?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("ArticleType")
    val articleType: ArticleType?,
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
    @SerializedName("IsAnonymous")
    val isAnonymous: Boolean?,
    @SerializedName("IsDisliked")
    val isDisliked: Boolean?,
    @SerializedName("IsLiked")
    val isLiked: Boolean?,
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("LikeCount")
    val likeCount: Int?,
    @SerializedName("ReplyArticle")
    val replyArticle: List<ReplyArticle?>?,
    @SerializedName("ReplyCount")
    val replyCount: Int?
)