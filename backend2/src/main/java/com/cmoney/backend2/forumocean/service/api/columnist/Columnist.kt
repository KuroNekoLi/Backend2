package com.cmoney.backend2.forumocean.service.api.columnist


import com.google.gson.annotations.SerializedName

/**
 * 一個專欄作家用戶
 *
 * @property memberId 專欄作家memberId
 */
data class Columnist(
    @SerializedName("memberId")
    val memberId: Long?
)
