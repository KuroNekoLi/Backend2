package com.cmoney.backend2.vtwebapi.service.api.purchaseproductcard

import com.google.gson.annotations.SerializedName

/**
 * @property giftFromMember 這張卡是誰送的
 * @property ownerMemberPk 擁有者PK
 * @property productSn 卡片定義序號
 */
data class PurchaseProductCardRequestBody(
    @SerializedName("giftFromMember")
    val giftFromMember: Int,
    @SerializedName("ownerMemberPk")
    val ownerMemberPk: Int,
    @SerializedName("productSn")
    val productSn: Long
)
