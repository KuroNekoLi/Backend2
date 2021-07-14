package com.cmoney.backend2.customgroup.service.api.getcustomgroupandlist

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CustomGroupAndListWithError(
    @SerializedName("Group")
    val group: List<SingleGroupAndList>?
): CMoneyError(),
    IWithError<CustomGroupAndList> {

    override fun toRealResponse(): CustomGroupAndList {
        return CustomGroupAndList(
            group
        )
    }
}