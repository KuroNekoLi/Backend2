package com.cmoney.backend2.notification2.service.api.getmainfcm

import com.google.gson.annotations.SerializedName

data class GetMainFCMResponseBody(
    @SerializedName("isNeedPush")
    val isNeedPush: Boolean?
)