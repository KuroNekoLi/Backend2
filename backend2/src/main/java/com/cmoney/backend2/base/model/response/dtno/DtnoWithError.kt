package com.cmoney.backend2.base.model.response.dtno

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * 有錯誤的Dtno物件
 *
 * @param title 服務回來資料表(table)的各個Title名稱
 * @param data 服務回來資料表(table)的各個Row資料
 */
data class DtnoWithError(
    @SerializedName("Title")
    val title: List<String>?,
    @SerializedName("Data")
    val data: List<List<String>?>?
) : CMoneyError(),
    IWithError<DtnoData> {

    override fun toRealResponse(): DtnoData {
        return DtnoData(
            title = title,
            data = data
        )
    }
}