package com.cmoney.backend2.forumocean.service.api.article.create.variable.commoditytag

import com.google.gson.annotations.SerializedName

data class CommodityTag(
    @SerializedName("commodityKey")
    val commodityKey : String,
    @SerializedName("bullOrBear")
    val bullOrBear : BullOrBear
)