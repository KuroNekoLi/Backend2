package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag

import com.google.gson.annotations.SerializedName

data class CommodityTagInfo(
    @SerializedName("commodityKey")
    val commodityKey : String?,
    @SerializedName("bullOrBear")
    val bullOrBear : BullOrBearInfo?
)