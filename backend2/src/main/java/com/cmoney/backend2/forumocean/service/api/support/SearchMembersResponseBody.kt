package com.cmoney.backend2.forumocean.service.api.support

import com.google.gson.annotations.SerializedName

/**
 * 以關鍵搜尋用戶ResponseBody
 *
 * @property memberId memberId
 * @property nickName 暱稱
 * @property image 頭像
 * @property fans 粉絲數量
 */
data class SearchMembersResponseBody(
    @SerializedName("memberId")
    val memberId: Long?,
    @SerializedName("nickName")
    val nickName: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("fans")
    val fans: Int?,
)
