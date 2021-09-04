package com.cmoney.backend2.userbehavior.service

import com.cmoney.backend2.userbehavior.service.api.common.Event


interface UserBehaviorWeb {

    suspend fun uploadReport(
        events: List<Event>,
        processId: String,
        appId: Int,
        platform: Int,
        version: String,
        os: String,
        device: String
    ): Result<Unit>

}