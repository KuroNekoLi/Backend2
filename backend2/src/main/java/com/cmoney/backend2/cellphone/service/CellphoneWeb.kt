package com.cmoney.backend2.cellphone.service

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
import java.security.NoSuchAlgorithmException

interface CellphoneWeb {

    val manager: GlobalBackend2Manager

    /**
     * 服務1. 取得驗證碼 / 重寄驗證碼
     *
     * @param cellphoneParam 電話參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getVerifyCode(
        cellphoneParam: CellphoneParam,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<CellphoneGetVerifyCode>

    /**
     * 服務2. 驗證註冊驗證碼
     *
     * @param cellphoneParam 電話參數
     * @param verifyCode 驗證碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun checkVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<CellphoneCheckVerifyCode>

    /**
     * 服務3. 手機號碼註冊
     *
     * @param cellphoneParam 電話參數
     * @param password 密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    @Throws(NoSuchAlgorithmException::class)
    suspend fun registerByCellphone(
        cellphoneParam: CellphoneParam,
        password: String,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<CellphoneRegister>

    /**
     * 服務5. 忘記密碼 by手機
     *
     * @param cellphoneParam 電話參數
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun forgotPasswordForCellphone(
        cellphoneParam: CellphoneParam,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<CellphoneForgotPassword>

    /**
     * 服務6. 更改密碼
     *
     * @param oldPassword 舊密碼
     * @param oldHasMd5 舊密碼是否為MD5
     * @param newPassword 新密碼
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun updatePassword(
        oldPassword: String,
        oldHasMd5: Boolean,
        newPassword: String,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<UpdatePasswordResponseBody>

    /**
     * 服務7. 取得帳號資訊
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun getAccountInfo(
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<AccountInfo>

    /**
     * 服務8. 觸發手機綁定
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun bindCellphone(
        cellphoneParam: CellphoneParam,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<BindCellphoneResponseBody>

    /**
     * 服務9. 執行手機綁定驗證手機綁定驗證碼
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun checkCellphoneBindingVerifyCode(
        cellphoneParam: CellphoneParam,
        verifyCode: String?,
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<CheckCellphoneBindingVerifyCodeResponseBody>

    /**
     * 服務10. 取消手機綁定
     *
     * @param domain 網域名稱
     * @param url 完整的Url
     *
     */
    suspend fun unbindCellphone(
        domain: String = manager.getCellphoneSettingAdapter().getDomain(),
        url: String = "${domain}MobileService/ashx/LoginCheck/LoginCheck.ashx"
    ): Result<UnbindCellphoneResponseBody>
}