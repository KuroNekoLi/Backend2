package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag

import com.google.gson.annotations.SerializedName

data class CommodityTagInfo(
    @SerializedName("key", alternate = ["Key"])
    val commodityKey : String?,
    @SerializedName("bullOrBear", alternate = ["BullOrBear"])
    val bullOrBear : BullOrBearInfo?,
    @SerializedName("type", alternate = ["Type"])
    val type : StockType?
)