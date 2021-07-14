package com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("AreaId")
    val areaId: Int?,
    @SerializedName("ProductInfos")
    val productInfos: List<ProductInfo>?
)