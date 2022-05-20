package com.cmoney.backend2.product.service

import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.parseServerException
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.product.service.api.GraphQLQuery
import com.cmoney.backend2.product.service.api.Product
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.withContext

class ProductDataProviderWebImpl(
    private val gson: Gson,
    private val setting: Setting,
    private val service: ProductDataProviderService,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : ProductDataProviderWeb {
    override suspend fun getProductBySalesId(id: Long): Result<Product> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getProductById(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    GraphQLQuery(
                        query = """query (${'$'}id: Long!) {
	saleInfo(id: ${'$'}id) {
		name
		price
		productId
		productInformation {
			name
			shortDesc
		}
        authorInfoSet{
            authorName
        }
	}
}""",
                        variables = JsonObject().apply { addProperty("id", id) }
                    )
                )
                if (response.code() >= 400) {
                    throw response.parseServerException(gson)
                }
                val productJson = JsonParser.parseString(
                    response.body()?.string() ?: "{}"
                ).asJsonObject
                    .get("data").asJsonObject
                    .get("saleInfo").asJsonObject
                val productInfoJson = productJson.get("productInformation").asJsonObject
                Product(
                    productJson.get("name").asString,
                    productJson.get("price").asDouble,
                    productJson.get("productId").asLong,
                    productInfoJson.get("authorInfoSet").asJsonArray.get(0).asJsonObject.get("authorName").asString,
                    productInfoJson.get("name").asString,
                    productInfoJson.get("shortDesc").asString
                )
            }
        }
}