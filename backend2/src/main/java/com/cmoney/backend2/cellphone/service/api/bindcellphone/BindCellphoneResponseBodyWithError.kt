package com.cmoney.backend2.cellphone.service.api.bindcellphone

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * Error
 * 9001-參數有誤 101-身分識別有誤 -1-服務尚未初始化 2-檢查手機是否重複有誤 3-無法取得會員資料
 * 4-此會員已綁定手機 5-輸入手機已被其他會員註冊 6-取得驗證碼失敗 7-發送驗證碼簡訊失敗
 */
data class BindCellphoneResponseBodyWithError(

    /**
     * 驗證碼有效時間(秒)
     */
    @SerializedName("VerifyCodeDuration")
    val verifyCodeDuration: Int?,

    /**
     * 重送驗證碼間隔時間(秒)
     */
    @SerializedName("VerifyCodeResendInterval")
    val verifyCodeResendInterval: Int?
): CMoneyError(), IWithError<BindCellphoneResponseBody> {

    override fun toRealResponse(): BindCellphoneResponseBody {
        return BindCellphoneResponseBody(verifyCodeDuration, verifyCodeResendInterval)
    }
}