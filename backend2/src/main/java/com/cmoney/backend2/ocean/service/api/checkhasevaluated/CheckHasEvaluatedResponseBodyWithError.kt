package com.cmoney.backend2.ocean.service.api.checkhasevaluated

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CheckHasEvaluatedResponseBodyWithError (
    @SerializedName("HasEvaluated")
    val hasEvaluated: Boolean?
): CMoneyError(), IWithError<CheckHasEvaluatedResponseBody> {
    override fun toRealResponse(): CheckHasEvaluatedResponseBody {
        return CheckHasEvaluatedResponseBody(
            hasEvaluated
        )
    }
}