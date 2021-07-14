package com.cmoney.backend2.mobileocean.service.api.common.article


import com.google.gson.annotations.SerializedName

/**
 * 股票標籤清單
 *
 * @property bullBear
 * @property commKey
 * @property commName
 */
data class StockTag(
    @SerializedName("BullBear")
    val bullBear: BullBearType?,
    @SerializedName("CommKey")
    val commKey: String?,
    @SerializedName("CommName")
    val commName: String?
){
    enum class BullBearType constructor(val value: Int) {
        // 沒有選
        @SerializedName("0")
        None(0),

        // 多
        @SerializedName("1")
        Bull(1),

        // 空
        @SerializedName("-1")
        Bear(-1);

        companion object {
            fun fromInt(i: Int): BullBearType {
                for (v in BullBearType.values()) {
                    if (v.value == i) {
                        return v
                    }
                }
                return None
            }
        }
    }
}

