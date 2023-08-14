package com.cmoney.backend2.cmtalk.model

/**
 * CMTalk 服務設定轉接器
 */
interface CMTalkSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}