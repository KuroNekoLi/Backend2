package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

/**
 * 徽章取得需求
 *
 * @property badgeId
 * @property description
 * @property metrics
 * @property operator
 */
data class Requirement(
    @SerializedName("BadgeId")
    val badgeId: Int?,
    @SerializedName("Descriptioin")
    val description: String?,
    @SerializedName("Metrics")
    val metrics: List<Metric?>?,
    @SerializedName("Operator")
    val operator: Int?
)