package com.cmoney.backend2.ocean.service.api.getrecommendclubs

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetRecommendClubsResponseBodyWithError(
    @SerializedName("Clubs")
    val clubs: List<Club?>?,
    @SerializedName("UpdatedInSeconds")
    val updatedInSeconds: Int?
): CMoneyError(), IWithError<GetRecommendClubsResponseBody> {
    override fun toRealResponse(): GetRecommendClubsResponseBody {
        return GetRecommendClubsResponseBody(clubs, updatedInSeconds)
    }
}