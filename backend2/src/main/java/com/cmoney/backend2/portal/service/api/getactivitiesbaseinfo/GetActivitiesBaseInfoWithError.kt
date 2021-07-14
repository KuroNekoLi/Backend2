package com.cmoney.backend2.portal.service.api.getactivitiesbaseinfo

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

data class GetActivitiesBaseInfoWithError(
    @SerializedName("ActivityBaseInfoList")
    val activityBaseInfoList: List<ActivityBaseInfo>?
) : CMoneyError(),IWithError<GetActivitiesBaseInfo>{
    override fun toRealResponse(): GetActivitiesBaseInfo {
        return GetActivitiesBaseInfo(
            activityBaseInfoList
        )
    }
}