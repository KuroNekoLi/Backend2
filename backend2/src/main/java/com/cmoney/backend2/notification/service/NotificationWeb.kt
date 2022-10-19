package com.cmoney.backend2.notification.service

interface NotificationWeb {
    /**
     * 增加到達數
     */
    @Deprecated("Will remove in 2023/1/1", replaceWith = ReplaceWith("this.updateArriveCount(sn, pushToken, title, content, emptyList(), 0)"))
    suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
    ): Result<Unit>

    /**
     * 增加到達數
     *
     * @property sn 序號
     * @property pushToken 推播Token
     * @property title 標題
     * @property content 內容
     * @property analyticsLabels 用於統計與分析的labels
     * @property createTime 推播建立時間
     */
    suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String> = emptyList(),
        createTime: Long = 0
    ): Result<Unit>

    /**
     * 增加點擊數
     */
    @Deprecated("Will remove in 2023/1/1", replaceWith = ReplaceWith("this.updateClickCount(sn, pushToken, title, content, emptyList(), 0)"))
    suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
    ): Result<Unit>

    /**
     * 增加點擊數
     *
     * @property sn 序號
     * @property pushToken 推播Token
     * @property title 標題
     * @property content 內容
     * @property analyticsLabels 用於統計與分析的labels
     * @property createTime 推播建立時間
     */
    suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String> = emptyList(),
        createTime: Long = 0
    ): Result<Unit>

    /**
     * 新增訪客Token
     */
    suspend fun updateGuestPushToken(pushToken: String): Result<Unit>

    /**
     * 新增會員Token
     */
    suspend fun updateMemberPushToken(pushToken: String): Result<Unit>
}