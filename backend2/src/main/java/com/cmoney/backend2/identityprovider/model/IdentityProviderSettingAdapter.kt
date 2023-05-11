package com.cmoney.backend2.identityprovider.model

/**
 * IdentityProvider服務設定轉接器
 */
interface IdentityProviderSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}