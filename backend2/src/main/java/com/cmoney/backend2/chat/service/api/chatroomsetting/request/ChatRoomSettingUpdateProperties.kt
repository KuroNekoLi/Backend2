package com.cmoney.backend2.chat.service.api.chatroomsetting.request

import com.cmoney.backend2.chat.service.api.chatroomsetting.response.Announcement
import com.google.gson.annotations.SerializedName

/**
 * 聊天室設定更新欄位，未更新內容請設定為null
 *
 * @property name 名稱
 * @property description 描述
 * @property announcement 公告
 */
data class ChatRoomSettingUpdateProperties(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("announcements")
    val announcement: List<Announcement>? = null
)
