package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * @SerializedName("VoteOptions")
 * val voteOptions : List<String>?,
 * @SerializedName("VoteMinutes")
 * val voteMinutes : Int?
 */
interface VoteOptionInfo {

    val voteOptions : List<String>?

    val voteMinutes : Int?
}