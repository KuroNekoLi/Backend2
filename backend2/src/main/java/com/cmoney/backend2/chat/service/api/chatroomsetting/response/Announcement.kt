package com.cmoney.backend2.chat.service.api.chatroomsetting.response

import com.google.gson.annotations.SerializedName

/**
 * 共告物件
 *
 * @property messageId 訊息ID
 * @property createTime 創建時間
 */
data class Announcement(
    @SerializedName("messageId")
    val messageId: Long?,
    @SerializedName("createTime")
    val createTime: Long?
)
