package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 權限物件
 */
@Keep
data class Auth(
    /**
     * 是否有權限
     */
    @SerializedName("canRead")
    val canRead: Boolean?
)
