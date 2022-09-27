package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 請加入以下欄位
 * @SerializedName("collected", alternate = ["hasCollect"])
 * val collected : Any?,
 * @SerializedName("@hash-collect", alternate = ["collectedCount"])
 * val collectCount : Int?,
 *
 */
interface CollectedInfo {

    /**
     * 是否收藏，有此屬性代表有收藏
     */
    val collected: Any? // TODO: Change this field to boolean after all api migrate to boolean type.

    /**
     * 總收藏數
     */
    val collectCount: Int?
}