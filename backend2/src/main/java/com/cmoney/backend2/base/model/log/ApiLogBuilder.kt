package com.cmoney.backend2.base.model.log

import java.net.URLEncoder

/**
 * 組成[ApiLog]的物件
 */
class ApiLogBuilder {

    private val chartSetName = Charsets.UTF_8.name()

    /**
     * 創建ApiLog
     *
     * @param appId App的Id
     * @param platform 平台
     * @param appVersion App版本
     * @param manufacturer 製造商
     * @param model 手機型號
     * @param osVersion 系統版本
     * @return
     */
    fun create(
        appId: Int,
        platform: Int,
        appVersion: String,
        manufacturer: String,
        model: String,
        osVersion: String
    ): ApiLog {
        return ApiLog(
            appId = appId,
            platform = platform,
            appVersion = URLEncoder.encode(appVersion, chartSetName),
            manufacturer = URLEncoder.encode(manufacturer, chartSetName),
            model = URLEncoder.encode(model, chartSetName),
            osVersion = URLEncoder.encode(osVersion, chartSetName)
        )
    }
}