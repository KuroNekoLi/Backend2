package com.cmoney.backend2.ocean.service.api.joinclub

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.cmoney.backend2.ocean.service.api.variable.MemberPosition
import com.google.gson.annotations.SerializedName

data class JoinClubResponseBodyWithError(
    @SerializedName("MemberPosition")
    val memberPosition : MemberPosition?
): CMoneyError(), IWithError<JoinClubResponseBody> {
    override fun toRealResponse(): JoinClubResponseBody {
        return JoinClubResponseBody(memberPosition)
    }
}