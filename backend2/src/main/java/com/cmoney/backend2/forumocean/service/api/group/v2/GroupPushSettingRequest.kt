package com.cmoney.backend2.forumocean.service.api.group.v2


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/**
 * GroupPushSetting 請求
 */
data class GroupPushSettingRequest(
    /**
     * 社團Id
     */
    @SerializedName("groupId")
    val groupId: Long?,
    /**
     *
     * all: 任何人的行為都會發送推播
     * admin: 只有幹部及社長的行為會發送推播
     * none: 關閉推播
     */
    @SerializedName("pushType")
    val pushType: String?
)