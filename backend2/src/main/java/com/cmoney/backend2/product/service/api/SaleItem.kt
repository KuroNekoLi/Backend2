package com.cmoney.backend2.product.service.api

data class SaleItem(
    val productId: Long,
    val productName: String,
    val saleId: Long,
    val title: String
)
