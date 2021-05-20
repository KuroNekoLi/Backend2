package com.cmoney.backend2.portal.service.api.getadditionalinfo

import com.google.gson.annotations.SerializedName

data class CmPortalAddition(

    /**
     * 表示每一欄的資料定義
     */
    @SerializedName("Columns")
    val columns: List<String>?,

    /**
     * 每檔股票的欄位資料
     * 欄位資詳細說明，請參考另一份文件
     */
    @SerializedName("Rows")
    val rows: List<List<String>?>?
)