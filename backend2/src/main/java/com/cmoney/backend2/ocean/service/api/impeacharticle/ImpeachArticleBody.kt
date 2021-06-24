package com.cmoney.backend2.ocean.service.api.impeacharticle

import com.google.gson.annotations.SerializedName

data class ImpeachArticleBody(
    @SerializedName("AppId")
    val appId: Int,
    /**
     * 被檢舉文章編號
     */
    @SerializedName("ArticleId")
    val articleId: Long,
    @SerializedName("Guid")
    val guid: String,

    /**
     * 檢舉原因 (最多50字)(檢舉原因有:垃圾廣告、人身攻擊、根本詐騙集團、與投資無關)
     */
    @SerializedName("Reason")
    val reason: String
)