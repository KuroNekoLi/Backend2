package com.cmoney.backend2.chat.service.api.chatroomsetting.response

import com.google.gson.annotations.SerializedName

/**
 * 設定內容
 *
 * @property name 名稱
 * @property description 描述
 * @property announcements 公告
 * @property roles 呼叫者所屬角色
 */
data class ChatRoomSettingProperties(
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("announcements")
    val announcements: List<Long>?,
    @SerializedName("@Roles")
    val roles: List<String>?
)