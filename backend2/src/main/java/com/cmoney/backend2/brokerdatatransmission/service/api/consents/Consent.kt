package com.cmoney.backend2.brokerdatatransmission.service.api.consents

import com.google.gson.annotations.SerializedName

class Consent(
    @SerializedName("brokerId")
    val brokerId: String?,
    @SerializedName("hasSigned")
    val hasSigned: Boolean?
)
