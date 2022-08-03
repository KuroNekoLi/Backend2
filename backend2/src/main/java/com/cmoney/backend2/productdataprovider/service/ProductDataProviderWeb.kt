package com.cmoney.backend2.productdataprovider.service

import com.cmoney.backend2.productdataprovider.service.api.Product
import com.cmoney.backend2.productdataprovider.service.api.SaleItem

interface ProductDataProviderWeb {
    /**
     * 取得商品資訊
     * @param id 銷售代碼
     */
    suspend fun getProductBySalesId(id: Long): Result<Product>

    /**
     * Get first sale item we can find with given subjectId.
     */
    suspend fun getSalesItemBySubjectId(subjectId: Long): Result<List<SaleItem>>
}