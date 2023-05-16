package com.cmoney.backend2.ocean.model

/**
 * Ocean服務設定轉接器
 */
interface OceanSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}