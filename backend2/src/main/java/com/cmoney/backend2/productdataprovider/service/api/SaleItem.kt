package com.cmoney.backend2.productdataprovider.service.api

/**
 * 銷售項目
 */
data class SaleItem(
    /**
     * 商品ID
     */
    val productId: Long,
    /**
     * 商品名稱
     */
    val productName: String,
    /**
     * 銷售代號
     */
    val saleId: Long,
    /**
     * 銷售名稱
     */
    val title: String
)
