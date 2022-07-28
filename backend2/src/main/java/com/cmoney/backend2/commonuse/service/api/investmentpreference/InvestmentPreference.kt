package com.cmoney.backend2.commonuse.service.api.investmentpreference

import com.google.gson.annotations.SerializedName

/**
 * 投資屬性
 *
 * @property id 投資偏好id
 * @property name 投資偏好顯示名稱
 * @property isChosen 是否勾選
 */
data class InvestmentPreference(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("isChosen")
    val isChosen: Boolean
)