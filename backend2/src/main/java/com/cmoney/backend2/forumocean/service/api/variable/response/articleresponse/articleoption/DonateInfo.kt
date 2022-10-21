package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 請加入以下欄位
 * @SerializedName("@vhash-donate")
 * val donateCount : Int?
 * @SerializedName("donate")
 * val donate : Int?
 */
interface DonateInfo {

    /**
     * 總打賞金額
     */
    val donateCount : Int?

    /**
     * 使用者對文章打賞的金額
     */
    val donate : Int?
}