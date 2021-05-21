package com.cmoney.backend2.dtno.service.api.getLatestBasicInfo

import com.google.gson.annotations.SerializedName

data class BasicInfoResponseBody(

    @SerializedName("BasicInfo")
    val basicInfoList: List<BasicInfoData>?
)