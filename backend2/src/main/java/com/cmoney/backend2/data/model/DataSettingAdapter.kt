package com.cmoney.backend2.data.model

/**
 * Data服務設定轉接器
 */
interface DataSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}