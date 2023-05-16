package com.cmoney.backend2.productdataprovider.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.productdataprovider.service.api.GraphQLQuery
import com.cmoney.backend2.productdataprovider.service.api.Product
import com.cmoney.backend2.productdataprovider.service.api.SaleItem
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.withContext

class ProductDataProviderWebImpl(
    override val manager: GlobalBackend2Manager,
    private val gson: Gson,
    private val service: ProductDataProviderService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ProductDataProviderWeb {
    override suspend fun getProductBySalesId(
        id: Long,
        domain: String,
        url: String
    ): Result<Product> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getProductBySalesId(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                GraphQLQuery(
                    query = """
                        query (${'$'}id: Long!) {
                            saleInfo(id: ${'$'}id) {
                                name
                                price
                                itemPrice
                                productId
                                productInformation {
                                    name
                                    shortDesc
                                    authorInfoSet {
                                        authorName
                                        memberId
                                        account
                                    }
                                }
                            }
                        }""".trimIndent(),
                    variables = JsonObject().apply { addProperty("id", id) }
                )
            )
            val responseBody = response.checkResponseBody(gson)
            val data = responseBody.data
            val saleInfo = data?.saleInfo
            if (data == null || saleInfo == null) {
                throw IllegalArgumentException("找不到符合的商品")
            }
            val product = Product(
                name = data.saleInfo.name.orEmpty(),
                price = data.saleInfo.price ?: 0.0,
                originalPrice = data.saleInfo.itemPrice ?: 0.0,
                productId = data.saleInfo.productId ?: 0L,
                authorName = data.saleInfo.productInformation?.authorInfoSet?.getOrNull(0)?.authorName.orEmpty(),
                displayName = data.saleInfo.productInformation?.name.orEmpty(),
                displayDesc = data.saleInfo.productInformation?.shortDesc.orEmpty()
            )
            product
        }
    }

    override suspend fun getSalesItemBySubjectId(
        subjectId: Long,
        domain: String,
        url: String
    ): Result<List<SaleItem>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getSalesItemBySubjectId(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                GraphQLQuery(
                    query = """
                        query(${'$'}author:Int) {
                            productInfoSet(author:${'$'}author) {
                                id
                                name
                                status
                                logoPath
                                saleInfoSet {
                                    id,
                                    name,
                                    isShow,
                                    rank
                                }
                            }
                        }""".trimIndent(),
                    variables = JsonObject().apply { addProperty("author", subjectId) }
                )
            )
            val responseBody = response.checkResponseBody(gson)
            val data = responseBody.data
            val productInfoSet = data?.productInfoSet?.getOrNull(0)
                ?: return@runCatching emptyList<SaleItem>()
            val items = productInfoSet.saleInfoSet.orEmpty()
                .mapNotNull { saleInfoSet ->
                    saleInfoSet?.id
                    SaleItem(
                        productId = productInfoSet.id ?: return@mapNotNull null,
                        productName = productInfoSet.name ?: return@mapNotNull null,
                        saleId = saleInfoSet?.id ?: return@mapNotNull null,
                        title = saleInfoSet.name ?: return@mapNotNull null,
                        isShown = saleInfoSet.isShow ?: return@mapNotNull null,
                    )
                }
            items
        }
    }
}