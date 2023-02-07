package com.cmoney.backend2.productdataprovider.service

import com.cmoney.backend2.base.extension.checkResponseBody
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.model.setting.Setting
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
    private val gson: Gson,
    private val setting: Setting,
    private val service: ProductDataProviderService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : ProductDataProviderWeb {
    override suspend fun getProductBySalesId(id: Long): Result<Product> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getProductByGraphQL(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    GraphQLQuery(
                        query = """query (${'$'}id: Long!) {
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
}""",
                        variables = JsonObject().apply { addProperty("id", id) }
                    )
                )
                val responseBody = response.checkResponseBody(gson)
                val productJson = JsonParser.parseString(
                    responseBody.string()
                ).asJsonObject
                    .get("data").asJsonObject
                    .get("saleInfo").asJsonObject
                val productInfoJson = productJson.get("productInformation").asJsonObject
                Product(
                    productJson.get("name").asString,
                    productJson.get("price").asDouble,
                    productJson.get("itemPrice").asDouble,
                    productJson.get("productId").asLong,
                    productInfoJson.get("authorInfoSet").asJsonArray.get(0).asJsonObject.get("authorName").asString,
                    productInfoJson.get("name").asString,
                    productInfoJson.get("shortDesc").asString
                )
            }
        }

    override suspend fun getSalesItemBySubjectId(subjectId: Long): Result<List<SaleItem>> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getProductByGraphQL(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    GraphQLQuery(
                        query = """query(${'$'}author:Int)
{
    productInfoSet(author:${'$'}author)
    {
        id
        name
        status
        logoPath
        saleInfoSet
        {
            id,
            name,
            isShow,
            rank
        }
    }
}""",
                        variables = JsonObject().apply { addProperty("author", subjectId) }
                    )
                )
                val responseBody = response.checkResponseBody(gson)
                val productObj = JsonParser.parseString(
                    responseBody.string()
                ).asJsonObject
                    .get("data").asJsonObject
                    .get("productInfoSet").asJsonArray
                    .get(0).asJsonObject
                val saleItemObjs = productObj.get("saleInfoSet").asJsonArray
                saleItemObjs.map { it.asJsonObject }
                    .sortedBy { it.get("rank").asInt }
                    .mapNotNull { saleItemObj ->
                        try {
                            SaleItem(
                                productObj.get("id").asLong,
                                productObj.get("name").asString,
                                saleItemObj.get("id").asLong,
                                saleItemObj.get("name").asString,
                                saleItemObj.get("isShow").asBoolean
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
            }
        }
}