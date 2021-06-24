package com.cmoney.backend2.mobileocean.service.api.createarticle.requestbody

import java.io.File

data class ArticleAppendParam(
    /**
     * 股票代號
     */
    val stockId : String? = null,

    /**
     * 股票關聯清單( 多空: 看多 1, 平盤 0, 看空 -1 )
     * Ex. 2330:0,1101:-1 (格式 : StockId:多空,StockId:多空)
     */
    val mentionTag : List<ArticleStockTag>? = null,

    /**
     * 上傳的圖檔(server限制暫時是4Mb,不縮圖不限長寬)
     */
    val image : List<File>? = null,

    /**
     * 股票推圖路徑
     */
    val stockImage : String? = null,

    /**
     * Youtube影片完整連結網址
     */
    val videoUrl : String? = null)