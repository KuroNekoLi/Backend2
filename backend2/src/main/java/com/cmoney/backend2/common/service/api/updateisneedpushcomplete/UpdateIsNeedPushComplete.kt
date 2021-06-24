package com.cmoney.backend2.common.service.api.updateisneedpushcomplete

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.google.gson.annotations.SerializedName

data class UpdateIsNeedPushComplete (
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
): CMoneyError()