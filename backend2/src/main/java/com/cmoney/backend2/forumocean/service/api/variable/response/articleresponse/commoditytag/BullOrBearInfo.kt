package com.cmoney.backend2.forumocean.service.api.variable.response.articleresponse.commoditytag

import com.google.gson.annotations.SerializedName

enum class BullOrBearInfo(val value : Int) {
    @SerializedName("1")
    Bull(1),
    @SerializedName("-1")
    Bear(-1),
    @SerializedName("0")
    None(0)
}