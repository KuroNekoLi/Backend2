package com.cmoney.backend2.mobileocean.service.api.common.article

import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.cmoney.backend2.mobileocean.service.api.common.DiamondInfo
import com.cmoney.backend2.mobileocean.service.api.common.LevelInfo
import com.google.gson.annotations.SerializedName

/**
 *  文章
 *  (服務2-7 ~ 服務2-14)
 *
 * @property articleFrom 文章來源
 * @property articleId 文章編號
 * @property articleType 文章類型
 * @property askInfo 問答文章資訊(只有問答文章會出現)
 * @property authorChannelId 發文者頻道編號
 * @property authorChannelType 發文者頻道類型
 * @property authorImage 發文者頭像 (討論類型文章才有)
 * @property authorName 發文者名稱
 * @property clickCount 點擊數
 * @property content 文章內容
 * @property contentImage 內文附帶的圖片
 * @property contentVideoPath 內文影片的連結(沒有的話是string.empty)
 * @property createTime 建立時間
 * @property diamondInfo 鑽石資料(發文者是人的時候才會有)
 * @property dislikeCount 按噓數
 * @property isAnonymous 是否匿名
 * @property isCollected 使用者是否收藏此文章
 * @property isDisliked 使用者是否按噓
 * @property isFollowedAuthor 是否追蹤發文者(討論類型文章才有)
 * @property isLiked 使用者是否按讚
 * @property levelInfo 追訊等級(發文者是人的時候才會有)
 * @property likeCount 按讚數
 * @property replyCount 回應數
 * @property retweetCount 轉推數
 * @property sourceUrl 來源連結 (新聞類型文章才有)
 * @property stockTags 股票標籤清單
 * @property tipInfo 打賞資訊
 * @property title 文章標題 (新聞類型文章才有)
 */
data class Article(
    @SerializedName("ArticleFrom")
    val articleFrom: String?,
    @SerializedName("ArticleId")
    val articleId: Long?,
    @SerializedName("ArticleType")
    val articleType: ArticleType?,
    @SerializedName("AskInfo")
    val askInfo: AskInfo?,
    @SerializedName("AuthorChannelId")
    val authorChannelId: Long?,
    @SerializedName("AuthorChannelType")
    val authorChannelType: ChannelType?,
    @SerializedName("AuthorImage")
    val authorImage: String?,
    @SerializedName("AuthorName")
    val authorName: String?,
    @SerializedName("ClickCount")
    val clickCount: Int?,
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
    @SerializedName("LevelInfo")
    val levelInfo: LevelInfo?,
    @SerializedName("LikeCount")
    val likeCount: Int?,
    @SerializedName("ReplyCount")
    val replyCount: Int?,
    @SerializedName("RetweetCount")
    val retweetCount: Int?,
    @SerializedName("SourceUrl")
    val sourceUrl: String?,
    @SerializedName("StockTags")
    val stockTags: List<StockTag?>?,
    @SerializedName("StockInfos")
    val stockInfos: List<StockInfo>?,
    @SerializedName("TipInfo")
    val tipInfo: TipInfo?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("RetweetOriginalArticle")
    val retweetOriginalArticle: RetweetOriginalArticle?,
    @SerializedName("LocationChannelInfo")
    val locationChannelInfo: LocationChannelInfo?
)
