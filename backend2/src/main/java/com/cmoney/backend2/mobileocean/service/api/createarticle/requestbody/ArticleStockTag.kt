package com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody

data class ArticleStockTag(val stockId: String, val stockTag: StockTag)
enum class StockTag(val value: Int) {
    Bull(1),
    Bear(-1),
    Unchanged(0);

    companion object {
        fun getType(value: Int): StockTag {
            return when (value) {
                0 -> Unchanged
                1 -> {
                    Bull
                }
                -1 -> {
                    Bear
                }
                else -> {
                    Unchanged
                }
            }
        }
    }
}