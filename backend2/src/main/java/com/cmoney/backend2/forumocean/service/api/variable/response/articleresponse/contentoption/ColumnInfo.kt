package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.contentoption

/**
 * 專欄文章資訊
 */
interface ColumnInfo {
    /**
     * 授權狀態
     */
    val authType: String?

    /**
     * pCoin解鎖所需p幣
     */
    val pCoin: Long?

    /**
     * 多少人有兌換
     */
    val exchangeCount: Long?

    /**
     * 專欄文章類別
     */
    val articleType: String?
}