package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 請加入以下欄位
 * @SerializedName("collected")
 * val collected : Any?,
 * @SerializedName("@hash-collect")
 * val collectCount : Int?,
 *
 */
interface CollectedInfo {

    /**
     * 是否收藏，有此屬性代表有收藏
     */
    val collected : Any?

    /**
     * 總收藏數
     */
    val collectCount : Int?
}