package com.cmoney.backend2.clientconfiguration.service.api

import com.google.gson.annotations.SerializedName

/**
 * 取得客端配置 ResponseBody
 * @property configs 配置資料列表
 */
data class ClientConfigResponseBody (
    @SerializedName("configs")
    val configs:List<ClientConfigInfo>
)