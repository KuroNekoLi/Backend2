package com.cmoney.backend2.clientconfiguration.service.api

import com.google.gson.annotations.SerializedName

/**
 * 客端配置資料
 * @property configKey 取得資料的類型
 * @property configValue 對應資料的json字串
 *
 * @see [ClientConfigInfoParser]  解析configValue工具
 */
data class ClientConfigInfo(
    @SerializedName("configKey")
    val configKey:String?,
    @SerializedName("configValue")
    val configValue:String?
)