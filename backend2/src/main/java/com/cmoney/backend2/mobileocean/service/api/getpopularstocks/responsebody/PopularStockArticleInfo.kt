package com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody


import com.cmoney.backend2.mobileocean.service.api.common.ArticleType
import com.google.gson.annotations.SerializedName

data class PopularStockArticleInfo(

    /**
     * 文章編號
     */
    @SerializedName("ArticleId")
    val articleId: Long?,

    /**
     * 文章類型
     * (使用Enum: ArticleType)
     * 1 一般發文
     * 2 提問發文
     * 3 一般回文
     * 4 問答回文
     * 5 轉推文章
     */
    @SerializedName("ArticleType")
    val articleType: ArticleType?,

    /**
     * 發文者頻道類型
     * 101:會員頻道
     * 601:訊號頻道
     * 701:RSS新聞頻道
     */
    @SerializedName("AuthorChannelType")
    val authorChannelType: Int?,

    /**
     * 發文者名稱
     */
    @SerializedName("AuthorName")
    val authorName: String?,

    /**
     * 文章內容
     */
    @SerializedName("Content")
    val content: String?,

    /**
     * 1.最熱文章 2.最新文章
     */
    @SerializedName("Tag")
    val tag: Int?,

    /**
     * 文章標題 (新聞類型文章才有)
     */
    @SerializedName("Title")
    val title: String?
)