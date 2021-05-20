package com.cmoney.backend2.cellphone.service.api.unbindcellphone

import com.google.gson.annotations.SerializedName


data class UnbindCellphoneResponseBody(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)