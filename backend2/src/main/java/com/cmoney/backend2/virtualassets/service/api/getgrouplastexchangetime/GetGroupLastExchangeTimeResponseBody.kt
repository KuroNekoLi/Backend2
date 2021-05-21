package com.cmoney.backend2.virtualassets.service.api.getgrouplastexchangetime

data class GetGroupLastExchangeTimeResponseBody(
    val lastExchangeTime: Map<String, Long>?
)