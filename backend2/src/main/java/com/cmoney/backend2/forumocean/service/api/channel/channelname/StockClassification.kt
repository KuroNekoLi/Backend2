package com.cmoney.backend2.forumocean.service.api.channel.channelname

sealed class StockClassification : ConvertChannelName{
    class Stock(private val stockId : String?) : StockClassification() {
        override fun getChannelName(): String? {
            return if (stockId != null){
                "Stock.$stockId"
            }else{
                "Stock"
            }
        }
    }

    object None : StockClassification() {
        override fun getChannelName(): String? {
            return null
        }
    }
}