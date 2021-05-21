package com.cmoney.backend2.customgroup.service.api.getcustomgroupwithorderandlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CustomGroupWithOrderAndListWithError(

    /**
     * 自選股清單
     */
    @SerializedName("Group")
    val group: List<SingleGroupWithOrderAndList>?
): CMoneyError(),
    IWithError<CustomGroupWithOrderAndList> {
    override fun toRealResponse(): CustomGroupWithOrderAndList {
        return CustomGroupWithOrderAndList(
            group
        )
    }
}