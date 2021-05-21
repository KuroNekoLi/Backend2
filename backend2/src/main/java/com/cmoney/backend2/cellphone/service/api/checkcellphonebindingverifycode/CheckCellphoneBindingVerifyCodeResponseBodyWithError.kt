package com.cmoney.backend2.cellphone.service.api.checkcellphonebindingverifycode

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數錯誤 101-身分識別有誤 -1-服務尚未初始化 2-驗證碼有誤
 * 3-檢查手機是否重複有誤 4-無法取得會員資料 5-更新手機資料出錯
 */
data class CheckCellphoneBindingVerifyCodeResponseBodyWithError(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(),
    IWithError<CheckCellphoneBindingVerifyCodeResponseBody> {

    override fun toRealResponse(): CheckCellphoneBindingVerifyCodeResponseBody {
        return CheckCellphoneBindingVerifyCodeResponseBody(
            isSuccess
        )
    }
}