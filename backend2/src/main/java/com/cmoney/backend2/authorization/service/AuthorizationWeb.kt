package com.cmoney.backend2.authorization.service

import com.cmoney.backend2.authorization.service.api.getexpiredtime.ExpiredTime
import com.cmoney.backend2.authorization.service.api.getexpiredtime.Type
import com.cmoney.backend2.authorization.service.api.hasauth.Auth
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager

interface AuthorizationWeb {

    /**
     * Backend2管理者
     */
    val manager: GlobalBackend2Manager

    /**
     * 取得會員某種權限類型的基底功能權限到期日
     *
     * @param type 權限類型
     * @param subjectId 權限編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getExpiredTime(
        type: Type,
        subjectId: Long,
        domain: String = manager.getAuthorizationSettingAdapter().getDomain(),
        url: String = "${domain}AuthorizationServer/Authorization/ExpiredTime/${type.value}/${subjectId}"
    ): Result<ExpiredTime>

    /**
     * 是否有權限
     *
     * @param type 權限類型
     * @param subjectId 權限編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     * @return 是否有權限
     */
    suspend fun hasAuth(
        type: Type,
        subjectId: Long,
        domain: String = manager.getAuthorizationSettingAdapter().getDomain(),
        url: String = "${domain}AuthorizationServer/Authorization/${type.value}/$subjectId"
    ): Result<Auth>

    /**
     * 取得會員指定類型有權限的基底功能清單
     *
     * @param type 權限類型
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getPurchasedSubjectIds(
        type: Type,
        domain: String = manager.getAuthorizationSettingAdapter().getDomain(),
        url: String = "${domain}AuthorizationServer/Authorization/${type.value}/SubjectIds"
    ): Result<List<Long>>
}