package com.cmoney.backend2.clientconfiguration.service.api

/**
 * config request key的列舉
 */
enum class ConfigKey(val stringValue: String) {
    // 目前沒有已知需要使用服務之設定
    ;

    companion object {
        fun getKeyByStringValue(stringValue: String): ConfigKey? {
            for (key in values()) {
                if (key.stringValue == stringValue) {
                    return key
                }
            }
            return null
        }
    }
}