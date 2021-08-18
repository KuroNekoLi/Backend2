package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type

interface AuthorizationWeb {
    /**
     * Authorization API
     * 取得會員某種權限類型的基底功能權限到期日
     *
     * @param type 權限類型
     * @param subjectId appId
     */
    suspend fun getExpiredTime(
        type: Type,
        subjectId: Long
    ): Result<ExpiredTime>
}