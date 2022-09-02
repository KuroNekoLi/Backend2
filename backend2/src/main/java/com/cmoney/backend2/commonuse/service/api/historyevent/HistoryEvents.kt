package com.cmoney.backend2.commonuse.service.api.historyevent

import com.google.gson.annotations.SerializedName

/**
 * 歷史推播事件清單
 *
 * @property events 事件清單
 * @property pageInfo 分頁資訊
 */
data class HistoryEvents(
    @SerializedName("edges")
    val events: List<Event>?,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo?
) {
    /**
     * 歷史推播事件
     *
     * @property content 事件內容
     */
    data class Event(
        @SerializedName("node")
        val content: Content?
    ) {
        /**
         * 事件內容
         *
         * @property commKey 商品代號
         * @property commName 商品名稱
         * @property body 內文
         * @property link 跳轉連結
         * @property notifyTime 推播時間
         */
        data class Content(
            @SerializedName("commKey")
            val commKey: String?,
            @SerializedName("commName")
            val commName: String?,
            @SerializedName("body")
            val body: String?,
            @SerializedName("link")
            val link: String?,
            @SerializedName("notifyTime")
            val notifyTime: Long?
        )
    }

    /**
     * 分頁資訊
     *
     * @property hasNextPage 是否有下一頁
     * @property endCursor 取下一分頁的 key
     */
    data class PageInfo(
        @SerializedName("hasNextPage")
        val hasNextPage: Boolean?,
        @SerializedName("endCursor")
        val endCursor: String?
    )
}
