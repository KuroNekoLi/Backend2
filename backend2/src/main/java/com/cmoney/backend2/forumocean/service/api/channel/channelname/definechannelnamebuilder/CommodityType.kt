package com.cmoney.backend2.forumocean.service.api.channel.channelname.definechannelnamebuilder

enum class CommodityType(val text : String) {
    /**
     * 台股
     *
     */
    Stock("Stock"),

    /**
     * 美股
     *
     */
    USStock("USStock"),

    /**
     * 興櫃
     *
     */
    Emerging("Emerging")
}