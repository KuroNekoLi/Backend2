package com.cmoney.backend2.profile.service.api.getusergraphqlinfo

import com.google.gson.annotations.SerializedName

/**
 * 取得使用者GraphQLInfo的資料
 *
 * @property memberIds 要查詢的使用者ID
 * @property fields 要查詢的欄位資訊，目前因為有授權，僅可查詢到 "{ id nickname image level badges}"
 */
data class GetUserGraphQLInfoRequestBody (
    @SerializedName("memberIds")
    val memberIds: List<Long>,
    @SerializedName("fields")
    val fields: String
)