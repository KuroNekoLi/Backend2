package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * 請加入以下欄位
 * @SerializedName("NewsId")
 * val newsId : Long
 * @SerializedName("Title")
 * val title : String?
 * @SerializedName("PublishTime")
 * val publishTime : Long?
 *
 */
interface NewsInfo {

    /**
     * 新聞Id
     */
    val newsId : Long?

    /**
     * 新聞標題
     */
    val title : String?

    /**
     * 新聞發佈時間
     */
    val publishTime : Long?

}