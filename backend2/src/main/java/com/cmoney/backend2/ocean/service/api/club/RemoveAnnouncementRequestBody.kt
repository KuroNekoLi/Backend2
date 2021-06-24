package com.cmoney.backend2.ocean.service.api.club

import com.google.gson.annotations.SerializedName

/**
 * 刪除公告RequestBody
 * @property articleId Int
 * @property isPinned Boolean
 * @property appId Int
 * @property guid String
 * @constructor
 */
data class RemoveAnnouncementRequestBody(
    @SerializedName("ArticleId")
    val articleId : Long,
    @SerializedName("IsPinned")
    val isPinned : Boolean,
    @SerializedName("AppId")
    val appId : Int,
    @SerializedName("Guid")
    val guid : String
   
)