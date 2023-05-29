package com.cmoney.backend2.activity.service

import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountResponseBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountResponseBody
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager

interface ActivityWeb {

    /**
     * Backend2管理者
     */
    val manager: GlobalBackend2Manager

    /**
     * 取得用戶這個月開啟幾天APP
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getDayCount(
        domain: String = manager.getActivitySettingAdapter().getDomain(),
        url: String = "${domain}ActivityService/Statistics/ActiveApp/Month/DayCount"
    ): Result<GetDayCountResponseBody>

    /**
     * 請求獎勵
     *
     * @param referrerId 推薦人會員編號
     * @param eventId 活動編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun requestBonus(
        referrerId: Long,
        eventId: Long,
        domain: String = manager.getActivitySettingAdapter().getDomain(),
        url: String = "${domain}ActivityService/Referral/Request"
    ): Result<Unit>

    /**
     * 取得推薦人總共成功推薦次數
     *
     * @param eventId 活動編號
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getReferralCount(
        eventId: Long,
        domain: String = manager.getActivitySettingAdapter().getDomain(),
        url: String = "${domain}ActivityService/Referral/ReferralCount"
    ): Result<GetReferralCountResponseBody>
}