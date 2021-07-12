package com.cmoney.backend2.crm.service.api.creatlivechat

import com.google.gson.annotations.SerializedName

/**
 * @property isPro 是否為付費用戶。
 */
class CreateLiveChatRequestBody(
    @SerializedName("isPro")
    val isPro: Boolean
)