package com.cmoney.backend2.notification.service

interface NotificationWeb {
    /**
     * 增加到達數
     */
    suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
    ): Result<Unit>

    /**
     * 增加點擊數
     */
    suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        analyticsId: Long,
        title: String,
        content: String
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