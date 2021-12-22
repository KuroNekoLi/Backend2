package com.cmoney.backend2.brokerdatatransmission.service.api.transactionhistory

import com.google.gson.annotations.SerializedName

data class FetchTransactionHistoryRequest(
    @SerializedName("Guid")
    val guid: String,
    @SerializedName("Content")
    val content: String
)
