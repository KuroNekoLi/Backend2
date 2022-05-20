package com.cmoney.backend2.product.service

import com.cmoney.backend2.product.service.api.Product
import com.cmoney.backend2.product.service.api.SaleItem

interface ProductDataProviderWeb {
    /**
     * @param id 銷售代碼
     */
    suspend fun getProductBySalesId(id: Long): Result<Product>

    /**
     * Get first sale item we can find with given subjectId.
     */
    suspend fun getSalesItemBySubjectId(id: Long): Result<SaleItem>
}