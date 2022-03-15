package com.cmoney.backend2.vtwebapi.service.api.createaccount

import com.google.gson.annotations.SerializedName

/**
 * @property type 創建帳號類型 現股：1 期權：2
 * @property isn 使用的道具卡卡號，沒有道具卡填0(免費創建)
 */
data class CreateAccountRequestBody(
    @SerializedName("type")
    val type: Int,
    @SerializedName("isn")
    val isn: Long
)
