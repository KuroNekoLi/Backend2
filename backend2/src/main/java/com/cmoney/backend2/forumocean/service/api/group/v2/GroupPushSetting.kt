package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * 社團推播設定
 */
@Keep
data class GroupPushSetting(
    /**
     * 推播種類
     */
    @SerializedName("pushType")
    val pushType: String?
)