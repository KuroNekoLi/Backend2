package com.cmoney.backend2.customgroup.service.api.addcustomgroup

import com.google.gson.annotations.SerializedName

data class NewCustomGroup(
    @SerializedName("DocNo")
    val docNo: Int?,

    @SerializedName("DocName")
    val docName: String?
)