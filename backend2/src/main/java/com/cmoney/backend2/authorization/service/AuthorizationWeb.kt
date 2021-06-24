package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.ExpiredTime

interface AuthorizationWeb {
    /**
     * Authorization API
     * 取得會員某種權限類型的基底功能權限到期日
     *
     * @param type 權限類型 (手機請使用 MobilePaid)
     * @param subjectId appId
     */
    suspend fun getExpiredTime(
        type: String,
        subjectId: Int
    ): Result<ExpiredTime>
}