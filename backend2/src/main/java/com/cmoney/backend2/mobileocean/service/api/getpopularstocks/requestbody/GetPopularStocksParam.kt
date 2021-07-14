package com.cmoney.backend2.mobileocean.service.api.getpopularstocks.requestbody

data class GetPopularStocksParam(
    val fetchCount: Int,
    /**
     * 取得更多資訊，傳入 1 .取得最熱或最新文章
     */
    val needMoreInfo: Int)