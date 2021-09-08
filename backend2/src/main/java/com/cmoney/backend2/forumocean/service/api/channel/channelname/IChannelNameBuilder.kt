package com.cmoney.backend2.forumocean.service.api.channel.channelname

interface IChannelNameBuilder {
    /**
     * 生成ChannelName
     * 頻道名稱的產生有順序限制  不能換程式碼的前後位置
     */
    fun create() : String
}