package com.cmoney.backend2.productdataprovider.service.api.getproductbysalesid


import com.google.gson.annotations.SerializedName

data class GetProductBySalesIdResponseBody(
    @SerializedName("data")
    val `data`: Data?
) {
    data class Data(
        @SerializedName("saleInfo")
        val saleInfo: SaleInfo?
    ) {
        data class SaleInfo(
            @SerializedName("itemPrice")
            val itemPrice: Double?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("price")
            val price: Double?,
            @SerializedName("productId")
            val productId: Long?,
            @SerializedName("productInformation")
            val productInformation: ProductInformation?
        ) {
            data class ProductInformation(
                @SerializedName("authorInfoSet")
                val authorInfoSet: List<AuthorInfoSet?>?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("shortDesc")
                val shortDesc: String?
            ) {
                data class AuthorInfoSet(
                    @SerializedName("account")
                    val account: String?,
                    @SerializedName("authorName")
                    val authorName: String?,
                    @SerializedName("memberId")
                    val memberId: Long?
                )
            }
        }
    }
}