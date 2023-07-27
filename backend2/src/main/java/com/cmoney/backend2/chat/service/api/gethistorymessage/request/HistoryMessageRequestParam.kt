package com.cmoney.backend2.chat.service.api.gethistorymessage.request

/**
 * 取得歷史訊息參數物件
 */
data class HistoryMessageRequestParam(
        val count: Int,

        val startTime: Long = Long.MIN_VALUE,

        val endTime: Long = Long.MIN_VALUE
) {
    fun putQueryMap(map: MutableMap<String, String>): MutableMap<String, String> {
        map[FETCH_COUNT] = count.toString()
        checkOrPut(startTime, START_TIME, map)
        checkOrPut(endTime, END_TIME, map)
        return map
    }

    private fun checkOrPut(value: Long, key: String, map: MutableMap<String, String>) {
        if (value != Long.MIN_VALUE) {
            map[key] = value.toString()
        }
    }

    companion object {
        private const val FETCH_COUNT = "count"

        private const val START_TIME = "startTime"

        private const val END_TIME = "endTime"
    }
}