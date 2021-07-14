package com.cmoney.backend2.forumocean.service.api.channel.channelname

sealed class UserTagClassification : ConvertChannelName{
    class UserTag(private val tagName : String) : UserTagClassification() {
        override fun getChannelName(): String? {
            return "UserTag.$tagName"
        }
    }

    object None : UserTagClassification() {
        override fun getChannelName(): String? {
            return null
        }
    }
}