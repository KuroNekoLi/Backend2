package com.cmoney.backend2.additioninformationrevisit.model.settingadapter

import com.cmoney.backend2.additioninformationrevisit.service.AdditionalInformationRevisitWeb

/**
 * 附加資訊設定轉接器。
 */
interface AdditionInformationRevisitSettingAdapter {

    /**
     * 取得網域名稱
     */
    fun getDomain(): String

    /**
     *
     * 取得替換的Path，只提供特定位置的Path。
     * 需要加上 "/"，例如: "AdditionalInformationRevisit/"
     * 如果要個別改變，請使用[AdditionalInformationRevisitWeb]方法的url參數。
     *
     */
    fun getPathName(): String
}