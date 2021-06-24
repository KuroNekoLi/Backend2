package com.cmoney.backend2.ocean.service.api.getmasters

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class GetMastersResponseBodyWithError (
    @SerializedName("Masters")
    val masters: List<Master>?
):CMoneyError(), IWithError<GetMastersResponseBody> {
    override fun toRealResponse(): GetMastersResponseBody {
        return GetMastersResponseBody(masters)
    }
}