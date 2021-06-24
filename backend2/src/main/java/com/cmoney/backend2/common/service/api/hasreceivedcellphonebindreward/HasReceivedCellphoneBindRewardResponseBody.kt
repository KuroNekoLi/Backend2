package com.cmoney.backend2.common.service.api.hasreceivedcellphonebindreward

import com.google.gson.annotations.SerializedName

data class HasReceivedCellphoneBindRewardResponseBody(
    /**
     * 是否已領過獎勵
     */
    @SerializedName("HasReceived")
    val hasReceived : Boolean?
)