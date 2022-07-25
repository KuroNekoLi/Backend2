package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AdminsDTO(
    @SerializedName("managers")
    val managers: List<Long>?,
    @SerializedName("owner")
    val owner: Long?
)