package com.cmoney.backend2.ocean.service.api.createclub

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CreateClubResponseBodyWithError (
    @SerializedName("ChannelId")
    val channelId : Long?
): CMoneyError(), IWithError<CreateClubResponseBody> {
    override fun toRealResponse(): CreateClubResponseBody {
        return CreateClubResponseBody(channelId)
    }
}