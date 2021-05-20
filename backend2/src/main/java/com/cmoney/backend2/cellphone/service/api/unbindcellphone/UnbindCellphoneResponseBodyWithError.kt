package com.cmoney.backend2.cellphone.service.api.unbindcellphone

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UnbindCellphoneResponseBodyWithError(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(), IWithError<UnbindCellphoneResponseBody> {

    override fun toRealResponse(): UnbindCellphoneResponseBody {
        return UnbindCellphoneResponseBody(isSuccess)
    }
}