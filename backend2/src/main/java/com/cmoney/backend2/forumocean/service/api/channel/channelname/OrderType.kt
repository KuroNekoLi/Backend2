package com.cmoney.backend2.forumocean.service.api.channel.channelname

sealed class OrderType : ConvertChannelName{
    object Popularity : OrderType() {
        override fun getChannelName(): String? = "Popular1H"
    }

    object None : OrderType(){
        override fun getChannelName(): String? = null
    }
}