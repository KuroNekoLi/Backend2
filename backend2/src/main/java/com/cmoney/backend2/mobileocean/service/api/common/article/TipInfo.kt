package com.cmoney.backend2.mobileocean.service.api.common.article


import com.google.gson.annotations.SerializedName

/**
 * 打賞資訊
 *
 * @property memberTip 會員打賞金額
 * @property totalTip 總打賞金額
 */
data class TipInfo(
    @SerializedName("MemberTip")
    val memberTip: Int?,
    @SerializedName("TotalTip")
    val totalTip: Int?
)