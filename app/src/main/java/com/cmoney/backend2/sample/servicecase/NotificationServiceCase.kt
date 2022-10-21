package com.cmoney.backend2.sample.servicecase

import com.cmoney.backend2.notification.service.NotificationWeb
import com.cmoney.backend2.sample.extension.logResponse
import org.koin.core.component.inject

class NotificationServiceCase : ServiceCase {

    private val notificationWeb by inject<NotificationWeb>()
    private val pushToken = "自己填入合法的"

    override suspend fun testAll() {
        notificationWeb.updateArriveCount(
            sn = 1,
            pushToken = pushToken,
            title = "title",
            content = "content",
            analyticsLabels = emptyList(),
            createTime = 0
        ).logResponse(TAG)
        notificationWeb.updateClickCount(
            sn = 1,
            pushToken = pushToken,
            title = "title",
            content = "content",
            analyticsLabels = emptyList(),
            createTime = 0
        ).logResponse(TAG)
        notificationWeb.updateGuestPushToken(
            pushToken = pushToken
        ).logResponse(TAG)
        notificationWeb.updateMemberPushToken(
            pushToken = pushToken
        ).logResponse(TAG)
    }

    companion object {
        private const val TAG = "Notification"
    }
}