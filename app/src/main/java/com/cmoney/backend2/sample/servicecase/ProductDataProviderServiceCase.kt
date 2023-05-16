package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.productdataprovider.service.ProductDataProviderWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class ProductDataProviderServiceCase : ServiceCase {

    private val web by inject<ProductDataProviderWeb>()

    override suspend fun testAll() {
        web.getProductBySalesId(id = 5038).logResponse(TAG)
        web.getSalesItemBySubjectId(829852).logResponse(TAG)
    }

    companion object {
        private const val TAG = "ProductDataProvider"
    }
}