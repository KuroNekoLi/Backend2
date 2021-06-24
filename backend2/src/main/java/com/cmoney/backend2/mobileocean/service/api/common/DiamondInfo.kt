package com.cmoney.backend2.mobileocean.service.api.common


import com.google.gson.annotations.SerializedName

/**
 * 鑽石資料
 *
 * @property intervalOfDiamondLevel 鑽石的級距值
 * @property level 鑽石等級
 * @property quantity 鑽石數量
 */
data class DiamondInfo(
    @SerializedName("IntervalOfDiamondLevel")
    val intervalOfDiamondLevel: Int?,
    @SerializedName("Level")
    val level: Int?,
    @SerializedName("Quantity")
    val quantity: Int?
)