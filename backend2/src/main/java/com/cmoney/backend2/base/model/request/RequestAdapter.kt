package com.cmoney.backend2.base.model.request

interface RequestAdapter {
    /**
     * 取得網域名稱
     */
    fun getDomain(): String

    /**
     * 取得BearerToken
     */
    fun getBearerToken(): String
}