package com.cmoney.backend2.portal.service.api

import com.google.gson.annotations.SerializedName

/**
 * 會員狀態 -1: 沒有猜 0:猜空 1:猜多
 */
enum class GuessBullBearStatus{
    @SerializedName("-1")
    NotJoin,
    @SerializedName("0")
    GuessBear,
    @SerializedName("1")
    GuessBull
}