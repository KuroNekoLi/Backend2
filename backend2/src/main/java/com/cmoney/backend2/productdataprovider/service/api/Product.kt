package com.cmoney.backend2.productdataprovider.service.api

/**
 * 商品資訊
 *
 * @property name 名稱
 * @property price 價格
 * @property originalPrice 原始價格
 * @property productId 商品ID
 * @property authorName 作者名
 * @property displayName 顯示商品名
 * @property displayDesc 顯示商品敘述
 */
data class Product(
    val name: String,
    val price: Double,
    val originalPrice: Double,
    val productId: Long,
    val authorName: String,
    val displayName: String,
    val displayDesc: String
)
