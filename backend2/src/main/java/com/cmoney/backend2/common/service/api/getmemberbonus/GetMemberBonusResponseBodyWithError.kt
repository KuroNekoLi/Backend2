package com.cmoney.backend2.common.service.api.getmemberbonus

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

class GetMemberBonusResponseBodyWithError(
    @SerializedName("Bonus")
    val bonus: Int
) : CMoneyError(), IWithError<GetMemberBonusResponseBody> {
    override fun toRealResponse(): GetMemberBonusResponseBody {
        return GetMemberBonusResponseBody(bonus)
    }
}