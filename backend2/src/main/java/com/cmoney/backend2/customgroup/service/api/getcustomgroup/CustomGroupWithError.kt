package com.cmoney.backend2.customgroup.service.api.getcustomgroup

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CustomGroupWithError(
    @SerializedName("Group")
    val group: List<SingleGroup>?
): CMoneyError(),
    IWithError<CustomGroup> {

    override fun toRealResponse(): CustomGroup {
        return CustomGroup(
            group
        )
    }
}