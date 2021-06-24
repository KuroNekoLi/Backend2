package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

/**
 * 徽章資訊
 *
 * @property description
 * @property enable
 * @property iconType
 * @property id
 * @property title
 * @property type
 */
data class Badge(
    @SerializedName("Description")
    val description: String?,
    @SerializedName("Enable")
    val enable: Boolean?,
    @SerializedName("IconType")
    val iconType: Int?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Type")
    val type: Int?
)