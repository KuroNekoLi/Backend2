package com.cmoney.backend2.cellphone.service.api.checkverifycode

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤  -1-服務尚未初始化 2-驗證碼有誤
 */
data class CellphoneCheckVerifyCodeWithError(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(),
    IWithError<CellphoneCheckVerifyCode> {

    override fun toRealResponse(): CellphoneCheckVerifyCode {
        return CellphoneCheckVerifyCode(
            isSuccess
        )
    }
}