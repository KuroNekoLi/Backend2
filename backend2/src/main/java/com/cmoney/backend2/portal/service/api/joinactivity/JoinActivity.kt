package com.cmoney.backend2.portal.service.api.joinactivity
import com.google.gson.annotations.SerializedName


data class JoinActivity(
    @SerializedName("IsSuccess")
    val isSuccess: Boolean?
)