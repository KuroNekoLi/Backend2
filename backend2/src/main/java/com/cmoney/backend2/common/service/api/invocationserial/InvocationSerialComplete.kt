package com.cmoney.backend2.common.service.api.invocationserial

import com.google.gson.annotations.SerializedName

/**
 * 開通序號時所回傳的狀態資料
 */
data class InvocationSerialComplete(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?,
    @SerializedName("ResponseCode")
    val responseCode : Int?,
    @SerializedName("ResponseMsg")
    val responseMsg : String?
)