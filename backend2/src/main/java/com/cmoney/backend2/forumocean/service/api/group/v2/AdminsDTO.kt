package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AdminsDTO(
    @SerializedName("managers")
    val managers: List<Int?>?,
    @SerializedName("owner")
    val owner: Int?
)