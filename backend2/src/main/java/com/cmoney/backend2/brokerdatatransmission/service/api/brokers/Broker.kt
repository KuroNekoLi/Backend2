package com.cmoney.backend2.brokerdatatransmission.service.api.brokers

import com.google.gson.annotations.SerializedName

data class Broker(

    @SerializedName("displayOrder")
    val displayOrder: Int = 0,

    @SerializedName("brokerID")
    val brokerId: String = "",

    @SerializedName("brokerName")
    val brokerName: String = "",

    @SerializedName("shortName")
    val brokerNickName: String = "",

    // 券商主題色
    @SerializedName("color")
    val hexColorCode: String = "#000000",

    // 使用者是否匯入過
    @SerializedName("hasImported")
    val hasImported: Boolean = false,

    // 是否需帶入分點id
    @SerializedName("isDetailToSubBrokerId")
    val isNeedSubBrokerId: Boolean = false,

    // 是否需要帶入生日
    @SerializedName("isNeedBirthday")
    val isNeedBirthday: Boolean = false,

    // 是否暫停服務
    @SerializedName("isSuspend")
    val isSuspend: Boolean = false
)
