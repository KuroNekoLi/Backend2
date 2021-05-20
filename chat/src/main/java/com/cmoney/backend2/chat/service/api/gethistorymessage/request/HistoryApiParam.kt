package com.cmoney.backend2.chat.service.api.gethistorymessage.request

sealed class HistoryApiParam(open val memberId: Long) {

    class FromLatest(val authorizationToken: String,override val memberId: Long, val fetchCount: Int): HistoryApiParam(memberId)

    class FromOldest(val authorizationToken: String,override val memberId: Long, val fetchCount: Int): HistoryApiParam(memberId)
}