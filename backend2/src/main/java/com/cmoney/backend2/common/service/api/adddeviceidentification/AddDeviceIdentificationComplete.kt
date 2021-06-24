package com.cmoney.backend2.common.service.api.adddeviceidentification

import com.google.gson.annotations.SerializedName

/**
 * @param isSuccess 是否成功
 */
data class AddDeviceIdentificationComplete(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean,
    @SerializedName("ResponseCode")
    val responseCode: Int?,
    @SerializedName("ResponseMsg")
    val responseMsg: String?
)