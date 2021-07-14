package com.cmoney.backend2.realtimeaftermarket.service.api.getcommlist


import com.google.gson.annotations.SerializedName

/**
 * 取得商品清單回傳物件
 *
 * @property products 商品清單
 * @property responseCode 回傳碼
 * @constructor Create empty Get comm list response body
 */
data class GetCommListResponseBody(
    @SerializedName("Products")
    val products: List<Product>?,
    @SerializedName("ResponseCode")
    val responseCode: Int?
)