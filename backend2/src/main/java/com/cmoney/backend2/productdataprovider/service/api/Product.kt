package com.cmoney.backend2.productdataprovider.service.api

/**
 * 商品資訊
 */
data class Product(
    /**
     * 名稱
     */
    val name: String,
    /**
     * 價格
     */
    val price: Double,

    /**
     * 原始價格
     */
    val originalPrice: Double,
    /**
     * 商品ID
     */
    val productId: Long,
    /**
     * 作者名
     */
    val authorName: String,
    /**
     * 顯示商品名
     */
    val displayName: String,

    /**
     * 顯示商品敘述
     */
    val displayDesc: String
)
