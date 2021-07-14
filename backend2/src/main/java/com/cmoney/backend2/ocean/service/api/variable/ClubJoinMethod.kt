package com.cmoney.backend2.ocean.service.api.variable

import com.google.gson.annotations.SerializedName

/**
 * 加入社團方式
 * 1.自由參加 (不需審核)
 * 2.邀請制
 * 3.付費社團
 * 4.需審核
 *
 */
enum class ClubJoinMethod {
    @SerializedName("1")
    Free,
    @SerializedName("2")
    InvitationOnly,
    @SerializedName("3")
    PaidOnly,
    @SerializedName("4")
    Review
}