package com.cmoney.backend2.customgroup.service.api.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 自選股類型
 */
sealed class CustomGroupType(val name: String) : Parcelable {
    /**
     * 全部
     */
    @Parcelize
    object All : CustomGroupType("all")

    /**
     * 個股
     */
    @Parcelize
    object Stock : CustomGroupType("stock")

    /**
     * 券商
     */
    @Parcelize
    object Broker : CustomGroupType("broker")

    /**
     * 權證
     */
    @Parcelize
    object Warrant : CustomGroupType("warrant")

    /**
     * 美股
     */
    @Parcelize
    object UsStock : CustomGroupType("ustock")

    /**
     * 債券
     */
    @Parcelize
    object Bond : CustomGroupType("bond")
}