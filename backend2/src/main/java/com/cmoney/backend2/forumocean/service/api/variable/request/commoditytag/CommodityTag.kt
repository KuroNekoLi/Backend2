package com.cmoney.backend2.forumocean.service.api.variable.request.commoditytag

import com.google.gson.annotations.SerializedName

data class CommodityTag(
    @SerializedName("key")
    val commodityKey : String,
    @Transient
    val bullOrBear : BullOrBear,
    @SerializedName("type")
    val type : StockTypeInfo
){

    /**
     * 為了用於解析欄位用 因為需要序列化成Int型態 所以無法直接在enum上使用SerializedName
     */
    @SerializedName("bullOrBear")
    private val bullOrBearField : Int = bullOrBear.value
}