package com.cmoney.backend2.forumocean.service.api.channel.channelname

sealed class Product : ConvertChannelName{
    class AppId(private val id : Int) : Product() {
        override fun getChannelName(): String? {
            return id.toString()
        }
    }

    object None : Product() {
        override fun getChannelName(): String? {
            return null
        }
    }
}