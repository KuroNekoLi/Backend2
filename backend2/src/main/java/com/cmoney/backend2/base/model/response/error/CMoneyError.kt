package com.cmoney.backend2.base.model.response.error

import com.google.gson.annotations.SerializedName

/**
 * CMoney錯誤的格式，如果有message有的話就不解析error的物件。
 * {
 *  "message": "custom message",
 *  "error": {
 *      "code": 1,
 *      "message", "custom message"
 *  }
 * }
 * 如果你問我為什麼message要open？因為就是有response取的跟message一模一樣。
 */
open class CMoneyError(
    @SerializedName("message", alternate = ["Message"])
    open val message: String? = null,
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