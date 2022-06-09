package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.model.setting.Setting

interface AuthorizationWeb {

    /**
     * Backend2設定
     */
    val setting: Setting

    /**
     * Authorization API
     * 取得會員某種權限類型的基底功能權限到期日
     *
     * @param type 權限類型
     * @param subjectId 權限編號
     */
    suspend fun getExpiredTime(
        type: Type,
        subjectId: Long
    ): Result<ExpiredTime>

    /**
     * 是否有權限
     *
     * @param host 網域位址
     * @param type 權限類型
     * @param subjectId 權限編號
     * @return 權限狀態回傳
     */
    suspend fun hasAuth(
        host: String = setting.domainUrl,
        type: Type,
        subjectId: Long
    ): Result<Auth>

    /**
     * 取得會員指定類型有權限的基底功能清單
     */
    suspend fun getPurchasedSubjectIds(type: Type): Result<List<Long>>
}