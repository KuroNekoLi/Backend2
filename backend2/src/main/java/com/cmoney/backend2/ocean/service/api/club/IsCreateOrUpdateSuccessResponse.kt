package com.cmoney.backend2.ocean.service.api.club

import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.response.error.IWithError
import com.google.gson.annotations.SerializedName

/**
 * 創建或更新是否成功 api回應物件
 * @property isSuccess Boolean
 * @constructor
 */
data class IsCreateOrUpdateSuccessResponse(
    @SerializedName(value = "IsSuccess" , alternate = ["isSuccess" , "issuccess" , "Issuccess"])
    val isSuccess : Boolean
)

/**
 * 可以解析Error的回應物件
 * @property isSuccess Boolean
 * @constructor
 */
data class IsCreateOrUpdateSuccessResponseWithError(
    @SerializedName(value = "IsSuccess" , alternate = ["isSuccess" , "issuccess" , "Issuccess"])
    val isSuccess : Boolean
): CMoneyError() , IWithError<IsCreateOrUpdateSuccessResponse> {
    override fun toRealResponse(): IsCreateOrUpdateSuccessResponse {
        return IsCreateOrUpdateSuccessResponse(isSuccess)
    }
}