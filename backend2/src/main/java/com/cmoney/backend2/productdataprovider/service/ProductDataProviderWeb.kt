package com.cmoney.backend2.productdataprovider.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.productdataprovider.service.api.Product
import com.cmoney.backend2.productdataprovider.service.api.SaleItem

interface ProductDataProviderWeb {

    val manager: GlobalBackend2Manager

    /**
     * 取得商品資訊
     *
     * @param id 銷售代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getProductBySalesId(
        id: Long,
        domain: String = manager.getProductDataProviderSettingAdapter().getDomain(),
        url: String = "${domain}ProductDataProvider/Product/GraphQLQuery"
    ): Result<Product>

    /**
     * 取得販售商品
     *
     * @param subjectId 主題代碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getSalesItemBySubjectId(
        subjectId: Long,
        domain: String = manager.getProductDataProviderSettingAdapter().getDomain(),
        url: String = "${domain}ProductDataProvider/Product/GraphQLQuery"
    ): Result<List<SaleItem>>
}