package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 * 請加入以下欄位
 * @SerializedName("reactionState")
 * val reactionState : Int?,
 * @SerializedName("myReaction")
 * val myReaction: String?,
 * @SerializedName("reactions", alternate = ["reaction"])
 * val reaction : Map<String,Int>?,
 * @SerializedName("@list-reaction")
 * val reactionCount : Int?,
 *
 */
interface ReactionInfo {

    /**
     * 我的反應
     */
    @Deprecated("Use myReaction field instead.(make sure api do returns one)")
    val reactionState : Int?

    /**
     * 我的反應
     * @see reactionState
     */
    val myReaction: String?

    /**
     * [目前反應類別: 該反應數]
     */
    val reaction : Map<String,Int>?

    /**
     * 主文的總反應數
     */
    val reactionCount : Int?
}