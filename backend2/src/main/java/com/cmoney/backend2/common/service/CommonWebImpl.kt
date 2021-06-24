package com.cmoney.backend2.common.service

import com.cmoney.backend2.base.extension.*
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.response.error.CMoneyError
import com.cmoney.backend2.base.model.setting.Setting
import com.cmoney.backend2.common.extension.md5
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
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.security.NoSuchAlgorithmException

class CommonWebImpl(
    private val service: CommonService,
    private val gson: Gson,
    private val setting: Setting,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : CommonWeb {

    override suspend fun getAppConfig(): Result<GetConfigResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getConfig(
                    appId = setting.appId,
                    version = setting.appVersion,
                    device = setting.platform.code
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    override suspend fun forgotPasswordForEmail(account: String): Result<EmailForgotPassword> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.forgotPasswordForEmail(account = account)
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    @Throws(NoSuchAlgorithmException::class)
    override suspend fun registerByEmail(
        account: String,
        password: String,
        device: Int
    ): Result<EmailRegister> = registerByEmail(account, password)

    override suspend fun registerByEmail(account: String, password: String): Result<EmailRegister> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.registerByEmail(
                    xApiLog = XApiLog(
                        appId = setting.appId,
                        platform = setting.platform.code,
                        mode = 1
                    ).let { gson.toJson(it) },
                    account = account,
                    password = password.md5(),
                    device = setting.platform.code
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    override suspend fun getMemberProfile(apiParam: MemberApiParam): Result<MemberProfile> =
        getMemberProfile()

    override suspend fun getMemberProfile(): Result<MemberProfile> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getMemberProfile(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
        }
    }

    override suspend fun loginReward(apiParam: MemberApiParam): Result<LoginRewardComplete> =
        loginReward()

    override suspend fun loginReward(): Result<LoginRewardComplete> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.loginReward(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
        }
    }

    override suspend fun hasSentLoginRewardToday(apiParam: MemberApiParam): Result<HasSentLoginRewardTodayComplete> =
        hasSentLoginRewardToday()

    override suspend fun hasSentLoginRewardToday(): Result<HasSentLoginRewardTodayComplete> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.hasSentLoginRewardToday(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun startTrial(
        apiParam: MemberApiParam
    ): Result<TrialBeginStatus> = startTrial()

    override suspend fun startTrial(): Result<TrialBeginStatus> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.startTrial(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun updateIsNeedPush(
        apiParam: MemberApiParam,
        isNeedPush: Boolean
    ): Result<UpdateIsNeedPushComplete> = updateIsNeedPush(isNeedPush)

    override suspend fun updateIsNeedPush(isNeedPush: Boolean) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateIsNeedPush(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                isNeedPush = isNeedPush
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
        }
    }

    override suspend fun pauseTrial(
        apiParam: MemberApiParam
    ): Result<TrialPauseStatus> = pauseTrial()

    override suspend fun pauseTrial(): Result<TrialPauseStatus> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.pauseTrial(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun addDeviceIdentification(
        apiParam: MemberApiParam,
        aaid: String
    ): Result<AddDeviceIdentificationComplete> = addDeviceIdentification(aaid)

    override suspend fun addDeviceIdentification(aaid: String): Result<AddDeviceIdentificationComplete> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.addDeviceIdentification(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    guid = setting.identityToken.getMemberGuid(),
                    aaid = aaid,
                    appId = setting.appId
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun changeNickname(
        apiParam: MemberApiParam,
        newNickname: String
    ): Result<ChangeNicknameResponse> = changeNickname(newNickname)

    /**
     * 服務7-2 更改使用者暱稱(加上身份識別)
     */
    override suspend fun changeNickname(
        newNickname: String
    ): Result<ChangeNicknameResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.changeNickname(
                    guid = setting.identityToken.getMemberGuid(),
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    nickname = newNickname
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun changeUserImage(
        apiParam: MemberApiParam,
        isUseFbImage: Boolean,
        newImageFile: File?
    ): Result<ChangeUserImageResponse> = changeUserImage(isUseFbImage, newImageFile)

    /**
     * 服務12. 更新會員頭像
     */
    override suspend fun changeUserImage(
        isUseFbImage: Boolean,
        newImageFile: File?
    ): Result<ChangeUserImageResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                builder.apply {
                    addFormDataPart("action", "changeuserimage")
                    addFormDataPart("guid", setting.identityToken.getMemberGuid())
                    addFormDataPart("appId", setting.appId.toString())
                    addFormDataPart("isUseFbImage", isUseFbImage.toString())
                    newImageFile?.apply {
                        addFormDataPart("image", this.name, this.asRequestBody())
                    }
                }

                val response = service.changeUserImage(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    body = builder.build()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }


    override suspend fun getAccessToken(apiParam: MemberApiParam): Result<GetAccessToken> =
        getAccessToken()

    override suspend fun getAccessToken(): Result<GetAccessToken> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getAccessToken(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun invocationSerialNumber(
        apiParam: MemberApiParam,
        serial: String
    ): Result<InvocationSerialComplete> = invocationSerialNumber(serial)

    override suspend fun invocationSerialNumber(serial: String): Result<InvocationSerialComplete> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.invocationSerialNumber(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
                    serial = serial
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun getDailyHeadLine(
        apiParam: MemberApiParam,
        baseArticleId: Long,
        newsType: Int,
        fetchSize: Int
    ): Result<List<News>> = getDailyHeadLine(baseArticleId, newsType, fetchSize)

    override suspend fun getDailyHeadLine(
        baseArticleId: Long,
        newsType: Int,
        fetchSize: Int
    ): Result<List<News>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getDailyHeadLine(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                baseArticleId = baseArticleId,
                newsType = newsType,
                fetchSize = fetchSize
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .newsList ?: emptyList()
        }
    }

    override suspend fun getStockRssArticlesWithFilterType(
        apiParam: MemberApiParam,
        stockId: String,
        baseArticleId: Long,
        condition: String,
        fromDate: String,
        beforeDays: Int,
        filterType: Int,
        fetchSize: Int
    ): Result<List<StockNews>> = getStockRssArticlesWithFilterType(
        stockId,
        baseArticleId,
        condition,
        fromDate,
        beforeDays,
        filterType,
        fetchSize
    )

    override suspend fun getStockRssArticlesWithFilterType(
        stockId: String,
        baseArticleId: Long,
        condition: String,
        fromDate: String,
        beforeDays: Int,
        filterType: Int,
        fetchSize: Int
    ): Result<List<StockNews>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getStockRssArticlesWithFilterType(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                stockId = stockId,
                baseArticleId = baseArticleId,
                condition = condition,
                fromDate = fromDate,
                beforeDays = beforeDays,
                filterType = filterType,
                fetchSize = fetchSize
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .stockNewsList ?: emptyList()
        }
    }

    override suspend fun addStockRssArticleClickCount(
        apiParam: MemberApiParam,
        memberPk: Int,
        articleId: Long
    ): Result<Unit> = addStockRssArticleClickCount(memberPk, articleId)

    override suspend fun addStockRssArticleClickCount(
        memberPk: Int,
        articleId: Long
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.addRssArticleClickCount(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
                memberPk = memberPk,
                articleId = articleId
            )
            if (!response.isSuccessful) {
                throw HttpException(response)
            }
            val body = response.body() ?: throw EmptyBodyException()
            val bodyString = body.string()
            if (bodyString.isBlank()) {
                return@runCatching
            }
            val error = gson.fromJson(bodyString, CMoneyError::class.java)
            throw ServerException(
                code = error.detail?.code ?: 0,
                message = error.detail?.message.orEmpty()
            )
        }
    }

    override suspend fun getMemberBonus(
        apiParam: MemberApiParam
    ): Result<GetMemberBonusResponseBody> = getMemberBonus()

    /**
     * 服務1. 取得會員購物金
     */
    override suspend fun getMemberBonus(): Result<GetMemberBonusResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getMemberBonus(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun hasReceivedCellphoneBindReward(
        apiParam: MemberApiParam
    ): Result<HasReceivedCellphoneBindRewardResponseBody> =
        hasReceivedCellphoneBindReward()

    /**
     * 服務16 . 查詢是否曾經領過"手機綁定"獎勵
     */
    override suspend fun hasReceivedCellphoneBindReward() =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.hasReceivedCellphoneBindReward(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }
}