package com.cmoney.backend2.product.service

import com.cmoney.backend2.product.service.api.Product

interface ProductDataProviderWeb {
    suspend fun getProductById(id: Long): Result<Product>
}