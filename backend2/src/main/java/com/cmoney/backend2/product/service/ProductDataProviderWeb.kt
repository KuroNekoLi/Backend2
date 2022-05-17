package com.cmoney.backend2.product.service

import com.cmoney.backend2.product.service.api.Product

interface ProductDataProviderWeb {
    /**
     * @param id 銷售代碼
     */
    suspend fun getProductBySalesId(id: Long): Result<Product>
}