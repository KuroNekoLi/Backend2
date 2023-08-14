package com.cmoney.backend2.crm.model

/**
 * CRM設定轉接器
 */
interface CrmSettingAdapter {

    /**
     * 取得網域名稱
     *
     * @return 網域名稱
     */
    fun getDomain(): String
}