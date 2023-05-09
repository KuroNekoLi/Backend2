package com.cmoney.backend2.chat.service.api.getuserprofile.response

import com.google.gson.annotations.SerializedName

/**
 * 使用者檔案回應Body
 *
 * @property id 使用者Id
 * @property properties 使用者設定的參數
 *
 */
data class UserProfileResponseBody(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("properties")
    val properties: Properties?
)