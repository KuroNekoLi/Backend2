package com.cmoney.backend2.portal.service.api.joinactivity

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class JoinActivityWithError(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError(),IWithError<JoinActivity> {
    override fun toRealResponse(): JoinActivity {
        return JoinActivity(
            isSuccess
        )
    }
}