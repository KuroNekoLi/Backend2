package com.cmoney.backend2.customgroup.service.api.addcustomgroup

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class NewCustomGroupWithError(
    @SerializedName("DocNo")
    val docNo: Int?,

    @SerializedName("DocName")
    val docName: String?
): CMoneyError(),
    IWithError<NewCustomGroup> {

    override fun toRealResponse(): NewCustomGroup {
        return NewCustomGroup(
            docNo,
            docName
        )
    }
}