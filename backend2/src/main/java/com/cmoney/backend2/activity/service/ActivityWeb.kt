package com.cmoney.backend2.activity.service

import com.cmoney.backend2.activity.service.api.getdaycount.GetDayCountResponseBody
import com.cmoney.backend2.activity.service.api.getreferralcount.GetReferralCountResponseBody
import com.cmoney.backend2.base.model.request.MemberApiParam

interface ActivityWeb {

    @Deprecated("ApiParam no longer required")
    suspend fun getDayCount(
        apiParam: MemberApiParam
    ): Result<GetDayCountResponseBody>

    /**
     * 取得用戶這個月開啟幾天APP
     *
     * @return
     */
    suspend fun getDayCount(): Result<GetDayCountResponseBody>

    @Deprecated("ApiParam no longer required")
    suspend fun requestBonus(
        apiParam: MemberApiParam,
        referrerId: Long,
        eventId: Long
    ): Result<Unit>

    /**
     * 請求獎勵
     *
     * @param referrerId 推薦人會員編號
     * @param eventId 活動編號
     * @return
     */
    suspend fun requestBonus(
        referrerId: Long,
        eventId: Long
    ): Result<Unit>

    @Deprecated("ApiParam no longer required")
    suspend fun getReferralCount(
        apiParam: MemberApiParam,
        eventId: Long
    ): Result<GetReferralCountResponseBody>

    /**
     * 取得推薦人總共成功推薦次數
     *
     * @param eventId 活動編號
     * @return
     */
    suspend fun getReferralCount(
        eventId: Long
    ): Result<GetReferralCountResponseBody>
}