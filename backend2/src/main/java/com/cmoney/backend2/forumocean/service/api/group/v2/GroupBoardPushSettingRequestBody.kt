package com.cmoney.backend2.forumocean.service.api.group.v2

import com.google.gson.annotations.SerializedName

data class GroupBoardPushSettingRequestBody(
    /**
     *
     * all: 任何人的行為都會發送推播
     * admin: 只有幹部及社長的行為會發送推播
     * none: 關閉推播
     */
    @SerializedName("pushType")
    val pushType: String?
)
