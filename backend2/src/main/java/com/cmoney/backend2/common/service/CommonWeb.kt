package com.cmoney.backend2.common.service

import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.common.service.api.adddeviceidentification.AddDeviceIdentificationComplete
import com.cmoney.backend2.common.service.api.changenickname.ChangeNicknameResponse
import com.cmoney.backend2.common.service.api.changeuserimage.ChangeUserImageResponse
import com.cmoney.backend2.common.service.api.forgotpasswordemail.EmailForgotPassword
import com.cmoney.backend2.common.service.api.getaccesstoken.GetAccessToken
import com.cmoney.backend2.common.service.api.getconfig.GetConfigResponseBody
import com.cmoney.backend2.common.service.api.getdailyheadline.News
import com.cmoney.backend2.common.service.api.getmemberbonus.GetMemberBonusResponseBody
import com.cmoney.backend2.common.service.api.getmemberprofile.MemberProfile
import com.cmoney.backend2.common.service.api.getstockrssarticleswithfiltertype.StockNews
import com.cmoney.backend2.common.service.api.hasreceivedcellphonebindreward.HasReceivedCellphoneBindRewardResponseBody
import com.cmoney.backend2.common.service.api.invocationserial.InvocationSerialComplete
import com.cmoney.backend2.common.service.api.loginreward.HasSentLoginRewardTodayComplete
import com.cmoney.backend2.common.service.api.loginreward.LoginRewardComplete
import com.cmoney.backend2.common.service.api.pausetrialtiming.TrialPauseStatus
import com.cmoney.backend2.common.service.api.registeraccount.EmailRegister
import com.cmoney.backend2.common.service.api.starttrialtiming.TrialBeginStatus
import com.cmoney.backend2.common.service.api.updateisneedpushcomplete.UpdateIsNeedPushComplete
import java.io.File

interface CommonWeb {

    /**
     * 服務1. 取得config服務位置(包含確認版本)
     */
    suspend fun getAppConfig(): Result<GetConfigResponseBody>

    /**
     * 服務4. 忘記密碼
     */
    suspend fun forgotPasswordForEmail(account: String): Result<EmailForgotPassword>

    /**
     * 服務5. 註冊帳號
     */
    @Deprecated("Device no longer required")
    suspend fun registerByEmail(
        account: String,
        password: String,
        device: Int
    ): Result<EmailRegister>

    suspend fun registerByEmail(
        account: String,
        password: String
    ): Result<EmailRegister>

    /**
     * 服務6-2. 取得該會員資訊(加上身份識別)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getMemberProfile(apiParam: MemberApiParam): Result<MemberProfile>

    suspend fun getMemberProfile(): Result<MemberProfile>

    /**
     * 服務14-2. 每天登入給獎
     */
    @Deprecated("ApiParam no longer required")
    suspend fun loginReward(apiParam: MemberApiParam): Result<LoginRewardComplete>

    suspend fun loginReward(): Result<LoginRewardComplete>

    /**
     * 服務15. 查詢今日是否已發放登入獎勵
     */
    @Deprecated("ApiParam no longer required")
    suspend fun hasSentLoginRewardToday(apiParam: MemberApiParam): Result<HasSentLoginRewardTodayComplete>

    suspend fun hasSentLoginRewardToday(): Result<HasSentLoginRewardTodayComplete>


    /**
     * 服務18. 更改是否需要接收推播(含驗證)
     */
    suspend fun updateIsNeedPush(isNeedPush: Boolean): Result<UpdateIsNeedPushComplete>

    @Deprecated("ApiParam no longer required")
    suspend fun updateIsNeedPush(
        apiParam: MemberApiParam,
        isNeedPush: Boolean
    ): Result<UpdateIsNeedPushComplete>


    /**試用服務**/
    /**
     * 服務1. 開始試用
     */
    @Deprecated("ApiParam no longer required")
    suspend fun startTrial(apiParam: MemberApiParam): Result<TrialBeginStatus>

    suspend fun startTrial(): Result<TrialBeginStatus>


    /**
     * 服務2. 暫停試用計時
     */
    @Deprecated("ApiParam no longer required")
    suspend fun pauseTrial(apiParam: MemberApiParam): Result<TrialPauseStatus>

    suspend fun pauseTrial(): Result<TrialPauseStatus>

    /**其他**/
    /**
     * 其他服務1 啟用序號
     */
    @Deprecated("ApiParam no longer required")
    suspend fun invocationSerialNumber(
        apiParam: MemberApiParam,
        serial: String
    ): Result<InvocationSerialComplete>

    suspend fun invocationSerialNumber(
        serial: String
    ): Result<InvocationSerialComplete>

    /**
     * 服務3. 取得Access-Token
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getAccessToken(
        apiParam: MemberApiParam
    ): Result<GetAccessToken>

    suspend fun getAccessToken(): Result<GetAccessToken>

    /**
     * 服務5. 紀錄AAID或IDFA
     */
    @Deprecated("ApiParam no longer required")
    suspend fun addDeviceIdentification(
        apiParam: MemberApiParam,
        aaid: String
    ): Result<AddDeviceIdentificationComplete>

    suspend fun addDeviceIdentification(
        aaid: String
    ): Result<AddDeviceIdentificationComplete>

    /**
     * 服務7-2 更改使用者暱稱
     *
     * @param apiParam
     * @param newNickname
     */
    @Deprecated("ApiParam no longer required")
    suspend fun changeNickname(
        apiParam: MemberApiParam,
        newNickname: String
    ): Result<ChangeNicknameResponse>

    suspend fun changeNickname(newNickname: String): Result<ChangeNicknameResponse>

    /**
     * 服務12. 更新會員頭像
     *
     * @param apiParam
     * @param isUseFbImage
     * @param newImageFile
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun changeUserImage(
        apiParam: MemberApiParam,
        isUseFbImage: Boolean,
        newImageFile: File?
    ): Result<ChangeUserImageResponse>

    suspend fun changeUserImage(
        isUseFbImage: Boolean,
        newImageFile: File?
    ): Result<ChangeUserImageResponse>


    /**
     * 新聞服務1. 今日頭條(會轉打cm-service)
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getDailyHeadLine(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        newsType: Int,
        fetchSize: Int
    ): Result<List<News>>

    suspend fun getDailyHeadLine(
        baseArticleId: Long,
        newsType: Int,
        fetchSize: Int
    ): Result<List<News>>

    @Deprecated("ApiParam no longer required")
    suspend fun getStockRssArticlesWithFilterType(
        apiParam: MemberApiParam,
        stockId: String,
        baseArticleId: Long,
        condition: String,
        fromDate: String,
        beforeDays: Int,
        filterType: Int,
        fetchSize: Int
    ): Result<List<StockNews>>

    suspend fun getStockRssArticlesWithFilterType(
        stockId: String,
        baseArticleId: Long,
        condition: String,
        fromDate: String,
        beforeDays: Int,
        filterType: Int,
        fetchSize: Int
    ): Result<List<StockNews>>

    @Deprecated("ApiParam no longer required")
    suspend fun addStockRssArticleClickCount(
        apiParam: MemberApiParam,
        memberPk: Int,
        articleId: Long
    ): Result<Unit>

    suspend fun addStockRssArticleClickCount(
        memberPk: Int,
        articleId: Long
    ): Result<Unit>

    /**
     * 服務1. 取得會員購物金
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun getMemberBonus(
        apiParam: MemberApiParam
    ): Result<GetMemberBonusResponseBody>

    suspend fun getMemberBonus(): Result<GetMemberBonusResponseBody>


    /**
     * 服務16 . 查詢是否曾經領過"手機綁定"獎勵
     *
     * @param apiParam
     * @return
     */
    @Deprecated("ApiParam no longer required")
    suspend fun hasReceivedCellphoneBindReward(
        apiParam: MemberApiParam
    ): Result<HasReceivedCellphoneBindRewardResponseBody>

    suspend fun hasReceivedCellphoneBindReward(): Result<HasReceivedCellphoneBindRewardResponseBody>
}