package com.cmoney.backend2.chat.service.api.getuserprofile.response

import com.google.gson.annotations.SerializedName

/**
 * Properties 設定檔
 *
 * @property userImageUrl 使用者圖像Url
 * @property userName 使用者名稱
 * @property nameIdentifiers 使用者名稱識別碼
 *
 */
data class Properties(
    @SerializedName("userImageUrl")
    val userImageUrl: String?,
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("@nameIdentifiers")
    val nameIdentifiers: List<NameIdentifier>?
)