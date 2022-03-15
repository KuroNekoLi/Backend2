package com.cmoney.backend2.vtwebapi.service.api.getcardinstancesns

import com.google.gson.annotations.SerializedName

/**
 * @property cardInstanceSns 擁有道具卡實際生成ID清單
 */
data class GetCardInstanceSnsResponseBody(
    @SerializedName("cardInstanceSns")
    val cardInstanceSns: List<Long>?
)
