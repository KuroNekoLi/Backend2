package com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorder

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CustomGroupWithOrderWithError(
    @SerializedName("Group")
    val group: List<SingleGroupWithOrder>?
): CMoneyError(),
    IWithError<CustomGroupWithOrder> {

    override fun toRealResponse(): CustomGroupWithOrder {
        return CustomGroupWithOrder(group)
    }
}