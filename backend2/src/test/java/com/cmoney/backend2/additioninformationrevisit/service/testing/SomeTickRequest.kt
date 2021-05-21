package com.cmoney.backend2.additioninformationrevisit.service.testing

import com.google.gson.annotations.SerializedName

data class SomeTickRequest(
    @SerializedName("CommKey")
    val commKey: String,
    @SerializedName("StartSeqNo")
    val startSeqNo: Int,
    @SerializedName("RequiredQuantity")
    val requiredQuantity: Int
)