package com.cmoney.backend2.mobileocean.service.api.common.article

import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.google.gson.annotations.SerializedName

/**
 * 轉推新聞資訊
 */
data class RetweetOriginalArticle(
    /**
     * 文章編號
     */
    @SerializedName("ArticleId")
    val articleId : Long?,

    /**
     * 文章類型
     */
    @SerializedName("ArticleType")
    val articleType : ArticleType?,

    /**
     * 文章標題 (新聞類型文章才有)
     */
    @SerializedName("Title")
    val title : String?,

    /**
     * 文章內容
     */
    @SerializedName("Content")
    val content : String?,

    /**
     * 來源連結 (新聞類型文章才有)
     */
    @SerializedName("SourceUrl")
    val sourceUrl : String?,

    /**
     * 發文者名稱 or 發文頻道名稱
     */
    @SerializedName("AuthorName")
    val authorName : String?,

    /**
     *  發文者頻道類型
     * 101:會員頻道
     * 601:訊號頻道
     * 701:RSS新聞頻道
     */
    @SerializedName("AuthorChannelType")
    val authorChannelType : ChannelType?,

    /**
     * 按讚數
     */
    @SerializedName("LikeCount")
    val likeCount : Int?,

    /**
     * 轉推數
     */
    @SerializedName("RetweetCount")
    val retweetCount : Int?
)