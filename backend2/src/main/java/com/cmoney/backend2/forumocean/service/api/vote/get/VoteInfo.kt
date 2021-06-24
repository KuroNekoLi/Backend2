package com.cmoney.backend2.forumocean.service.api.vote.get

import com.google.gson.annotations.SerializedName

/**
 * 投票資訊
 *
 * @property option 投票選項的Index
 * @property count 投票數
 */
data class VoteInfo(
    @SerializedName("option")
    val option :Int?,
    @SerializedName("count")
    val count : Int?
)