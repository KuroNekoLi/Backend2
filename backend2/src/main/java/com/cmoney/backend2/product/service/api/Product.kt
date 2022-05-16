package com.cmoney.backend2.product.service.api

data class Product(
    val name: String,
    val price: Double,
    val productId: Long,
    val displayName: String,
    val displayDesc: String
)
