package com.cmoney.backend2.base.model.log

import com.google.gson.annotations.SerializedName
import java.net.URLEncoder

/**
 * cmoneyapi-trace-context的紀錄，每隻都需要使用。
 *
 * @property appId App的Id
 * @property platform 平台
 * @property appVersion App版本
 * @property manufacturer 製造商
 * @property model 手機型號
 * @property osVersion 系統版本
 */
class ApiLog private constructor(
    @SerializedName("appId")
    val appId : Int,
    @SerializedName("platform")
    val platform: Int,
    @SerializedName("appVersion")
    val appVersion: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("osVersion")
    val osVersion: String
) {
    companion object {

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
                appVersion = URLEncoder.encode(appVersion, Charsets.UTF_8.name()),
                manufacturer = URLEncoder.encode(manufacturer, Charsets.UTF_8.name()),
                model = URLEncoder.encode(model, Charsets.UTF_8.name()),
                osVersion = URLEncoder.encode(osVersion, Charsets.UTF_8.name())
            )
        }
    }
}