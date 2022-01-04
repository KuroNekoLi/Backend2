package com.cmoney.backend2.forumocean.service.api.notifysetting

import com.google.gson.annotations.SerializedName

/**
 * 通知設定
 *
 * @property notifySettingKey 通知類型
 * @property enable 是否啟用
 */
data class NotifyPushSetting(
    @SerializedName("notifySettingKey")
    val notifySettingKey : String?,
    @SerializedName("enable")
    val enable : Boolean?
)
