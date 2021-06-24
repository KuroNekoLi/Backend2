package com.cmoney.backend2.forumocean.service.api.official.get

import com.google.gson.annotations.SerializedName

/**
 * 官方頻道資訊
 *
 * @property id 官方頻道Id
 * @property name 官方頻道名稱
 * @property description 官方頻道描述
 * @property imageUrl
 * @property typeName
 */
data class OfficialChannelInfo(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("imageUrl")
    val imageUrl : String,
    @SerializedName("typeName")
    val typeName : String
)