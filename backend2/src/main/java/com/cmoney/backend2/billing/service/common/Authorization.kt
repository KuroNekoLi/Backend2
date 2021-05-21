package com.cmoney.backend2.billing.service.common

import com.cmoney.backend2.billing.service.common.Authorization.State.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @param state 該會員的權限狀態
 * @param expireTime 權限到期日
 */
data class Authorization(
    val state: State,
    val expireTime: Date
) {
    constructor(type: Int?, expireTime: String?) : this(parseToState(type), parseToDate(expireTime))

    companion object {

        private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN)
        /**
         * 解析授權狀態
         */
        fun parseToState(value: Int?): State {
            return when(value) {
                0 -> {
                    State.Free
                }
                1 -> {
                    State.Phone
                }
                2, 3 -> {
                    State.Pc
                }
                else -> {
                    State.Free
                }
            }
        }

        /**
         * 解析過期時間
         */
        @Throws(ParseException::class)
        fun parseToDate(value: String?): Date {
            return simpleDateFormat.parse(value.orEmpty())!!
        }
    }

    /**
     * @property Free 免費
     * @property Pc 理財寶
     * @property Phone 手機
     */
    enum class State {
        Free,
        Pc,
        Phone
    }
}