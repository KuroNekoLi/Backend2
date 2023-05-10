package com.cmoney.backend2.common.service

import com.cmoney.backend2.base.model.calladapter.RecordApi
import com.cmoney.backend2.common.service.api.adddeviceidentification.AddDeviceIdentificationComplete
import com.cmoney.backend2.common.service.api.changenickname.ChangeNicknameResponseWithError
import com.cmoney.backend2.common.service.api.changeuserimage.ChangeUserImageResponseWithError
import com.cmoney.backend2.common.service.api.forgotpasswordemail.EmailForgotPassword
import com.cmoney.backend2.common.service.api.getaccesstoken.GetAccessTokenResponseWithError
import com.cmoney.backend2.common.service.api.getconfig.GetConfigResponseBody
import com.cmoney.backend2.common.service.api.getdailyheadline.HeadlineResponse
import com.cmoney.backend2.common.service.api.getmemberbonus.GetMemberBonusResponseBodyWithError
import com.cmoney.backend2.common.service.api.getmemberprofile.MemberProfile
import com.cmoney.backend2.common.service.api.getstockrssarticleswithfiltertype.StockRssNewsResponse
import com.cmoney.backend2.common.service.api.hasreceivedcellphonebindreward.HasReceivedCellphoneBindRewardResponseBodyWithError
import com.cmoney.backend2.common.service.api.invocationserial.InvocationSerialComplete
import com.cmoney.backend2.common.service.api.loginreward.HasSentLoginRewardTodayComplete
import com.cmoney.backend2.common.service.api.loginreward.LoginRewardComplete
import com.cmoney.backend2.common.service.api.logout.LogoutComplete
import com.cmoney.backend2.common.service.api.pausetrialtiming.TrialPauseStatus
import com.cmoney.backend2.common.service.api.registeraccount.EmailRegister
import com.cmoney.backend2.common.service.api.starttrialtiming.TrialBeginStatus
import com.cmoney.backend2.common.service.api.updateisneedpushcomplete.UpdateIsNeedPushComplete
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * MobileService-通用
 */
interface CommonService {

    /**
     * 服務1. 取得config服務位置(包含確認版本)
     *
     * @param device 平台
     * @param appId App編號
     * @param version 版本號碼
     *
     */
    @RecordApi(cmoneyAction = "getconfig")
    @FormUrlEncoded
    @POST
    suspend fun getConfig(
        @Url url: String,
        @Field("Action") action: String = "getconfig",
        @Field("Device") device: Int,
        @Field("AppId") appId: Int,
        @Field("Version") version: String
    ): Response<GetConfigResponseBody>

    /**
     * 服務4. 忘記密碼
     */
    @RecordApi(cmoneyAction = "forgetpassword")
    @FormUrlEncoded
    @POST
    suspend fun forgotPasswordForEmail(
        @Url url: String,
        @Field("Action") action: String = "forgetpassword",
        @Field("Account") account: String
    ): Response<EmailForgotPassword>

    /**
     * 服務5. 註冊帳號
     *
     * @param password 需要被md5過
     */
    @RecordApi(cmoneyAction = "registeraccount")
    @FormUrlEncoded
    @POST
    suspend fun registerByEmail(
        @Url url: String,
        @Header("x-cmapi-trace-context") xApiLog: String,
        @Field("Action") action: String = "registeraccount",
        @Field("Account") account: String,
        @Field("Password") password: String,
        @Field("Device") device: Int
    ): Response<EmailRegister>

    /**
     * 服務6-2. 取得該會員資訊(加上身份識別)
     */
    @RecordApi(cmoneyAction = "getmemberprofile")
    @FormUrlEncoded
    @POST
    suspend fun getMemberProfile(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getmemberprofile",
        @Field("Guid") guid: String,
        @Field("AppId") appId: Int
    ): Response<MemberProfile>

    /**
     * 改變暱稱
     */
    @RecordApi(cmoneyAction = "changenickname")
    @FormUrlEncoded
    @POST
    suspend fun changeNickname(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "changenickname",
        @Field("appid") appId: Int,
        @Field("guid") guid: String,
        @Field("nickname") nickname : String
    ): Response<ChangeNicknameResponseWithError>

    /**
     * 服務11. 登出(清除推播Token)
     */
    @RecordApi(cmoneyAction = "logout")
    @FormUrlEncoded
    @POST
    suspend fun logout(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "logout",
        @Field("appid") appId: Int,
        @Field("guid") guid: String
    ): Response<LogoutComplete>

    /**
     * 服務12. 更新會員頭像
     */
    @RecordApi
    @POST
    suspend fun changeUserImage(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body : MultipartBody
    ): Response<ChangeUserImageResponseWithError>

    /**
     * 服務14-2. 每天登入給獎
     */
    @RecordApi(cmoneyAction = "loginreward")
    @FormUrlEncoded
    @POST
    suspend fun loginReward(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "loginreward",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<LoginRewardComplete>

    /**
     * 服務15. 查詢今日是否已發放登入獎勵
     */
    @RecordApi(cmoneyAction = "hassentloginrewardtoday")
    @FormUrlEncoded
    @POST
    suspend fun hasSentLoginRewardToday(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "hassentloginrewardtoday",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<HasSentLoginRewardTodayComplete>

    /**
     * 服務18. 更改是否需要接收推播(含驗證)
     */
    @RecordApi(cmoneyAction = "updateisneedpush")
    @FormUrlEncoded
    @POST
    suspend fun updateIsNeedPush(
        @Url url: String,
        @Header("Authorization")authorization: String,
        @Field("Action") action: String = "updateisneedpush",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("IsNeedPush") isNeedPush: Boolean
    ): Response<UpdateIsNeedPushComplete>

    /**試用服務**/
    /**
     * 服務1. 開始試用
     */
    @RecordApi(cmoneyAction = "starttrialtiming")
    @FormUrlEncoded
    @POST
    suspend fun startTrial(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "starttrialtiming",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<TrialBeginStatus>

    /**
     * 服務2. 暫停試用計時
     */
    @RecordApi(cmoneyAction = "pausetrialtiming")
    @FormUrlEncoded
    @POST
    suspend fun pauseTrial(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "pausetrialtiming",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<TrialPauseStatus>

    /**其他**/

    /**
     * 服務1. 啟用序號
     */
    @RecordApi(cmoneyAction = "enableserialnum")
    @FormUrlEncoded
    @POST
    suspend fun invocationSerialNumber(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "enableserialnum",
        @Field("guid") guid: String,
        @Field("serial") serial: String,
        @Field("AppId") appId: Int
    ): Response<InvocationSerialComplete>

    /**
     * 服務3. 取得Access-Token
     */
    @RecordApi(cmoneyAction = "getaccesstoken")
    @FormUrlEncoded
    @POST
    suspend fun getAccessToken(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("action") action: String = "getaccesstoken",
        @Field("guid") guid: String,
        @Field("appId") appId: Int
    ): Response<GetAccessTokenResponseWithError>

    /**
     * 服務5. 紀錄AAID或IDFA(紀錄廣告識別碼)
     */
    @RecordApi(cmoneyAction = "adddeviceidentification")
    @GET
    suspend fun addDeviceIdentification(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Query("Action") action: String = "adddeviceidentification",
        @Query("guid") guid: String,
        @Query("aaid") aaid: String,
        @Query("appId") appId: Int
    ): Response<AddDeviceIdentificationComplete>

    /**
     * 新聞服務1. 今日頭條(會轉打cm-service)
     *
     * @param baseArticleId Long 從哪篇文章ID後開始拿，第一次取請傳0
     * @param newsType Int 新聞種類(1:每日頭條 2:討論區)
     * @param fetchSize Int
     */
    @RecordApi(cmoneyAction = "getdailyheadline")
    @FormUrlEncoded
    @POST
    suspend fun getDailyHeadLine(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getdailyheadline",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("baseArticleId") baseArticleId: Long,
        @Field("newsType") newsType: Int,
        @Field("fetchSize") fetchSize: Int
    ): Response<HeadlineResponse>

    /**
     * 服務2. 取得個股新聞(會轉打cm-service)
     *
     * 篩選條件的說明
     *
     * 過濾條件編號/說明
     * 1 標題有包含股票名稱，修改為 "搜尋關鍵字",多個關鍵字以 | 符號隔開
     * 2 股票Tag 唯一
     * 3 股票Tag 在前 N (過濾條件參數需設值)
     * 4 點閱次數 >= N (過濾條件參數需設值)
     *
     * 過濾條件參數格式 (空值表無過濾條件)
     * 過濾條件編號_過濾條件參數值 ，多個過濾條件以 逗號 (,) 分隔
     * 注意: 過濾條件 2 跟 3 都有設定，則不回覆任何訊息
     *
     * EX. 1_台積電|2330|外資,3_3,4_2
     * 其條件說明為: 新聞標題包含股票名稱或2330或外資及股票Tag在前3位置且點閱次數大於等於2
     *
     * @param condition 碎碎念的篩選條件
     * @param baseArticleId Long 從哪篇文章ID後開始拿，第一次取請傳0
     * @param filterType Int 篩選方式(And 0, Or 1)
     * @param fetchSize Int
     */
    @RecordApi(cmoneyAction = "getstockrssarticleswithfiltertype")
    @FormUrlEncoded
    @POST
    suspend fun getStockRssArticlesWithFilterType(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getstockrssarticleswithfiltertype",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("StockId") stockId: String,
        @Field("baseArticleId") baseArticleId: Long,
        @Field("condition") condition: String,
        @Field("fromDate") fromDate: String,
        @Field("beforeDays") beforeDays: Int,
        @Field("filterType") filterType: Int,
        @Field("fetchSize") fetchSize: Int
    ): Response<StockRssNewsResponse>

    @RecordApi(cmoneyAction = "addrssarticleclickcount")
    @FormUrlEncoded
    @POST
    suspend fun addRssArticleClickCount(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "addrssarticleclickcount",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String,
        @Field("memberpk") memberPk: Int,
        @Field("ArticleId") articleId: Long
    ): Response<ResponseBody>

    /**
     *  服務1. 取得會員購物金
     */
    @RecordApi(cmoneyAction = "getmemberbonus")
    @FormUrlEncoded
    @POST
    suspend fun getMemberBonus(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "getmemberbonus",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<GetMemberBonusResponseBodyWithError>


    /**
     * 服務16 . 查詢是否曾經領過"手機綁定"獎勵
     */
    @RecordApi(cmoneyAction = "hasreceivedcellphoneBindReward")
    @FormUrlEncoded
    @POST
    suspend fun hasReceivedCellphoneBindReward(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("Action") action: String = "hasreceivedcellphoneBindReward",
        @Field("AppId") appId: Int,
        @Field("Guid") guid: String
    ): Response<HasReceivedCellphoneBindRewardResponseBodyWithError>
}