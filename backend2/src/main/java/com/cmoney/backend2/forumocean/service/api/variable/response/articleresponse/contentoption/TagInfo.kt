package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

import com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag.CommodityTagInfo


/**
 * 請加入以下欄位
 * @SerializedName("CommodityTags")
 * val commodityTags: List<CommodityTag>?
 */
interface TagInfo {
    /**
     * 股票Tag資訊
     */
    val commodityTags: List<CommodityTagInfo>?
}