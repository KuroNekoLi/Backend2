package com.cmoney.backend2.productdataprovider.service.api

/**
 * 銷售項目
 *
 * @property productId 商品ID
 * @property productName 商品名稱
 * @property saleId 銷售代號
 * @property title 銷售名稱
 */
data class SaleItem(
    val productId: Long,
    val productName: String,
    val saleId: Long,
    val title: String
)
