package com.cmoney.backend2.base.model.response.error

import com.google.gson.annotations.SerializedName

/**
 * CMoney錯誤的格式
 */
open class CMoneyError(
    @SerializedName("Error", alternate = ["error"])
    val detail: Detail? = null
) {
    class Detail(
        @SerializedName("Code", alternate = ["code"])
        val code: Int? = null,
        @SerializedName("Message", alternate = ["message"])
        val message: String? = null
    )
}