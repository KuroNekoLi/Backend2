package com.cmoney.backend2.cellphone.service.api.checkcellphonebindingverifycode

import com.google.gson.annotations.SerializedName


data class CheckCellphoneBindingVerifyCodeResponseBody(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)