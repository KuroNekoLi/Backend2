package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 請加入以下欄位
 * @SerializedName("interested")
 * val interested : Any?
 * @SerializedName("@hash-interest")
 * val interestCount : Int?
 * @SerializedName("RewardPoints")
 * val rewardPoints : Int?
 *
 */
interface QuestionInfo {

    /**
     * 是否感興趣，有此屬性代表感興趣
     */
    val interested : Any?

    /**
     * 感興趣的人總數
     */
    val interestCount : Int?

    /**
     * 問答獎金
     */
    val rewardPoints : Int?
}