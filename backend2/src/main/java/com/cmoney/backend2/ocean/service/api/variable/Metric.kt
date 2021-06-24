package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

/**
 * 徽章指標
 *
 * @property badgeId
 * @property goal
 * @property metricsId
 */
data class Metric(
    @SerializedName("BadgeId")
    val badgeId: Int?,
    @SerializedName("Goal")
    val goal: Int?,
    @SerializedName("MetricsId")
    val metricsId: Int?
)