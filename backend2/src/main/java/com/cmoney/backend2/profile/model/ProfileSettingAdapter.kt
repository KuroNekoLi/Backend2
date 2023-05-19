package com.cmoney.backend2.profile.model

/**
 * 會員服務設定轉接器
 */
interface ProfileSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}