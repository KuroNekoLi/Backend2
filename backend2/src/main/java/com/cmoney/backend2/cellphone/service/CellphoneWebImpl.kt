package com.cmoney.backend2.cellphone.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.manager.GlobalBackend2Manager
import com.cmoney.backend2.cellphone.service.api.CellphoneParam
import com.cmoney.backend2.cellphone.service.api.bindcellphone.BindCellphoneResponseBody
import com.cmoney.backend2.cellphone.service.api.checkcellphonebindingverifycode.CheckCellphoneBindingVerifyCodeResponseBody
import com.cmoney.backend2.cellphone.service.api.checkverifycode.CellphoneCheckVerifyCode
import com.cmoney.backend2.cellphone.service.api.forgotpassword.CellphoneForgotPassword
import com.cmoney.backend2.cellphone.service.api.getaccountinfo.AccountInfo
import com.cmoney.backend2.cellphone.service.api.getverifycode.CellphoneGetVerifyCode
import com.cmoney.backend2.cellphone.service.api.register.CellphoneRegister
import com.cmoney.backend2.cellphone.service.api.unbindcellphone.UnbindCellphoneResponseBody
import com.cmoney.backend2.cellphone.service.api.updatepassword.UpdatePasswordResponseBody
import com.cmoney.core.DefaultDispatcherProvider
import com.cmoney.core.DispatcherProvider
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CellphoneWebImpl(
    override val manager: GlobalBackend2Manager,
    private val service: CellphoneService,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider
) : CellphoneWeb {

    override suspend fun getVerifyCode(
        cellphoneParam: CellphoneParam,
        domain: String,
        url: String
    ): Result<CellphoneGetVerifyCode> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getVerifyCode(
                url = url,
                countryCode = cellphoneParam.countryCode,
                cellphoneNumber = cellphoneParam.cellphoneNumber,
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun checkVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String,
        domain: String,
        url: String
    ): Result<CellphoneCheckVerifyCode> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.checkVerifyCode(
                url = url,
                countryCode = cellphoneParam.countryCode,
                cellphoneNumber = cellphoneParam.cellphoneNumber,
                verifyCode = verifyCode
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    override suspend fun registerByCellphone(
        cellphoneParam: CellphoneParam,
        password: String,
        domain: String,
        url: String
    ): Result<CellphoneRegister> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.registerByCellphone(
                url = url,
                xApiLog = XApiLog(
                    appId = manager.getAppId(),
                    platform = manager.getPlatform().code,
                    mode = 3
                ).let { gson.toJson(it) },
                countryCode = cellphoneParam.countryCode,
                cellphoneNumber = cellphoneParam.cellphoneNumber,
                password = password.md5(),
                platform = manager.getPlatform().code
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun forgotPasswordForCellphone(
        cellphoneParam: CellphoneParam,
        domain: String,
        url: String
    ): Result<CellphoneForgotPassword> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.forgotPasswordForCellphone(
                url = url,
                countryCode = cellphoneParam.countryCode,
                cellphoneNumber = cellphoneParam.cellphoneNumber
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun updatePassword(
        oldPassword: String,
        oldHasMd5: Boolean,
        newPassword: String,
        domain: String,
        url: String
    ): Result<UpdatePasswordResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updatePassword(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                oldPassword = if (oldHasMd5) {
                    oldPassword
                } else {
                    oldPassword.md5()
                },
                newPassword = newPassword.md5()
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun getAccountInfo(
        domain: String,
        url: String
    ): Result<AccountInfo> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getAccountInfo(
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

    override suspend fun bindCellphone(
        cellphoneParam: CellphoneParam,
        domain: String,
        url: String
    ): Result<BindCellphoneResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.bindCellphone(
                    url = url,
                    authorization = manager.getAccessToken().createAuthorizationBearer(),
                    appId = manager.getAppId(),
                    guid = manager.getIdentityToken().getMemberGuid(),
                    countryCode = cellphoneParam.countryCode,
                    cellphoneNumber = cellphoneParam.cellphoneNumber
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun checkCellphoneBindingVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String?,
        domain: String,
        url: String
    ): Result<CheckCellphoneBindingVerifyCodeResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.checkCellphoneBindingVerifyCode(
                url = url,
                authorization = manager.getAccessToken().createAuthorizationBearer(),
                appId = manager.getAppId(),
                guid = manager.getIdentityToken().getMemberGuid(),
                countryCode = cellphoneParam.countryCode,
                cellphoneNumber = cellphoneParam.cellphoneNumber,
                verifyCode = verifyCode
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun unbindCellphone(
        domain: String,
        url: String
    ): Result<UnbindCellphoneResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.unbindCellphone(
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

    @Throws(NoSuchAlgorithmException::class)
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(this.toByteArray())
        val digest = md.digest()
        val buffer = StringBuffer()
        val codingString = "%02x"
        for (b in digest) {
            buffer.append(String.format(codingString, b.toInt() and 0xff))
        }
        return buffer.toString()
    }
}