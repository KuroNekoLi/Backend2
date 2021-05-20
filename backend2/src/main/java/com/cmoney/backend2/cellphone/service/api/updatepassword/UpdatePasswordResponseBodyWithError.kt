package com.cmoney.backend2.cellphone.service.api.updatepassword

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponseBodyWithError(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(), IWithError<UpdatePasswordResponseBody> {

    override fun toRealResponse(): UpdatePasswordResponseBody {
        return UpdatePasswordResponseBody(isSuccess)
    }
}