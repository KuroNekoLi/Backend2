package com.cmoney.backend2.cellphone.service.api.getaccountinfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * Error Code
 * 9001-參數有誤 101-身分識別有誤 -1-服務尚未初始化 2-無法取得會員資料 3-無法取得登入帳號
 */
data class AccountInfoWithError(

    /**
     * 會員帳號(沒綁定帳號為空字串)
     */
    @SerializedName("Account")
    val account: String?,

    /**
     * 手機國碼加號碼 E164 格式(沒綁定為空字串)
     */
    @SerializedName("Cellphone")
    val cellphone: String?,

    /**
     * 會員收件信箱(沒綁定帳號為空字串)
     */
    @SerializedName("ContactEmail")
    val contactEmail: String?,

    /**
     * FB信箱(若FB帳號沒驗證信箱為空字串)
     */
    @SerializedName("FbMail")
    val fbMail: String?,

    /**
     * FB帳號網址(沒綁定為空字串)
     */
    @SerializedName("FbUrl")
    val fbUrl: String?,

    /**
     * 是否有綁定登入信箱(帳號)
     */
    @SerializedName("HasBindingAccount")
    val hasBindingAccount: Boolean?,

    /**
     * 是否有尚未驗證的收件信箱
     */
    @SerializedName("HasUnverifiedContactEmail")
    val hasUnverifiedContactEmail: Boolean?,

    /**
     * 註冊時間 UnixTIme(秒)
     */
    @SerializedName("RegisterTime")
    val registerTime: Int?
) : CMoneyError(),
    IWithError<AccountInfo> {

    override fun toRealResponse(): AccountInfo {
        return AccountInfo(
            account,
            cellphone,
            contactEmail,
            fbMail,
            fbUrl,
            hasBindingAccount,
            hasUnverifiedContactEmail,
            registerTime
        )
    }
}