package com.cmoney.backend2.notification.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager

interface NotificationWeb {

    val manager: GlobalBackend2Manager

    /**
     * 增加到達數
     *
     * @param sn 序號
     * @param pushToken 推播Token
     * @param title 標題
     * @param content 內容
     * @param analyticsLabels 用於統計與分析的labels
     * @param createTime 推播建立時間,
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateArriveCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String> = emptyList(),
        createTime: Long = 0,
        domain: String = manager.getNotificationSettingAdapter().getDomain(),
        url: String = "${domain}NotificationService/Statistics/arrived"
    ): Result<Unit>

    /**
     * 新增訪客Token
     *
     * @param pushToken 推播Token
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateGuestPushToken(
        pushToken: String,
        domain: String = manager.getNotificationSettingAdapter().getDomain(),
        url: String = "${domain}NotificationService/DeviceToken/guest"
    ): Result<Unit>

    /**
     * 新增會員Token
     *
     * @param pushToken 推播Token
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateMemberPushToken(
        pushToken: String,
        domain: String = manager.getNotificationSettingAdapter().getDomain(),
        url: String = "${domain}NotificationService/DeviceToken/member"
    ): Result<Unit>

    /**
     * 增加點擊數
     *
     * @param sn 序號
     * @param pushToken 推播Token
     * @param title 標題
     * @param content 內容
     * @param analyticsLabels 用於統計與分析的labels
     * @param createTime 推播建立時間
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updateClickCount(
        sn: Long,
        pushToken: String,
        title: String,
        content: String,
        analyticsLabels: List<String> = emptyList(),
        createTime: Long = 0,
        domain: String = manager.getNotificationSettingAdapter().getDomain(),
        url: String = "${domain}NotificationService/Statistics/clicked"
    ): Result<Unit>
}