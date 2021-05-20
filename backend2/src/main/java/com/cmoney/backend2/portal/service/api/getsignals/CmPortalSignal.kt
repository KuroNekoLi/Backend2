package com.cmoney.backend2.portal.service.api.getsignals

import com.google.gson.annotations.SerializedName

data class CmPortalSignal(

    /**
     * 第一個固定為股票代號，後面每一欄是條件名稱
     */
    @SerializedName("Columns")
    val columns: List<String>?,

    /**
     * 根據欄位對應最新的股票篩選狀態 ( 0 : 沒有符合, 1 : 有符合條件)
     * 沒有出現的股票，都是不符合條件
     */
    @SerializedName("Rows")
    val rows: List<List<String>?>?
)