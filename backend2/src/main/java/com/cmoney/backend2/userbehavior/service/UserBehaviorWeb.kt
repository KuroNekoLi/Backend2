package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.userbehavior.service.api.common.Event

interface UserBehaviorWeb {

    val manager: GlobalBackend2Manager

    /**
     * 上傳報告
     *
     * @param events 事件清單
     * @param processId 用戶使用階段流水號
     * @param appId App編號
     * @param platform 裝置平台
     * @param version 程式版本號
     * @param os 作業系統
     * @param device 裝置型號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun uploadReport(
        events: List<Event>,
        processId: String?,
        appId: Int,
        platform: Int,
        version: String,
        os: String?,
        device: String?,
        domain: String = manager.getUserBehaviorSettingAdapter().getDomain(),
        url: String = "${domain}UserBehaviorLog/Log"
    ): Result<Unit>

}