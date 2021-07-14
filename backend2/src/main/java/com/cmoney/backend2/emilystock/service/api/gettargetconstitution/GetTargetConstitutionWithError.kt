package com.cmoney.backend2.emilystock.service.api.gettargetconstitution

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetTargetConstitutionWithError(
    @SerializedName("Response")
    val response: Constitution?
) : IWithError<GetTargetConstitution>, CMoneyError() {

    override fun toRealResponse(): GetTargetConstitution {
        return GetTargetConstitution(
            response = response
        )
    }
}