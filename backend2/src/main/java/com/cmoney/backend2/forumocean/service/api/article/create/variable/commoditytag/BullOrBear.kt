package com.cmoney.backend2.forumocean.service.api.article.create.variable.commoditytag

import com.google.gson.annotations.SerializedName

enum class BullOrBear(val value : Int) {
    @SerializedName("1")
    Bull(1),
    @SerializedName("-1")
    Bear(-1),
    @SerializedName("0")
    None(0)
}