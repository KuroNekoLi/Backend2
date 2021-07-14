package com.cmoney.backend2.cellphone.service.api.login

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class CellphoneLoginWithError(
    @SerializedName("Guid")
    val guid: String?,

    @SerializedName("AuthToken")
    val authToken: String?,

    @SerializedName("MemberPk")
    val memberPk: Int?,

    @SerializedName("IsFirstLogin")
    val isFirstLogin: Boolean?,

    @SerializedName("TrialRemainingSeconds")
    val trialRemainingSeconds: Int?
): CMoneyError(),
    IWithError<CellphoneLogin> {

    override fun toRealResponse(): CellphoneLogin {
        return CellphoneLogin(
            guid, authToken, memberPk, isFirstLogin, trialRemainingSeconds
        )
    }
}