package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag

import com.cmoney.backend2.forumocean.service.api.schemas.v2.CommodityTag
import com.google.gson.annotations.SerializedName

/**
 *  為更精確反映後端API格式，請改用[CommodityTag]
 */
@Deprecated("請改用 CommodityTag")
data class CommodityTagInfo(
    @SerializedName("key", alternate = ["Key"])
    val commodityKey : String?,
    @SerializedName("bullOrBear", alternate = ["BullOrBear"])
    val bullOrBear : BullOrBearInfo?,
    @SerializedName("type", alternate = ["Type"])
    val type : StockType?
)