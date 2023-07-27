package com.cmoney.backend2.common.service

import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
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

    val manager: GlobalBackend2Manager

    /**
     * 服務1. 取得config服務位置(包含確認版本)
     */
    suspend fun getAppConfig(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/SystemCheck.ashx"
    ): Result<GetConfigResponseBody>

    /**
     * 服務4. 忘記密碼
     */
    suspend fun forgotPasswordForEmail(
        account: String,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<EmailForgotPassword>

    /**
     * 服務5. 註冊帳號
     */
    suspend fun registerByEmail(
        account: String,
        password: String,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<EmailRegister>

    /**
     * 服務6-2. 取得該會員資訊(加上身份識別)
     */
    suspend fun getMemberProfile(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<MemberProfile>

    /**
     * 服務14-2. 每天登入給獎
     */
    suspend fun loginReward(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<LoginRewardComplete>

    /**
     * 服務15. 查詢今日是否已發放登入獎勵
     */
    suspend fun hasSentLoginRewardToday(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<HasSentLoginRewardTodayComplete>

    /**
     * 服務18. 更改是否需要接收推播(含驗證)
     */
    suspend fun updateIsNeedPush(
        isNeedPush: Boolean,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/MobilePush.ashx"
    ): Result<UpdateIsNeedPushComplete>

    /**試用服務**/
    /**
     * 服務1. 開始試用
     */
    suspend fun startTrial(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<TrialBeginStatus>

    /**
     * 服務2. 暫停試用計時
     */
    suspend fun pauseTrial(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<TrialPauseStatus>

    /**其他**/
    /**
     * 其他服務1 啟用序號
     */
    suspend fun invocationSerialNumber(
        serial: String,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<InvocationSerialComplete>

    /**
     * 服務3. 取得Access-Token
     */
    suspend fun getAccessToken(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/AccessToken.ashx"
    ): Result<GetAccessToken>

    /**
     * 服務5. 紀錄AAID或IDFA
     */
    suspend fun addDeviceIdentification(
        aaid: String,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<AddDeviceIdentificationComplete>

    /**
     * 服務7-2 更改使用者暱稱
     *
     * @param newNickname
     */
    suspend fun changeNickname(
        newNickname: String,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<ChangeNicknameResponse>

    /**
     * 服務12. 更新會員頭像
     *
     * @param isUseFbImage
     * @param newImageFile
     * @return
     */
    suspend fun changeUserImage(
        isUseFbImage: Boolean,
        newImageFile: File?,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<ChangeUserImageResponse>

    /**
     * 新聞服務1. 今日頭條(會轉打cm-service)
     */
    suspend fun getDailyHeadLine(
        baseArticleId: Long,
        newsType: Int,
        fetchSize: Int,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/StockNews/StockNews.ashx"
    ): Result<List<News>>

    /**
     * 以 [stockId]、[filterType]等參數取得新聞
     *
     * @param stockId
     * @param baseArticleId
     * @param condition
     * @param fromDate
     * @param beforeDays
     * @param filterType
     * @param fetchSize
     */
    suspend fun getStockRssArticlesWithFilterType(
        stockId: String,
        baseArticleId: Long,
        condition: String,
        fromDate: String,
        beforeDays: Int,
        filterType: Int,
        fetchSize: Int,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/StockNews/StockNews.ashx"
    ): Result<List<StockNews>>

    /**
     * 新增新聞已讀數
     *
     * @param memberPk
     * @param articleId
     */
    suspend fun addStockRssArticleClickCount(
        memberPk: Int,
        articleId: Long,
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/StockNews/StockNews.ashx"
    ): Result<Unit>

    /**
     * 服務1. 取得會員購物金
     *
     * @return
     */
    suspend fun getMemberBonus(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<GetMemberBonusResponseBody>

    /**
     * 服務16 . 查詢是否曾經領過"手機綁定"獎勵
     *
     * @return
     */
    suspend fun hasReceivedCellphoneBindReward(
        domain: String = manager.getCommonSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<HasReceivedCellphoneBindRewardResponseBody>
}