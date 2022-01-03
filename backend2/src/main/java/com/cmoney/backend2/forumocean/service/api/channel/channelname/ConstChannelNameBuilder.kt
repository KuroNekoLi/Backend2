package com.cmoney.backend2.forumocean.service.api.channel.channelname

/**
 * ChannelNameBuilder using constant value.
 */
class ConstChannelNameBuilder(private val channelName: String) : IChannelNameBuilder {
    override fun create(): String {
        return channelName
    }
}