package com.cmoney.backend2.base.extension

import com.google.gson.annotations.SerializedName

data class ComplicatedDao(
    @SerializedName("名稱")
    val name: String?,
    @SerializedName("漲跌幅")
    val upAndDown: Double?,
    @SerializedName("時間")
    val time: Long?,
    @SerializedName("時間2")
    val time2: Long?,
    @SerializedName("是否顯示")
    val isShow: Boolean?
)
