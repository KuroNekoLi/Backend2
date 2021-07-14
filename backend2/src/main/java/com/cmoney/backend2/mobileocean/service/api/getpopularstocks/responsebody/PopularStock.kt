package com.cmoney.backend2.mobileocean.service.api.getpopularstocks.responsebody


import com.google.gson.annotations.SerializedName

data class PopularStock(

    /**
     * 股票文章熱門值
     */
    @SerializedName("ArticlePopularity")
    val articlePopularity: Int?,

    /**
     * 股票頻道編號
     */
    @SerializedName("ChannelId")
    val channelId: Long?,

    /**
     * 是否有上次排名
     */
    @SerializedName("HasLastRanking")
    val hasLastRanking: Boolean?,

    /**
     * 股票代號
     */
    @SerializedName("Key")
    val key: String?,

    /**
     * 上次排行， 若沒有上次排名(EX:新股票)，則為int最大值2147483647
     */
    @SerializedName("LastRanking")
    val lastRanking: Int?,

    /**
     * 股票名稱
     */
    @SerializedName("Name")
    val name: String?,

    /**
     * 目前排行
     */
    @SerializedName("Ranking")
    val ranking: Int?,

    /**
     * 最熱 或 最新文章資訊
     */
    @SerializedName("ArticleInfo")
    val articleInfo: PopularStockArticleInfo?
)