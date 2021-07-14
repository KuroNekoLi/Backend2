package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.articleoption

/**
 *
 * @SerializedName("@list-vote")
 * val voteCount : Int?
 * @SerializedName("voteStatus")
 * val voteStatus : Int?
 */
interface VoteStatusInfo {

    val voteCount : Int?

    val voteStatus : Int?
}