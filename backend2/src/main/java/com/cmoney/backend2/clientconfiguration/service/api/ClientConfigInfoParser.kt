package com.cmoney.backend2.clientconfiguration.service.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 * 將[ClientConfigInfo] value轉為物件的工具
 */
object ClientConfigInfoParser {

    @Throws(JsonSyntaxException::class)
    fun getClientConfigType(responseBody: ClientConfigInfo, jsonParser: Gson): ClientConfigType? {
        val key = ConfigKey.getKeyByStringValue(responseBody.configKey ?: return null)
      return  when (key) { // 目前沒有已知需要使用服務之設定
          else -> null
      }
    }
}