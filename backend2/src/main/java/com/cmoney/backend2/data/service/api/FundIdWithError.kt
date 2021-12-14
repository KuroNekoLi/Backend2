package com.cmoney.backend2.data.service.api

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class FundIdWithError(
    @SerializedName(value = "state")
    val state: Int? = 0,
    @SerializedName(value = "title", alternate = ["Title"])
    val title: List<String>? = null,
    @SerializedName(value = "data", alternate = ["Data"])
    val data: List<List<String>>? = null
) : CMoneyError(), IWithError<FundIdData> {

    override fun toRealResponse() = FundIdData(title, data)

    companion object {
        const val STATE_FAIL = 0
        const val STATE_SUCCESS = 1
        const val STATE_EMPTY = 2
        const val STATE_TIMEOUT = 3
    }

}
