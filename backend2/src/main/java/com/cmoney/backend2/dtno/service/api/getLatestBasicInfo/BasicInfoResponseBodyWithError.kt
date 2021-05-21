package com.cmoney.backend2.dtno.service.api.getLatestBasicInfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class BasicInfoResponseBodyWithError (

    @SerializedName("BasicInfo")
    val basicInfoList: List<BasicInfoData>?

): CMoneyError(),
    IWithError<BasicInfoResponseBody> {

    override fun toRealResponse(): BasicInfoResponseBody {
        return BasicInfoResponseBody(
            basicInfoList = basicInfoList
        )
    }
}