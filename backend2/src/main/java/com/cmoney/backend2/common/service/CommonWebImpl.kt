package com.cmoney.backend2.common.service

import com.cmoney.backend2.base.extension.checkISuccess
import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.exception.EmptyBodyException
import com.cmoney.backend2.base.model.exception.ServerException
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.base.model.response.error.CMoneyError
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
import com.cmoney.backend2.common.service.api.invocationserial.InvocationSerialComplete
import com.cmoney.backend2.common.service.api.loginreward.HasSentLoginRewardTodayComplete
import com.cmoney.backend2.common.service.api.loginreward.LoginRewardComplete
import com.cmoney.backend2.common.service.api.pausetrialtiming.TrialPauseStatus
import com.cmoney.backend2.common.service.api.registeraccount.EmailRegister
import com.cmoney.backend2.common.service.api.starttrialtiming.TrialBeginStatus
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class CommonWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: CommonService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : CommonWeb {

    override suspend fun getAppConfig(
        domain: String,
        url: String
    ): Result<GetConfigResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getConfig(
                    url = url,
                    appId = manager.getAppId(),
                    version = manager.getAppVersionName(),
                    device = manager.getPlatform().code
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    override suspend fun forgotPasswordForEmail(
        account: String,
        domain: String,
        url: String
    ): Result<EmailForgotPassword> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.forgotPasswordForEmail(
                    url = url,
                    account = account
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    override suspend fun registerByEmail(
        account: String,
        password: String,
        domain: String,
        url: String
    ): Result<EmailRegister> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.registerByEmail(
                    url = url,
                    xApiLog = XApiLog(
                        appId = manager.getAppId(),
                        platform = manager.getPlatform().code,
                        mode = 1
                    ).let { gson.toJson(it) },
                    account = account,
                    password = password.md5(),
                    device = manager.getPlatform().code
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkISuccess()
            }
        }

    override suspend fun getMemberProfile(domain: String, url: String): Result<MemberProfile> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getMemberProfile(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun loginReward(domain: String, url: String): Result<LoginRewardComplete> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.loginReward(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun hasSentLoginRewardToday(
        domain: String,
        url: String
    ): Result<HasSentLoginRewardTodayComplete> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.hasSentLoginRewardToday(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun startTrial(domain: String, url: String): Result<TrialBeginStatus> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.startTrial(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun updateIsNeedPush(
        isNeedPush: Boolean,
        domain: String,
        url: String
    ) = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updateIsNeedPush(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                isNeedPush = isNeedPush
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
        }
    }

    override suspend fun pauseTrial(domain: String, url: String): Result<TrialPauseStatus> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.pauseTrial(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
            }
        }

    override suspend fun addDeviceIdentification(
        aaid: String,
        domain: String,
        url: String
    ): Result<AddDeviceIdentificationComplete> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.addDeviceIdentification(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    aaid = aaid,
                    appId = manager.getAppId()
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }

    /**
     * 服務7-2 更改使用者暱稱(加上身份識別)
     */
    override suspend fun changeNickname(
        newNickname: String,
        domain: String,
        url: String
    ): Result<ChangeNicknameResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.changeNickname(
                    guid = manager.getIdentityToken().getMemberGuid(),
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    nickname = newNickname
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務12. 更新會員頭像
     */
    override suspend fun changeUserImage(
        isUseFbImage: Boolean,
        newImageFile: File?,
        domain: String,
        url: String
    ): Result<ChangeUserImageResponse> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
                builder.apply {
                    addFormDataPart("action", "changeuserimage")
                    addFormDataPart("guid", manager.getIdentityToken().getMemberGuid())
                    addFormDataPart("appId", manager.getAppId().toString())
                    addFormDataPart("isUseFbImage", isUseFbImage.toString())
                    newImageFile?.apply {
                        addFormDataPart("image", this.name, this.asRequestBody())
                    }
                }

                val response = service.changeUserImage(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    body = builder.build()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun getAccessToken(domain: String, url: String): Result<GetAccessToken> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.getAccessToken(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun invocationSerialNumber(
        serial: String,
        domain: String,
        url: String
    ): Result<InvocationSerialComplete> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.invocationSerialNumber(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    serial = serial
                )
                response.checkIsSuccessful()
                    .requireBody()
            }
        }

    override suspend fun getDailyHeadLine(
        baseArticleId: Long,
        newsType: Int,
        fetchSize: Int,
        domain: String,
        url: String
    ): Result<List<News>> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getDailyHeadLine(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
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
        stockId: String,
        baseArticleId: Long,
        condition: String,
        fromDate: String,
        beforeDays: Int,
        filterType: Int,
        fetchSize: Int,
        domain: String,
        url: String
    ): Result<List<StockNews>> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.getStockRssArticlesWithFilterType(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
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
        memberPk: Int,
        articleId: Long,
        domain: String,
        url: String
    ): Result<Unit> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.addRssArticleClickCount(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
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

    /**
     * 服務1. 取得會員購物金
     */
    override suspend fun getMemberBonus(
        domain: String,
        url: String
    ): Result<GetMemberBonusResponseBody> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getMemberBonus(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    /**
     * 服務16 . 查詢是否曾經領過"手機綁定"獎勵
     */
    override suspend fun hasReceivedCellphoneBindReward(
        domain: String,
        url: String
    ) =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.hasReceivedCellphoneBindReward(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid()
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }
}