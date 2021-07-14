package com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist


import com.google.gson.annotations.SerializedName

/**
 * 商品
 *
 * @property areaId 區域範圍編號
 * @property productInfos 商品明細清單
 * @constructor Create empty Product
 */
data class Product(
    @SerializedName("AreaId")
    val areaId: Int?,
    @SerializedName("ProductInfos")
    val productInfos: List<ProductInfo>?
)