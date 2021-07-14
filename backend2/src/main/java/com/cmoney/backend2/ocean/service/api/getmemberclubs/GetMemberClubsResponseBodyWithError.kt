package com.cmoney.backend2.ocean.service.api.getmemberclubs

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetMemberClubsResponseBodyWithError(
    @SerializedName("Clubs")
    val clubs: List<Club?>?
): CMoneyError(), IWithError<GetMemberClubsResponseBody> {
    override fun toRealResponse(): GetMemberClubsResponseBody {
        return GetMemberClubsResponseBody(clubs)
    }
}