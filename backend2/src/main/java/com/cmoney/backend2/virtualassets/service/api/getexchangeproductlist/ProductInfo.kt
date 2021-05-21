package com.cmoney.backend2.virtualassets.service.api.getexchangeproductlist

data class ProductInfo(
    /**
     * 兌換商品編號
     */
    val exchangeId: Long?,

    /**
     * 花費購物金
     */
    val bonus: Int?,

    /**
     * 兌換商品名稱
     */
    val name: String?,

    /**
     * 兌換商品圖片網址
     */
    val imageLink: String?,

    /**
     * 商品網頁連結
     */
    val productLink: String?,

    /**
     * 兌換條件
     */
    val conditions: Map<String, String>?,

    /**
     * 額外資訊
     */
    val extraInfo: Map<String, String>?
)