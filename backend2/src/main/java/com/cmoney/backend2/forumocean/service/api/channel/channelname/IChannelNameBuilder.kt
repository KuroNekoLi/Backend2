package com.cmoney.backend2.forumocean.service.api.channel.channelname

interface IChannelNameBuilder {
    companion object{
        /**
         * 頻道名稱清單轉成request body字串的方法 用逗點分隔  但不確定為何單用 "," 會導致500 使用", " 就沒問題
         */
        fun List<IChannelNameBuilder>.createChannelNameList() : String{
            return joinToString() { it.create() }
        }
    }
    /**
     * 生成ChannelName
     * 頻道名稱的產生有順序限制  不能換程式碼的前後位置
     */
    fun create() : String
}