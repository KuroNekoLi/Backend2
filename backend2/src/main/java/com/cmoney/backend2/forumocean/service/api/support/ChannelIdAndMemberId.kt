package com.cmoney.backend2.forumocean.service.api.support

import com.google.gson.annotations.SerializedName

/**
 * 會員Id 與頻道Id的對照表
 *
 * @property channelId 頻道Id
 * @property memberId 會員Id
 */
data class ChannelIdAndMemberId(
        @SerializedName("channelId")
        val channelId : Long?,
        @SerializedName("memberId")
        val memberId : Long?
)
