package com.cmoney.backend2.cellphone.service

import com.cmoney.backend2.base.extension.checkIWithError
import com.cmoney.backend2.base.extension.checkIsSuccessful
import com.cmoney.backend2.base.extension.createAuthorizationBearer
import com.cmoney.backend2.base.extension.requireBody
import com.cmoney.backend2.base.model.dispatcher.DefaultDispatcherProvider
import com.cmoney.backend2.base.model.dispatcher.DispatcherProvider
import com.cmoney.backend2.base.model.log.XApiLog
import com.cmoney.backend2.base.model.request.MemberApiParam
import com.cmoney.backend2.base.model.setting.Setting
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
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CellphoneWebImpl(
    private val service: CellphoneService,
    private val setting: Setting,
    private val gson: Gson,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : CellphoneWeb {

    override suspend fun getVerifyCode(cellphoneParam: CellphoneParam): Result<CellphoneGetVerifyCode> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.getVerifyCode(
                    countryCode = cellphoneParam.countryCode,
                    cellphoneNumber = cellphoneParam.cellphoneNumber
                )
                response.checkIsSuccessful()
                    .requireBody()
                    .checkIWithError()
                    .toRealResponse()
            }
        }

    override suspend fun checkVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String
    ): Result<CellphoneCheckVerifyCode> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.checkVerifyCode(
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
        password: String
    ): Result<CellphoneRegister> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.registerByCellphone(
                xApiLog = XApiLog(
                    appId = setting.appId,
                    platform = setting.platform.code,
                    mode = 3
                ).let { gson.toJson(it) },
                countryCode = cellphoneParam . countryCode,
                cellphoneNumber = cellphoneParam.cellphoneNumber,
                password = password.md5(),
                platform = setting.platform.code
            )
            response.checkIsSuccessful()
                .requireBody()
                .checkIWithError()
                .toRealResponse()
        }
    }

    override suspend fun forgotPasswordForCellphone(cellphoneParam: CellphoneParam): Result<CellphoneForgotPassword> =
        withContext(dispatcher.io()) {
            kotlin.runCatching {
                val response = service.forgotPasswordForCellphone(
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
        apiParam: MemberApiParam,
        oldPassword: String,
        oldHasMd5: Boolean,
        newPassword: String
    ) = updatePassword(oldPassword, oldHasMd5, newPassword)

    override suspend fun updatePassword(
        oldPassword: String,
        oldHasMd5: Boolean,
        newPassword: String
    ): Result<UpdatePasswordResponseBody> = withContext(dispatcher.io()) {
        kotlin.runCatching {
            val response = service.updatePassword(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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

    override suspend fun getAccountInfo(apiParam: MemberApiParam): Result<AccountInfo> =
        getAccountInfo()

    override suspend fun getAccountInfo(): Result<AccountInfo> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.getAccountInfo(
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

    override suspend fun bindCellphone(apiParam: MemberApiParam, cellphoneParam: CellphoneParam) =
        bindCellphone(cellphoneParam)

    override suspend fun bindCellphone(cellphoneParam: CellphoneParam): Result<BindCellphoneResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.bindCellphone(
                    authorization = setting.accessToken.createAuthorizationBearer(),
                    appId = setting.appId,
                    guid = setting.identityToken.getMemberGuid(),
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
        apiParam: MemberApiParam,
        cellphoneParam: CellphoneParam,
        verifyCode: String?
    ) = checkCellphoneBindingVerifyCode(cellphoneParam, verifyCode)

    override suspend fun checkCellphoneBindingVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String?
    ): Result<CheckCellphoneBindingVerifyCodeResponseBody> = withContext(dispatcher.io()) {
        runCatching {
            val response = service.checkCellphoneBindingVerifyCode(
                authorization = setting.accessToken.createAuthorizationBearer(),
                appId = setting.appId,
                guid = setting.identityToken.getMemberGuid(),
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

    override suspend fun unbindCellphone(apiParam: MemberApiParam) = unbindCellphone()

    override suspend fun unbindCellphone(): Result<UnbindCellphoneResponseBody> =
        withContext(dispatcher.io()) {
            runCatching {
                val response = service.unbindCellphone(
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