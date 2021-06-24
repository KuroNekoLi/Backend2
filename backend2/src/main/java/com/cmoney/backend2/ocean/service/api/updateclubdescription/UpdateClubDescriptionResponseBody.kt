package com.cmoney.backend2.ocean.service.api.updateclubdescription

import com.google.gson.annotations.SerializedName

data class UpdateClubDescriptionResponseBody(
    @SerializedName("IsSuccess")
    val isSuccess : Boolean?
)