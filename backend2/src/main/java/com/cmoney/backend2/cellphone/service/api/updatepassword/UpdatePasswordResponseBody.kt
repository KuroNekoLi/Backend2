package com.cmoney.backend2.cellphone.service.api.updatepassword

import com.google.gson.annotations.SerializedName


data class UpdatePasswordResponseBody(

    /**
     * 驗證是否成功
     */
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)