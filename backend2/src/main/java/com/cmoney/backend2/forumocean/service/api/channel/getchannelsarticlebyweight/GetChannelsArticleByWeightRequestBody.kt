package com.cmoney.backend2.forumocean.service.api.channel.getchannelsarticlebyweight

import com.google.gson.annotations.SerializedName

/**
 * 取的文章清單的request body
 *
 * @property items 頻道清單
 */
data class GetChannelsArticleByWeightRequestBody(
    @SerializedName("items")
    val items : List<String>
)