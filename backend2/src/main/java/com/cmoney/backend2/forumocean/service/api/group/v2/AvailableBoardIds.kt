package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AvailableBoardIds(
    @SerializedName("ids")
    val ids: List<Long>?
)
