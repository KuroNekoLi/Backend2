package com.cmoney.backend2.ocean.service.api.checkHasJoinedClub


import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class HasJoinedClubResponseWithError(
    @SerializedName("HasJoined")
    val isJoin: Boolean?
) : IWithError<HasJoinedClubComplete>, CMoneyError() {

    override fun toRealResponse(): HasJoinedClubComplete {
        return HasJoinedClubComplete(isJoin)
    }
}