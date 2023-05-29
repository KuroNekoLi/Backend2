package com.cmoney.backend2.productdataprovider.service.api.getsalesitembysubjectid


import com.google.gson.annotations.SerializedName

data class GetSalesItemBySubjectIdResponseBody(
    @SerializedName("data")
    val `data`: Data?
) {
    data class Data(
        @SerializedName("productInfoSet")
        val productInfoSet: List<ProductInfoSet?>?
    ) {
        data class ProductInfoSet(
            @SerializedName("id")
            val id: Long?,
            @SerializedName("logoPath")
            val logoPath: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("saleInfoSet")
            val saleInfoSet: List<SaleInfoSet?>?,
            @SerializedName("status")
            val status: Int?
        ) {
            data class SaleInfoSet(
                @SerializedName("id")
                val id: Long?,
                @SerializedName("isShow")
                val isShow: Boolean?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("rank")
                val rank: Int?
            )
        }
    }
}