package com.cmoney.backend2.customgroup.service.api.getcustomlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CustomListWithError(
    @SerializedName("Stocks")
    val stocks: List<String>?
): CMoneyError(),
    IWithError<CustomList> {

    override fun toRealResponse(): CustomList {
        return CustomList(
            stocks
        )
    }
}